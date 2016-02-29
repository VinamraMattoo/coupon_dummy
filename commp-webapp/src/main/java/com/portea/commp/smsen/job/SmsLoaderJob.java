package com.portea.commp.smsen.job;

import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.engine.Brand;
import com.portea.commp.smsen.engine.SmsValidationResult;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A job which retrieves a new batch of SMS to be sent from the queue in the database.
 * The retrieved SMS are moved to assembly for processing.
 */
@DisallowConcurrentExecution
public class SmsLoaderJob implements Job {

    private static Logger LOG = LoggerFactory.getLogger(SmsLoaderJob.class);

    @Inject @JpaDao
    private SmsQueueDao smsQueueDao;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject @JpaDao
    private SmsRecordDao smsRecordDao;

    @Inject
    @JpaDao
    private SmsSentCoolingDataDao smsSentCoolingDataDao;

    @Inject
    @JpaDao
    private PatientDetailDao patientDetailDao;

    @Inject
    @JpaDao
    private BrandDao brandDao;

    @Inject @JpaDao
    private SmsAuditDao smsAuditDao;

    @Resource
    ManagedThreadFactory threadFactory;

    @Inject
    private SmsEngineUtil smsEngineUtil;
    private String maxLoad;
    private String loadWindow;
    private boolean validatePhoneNumber;
    private boolean doDndCheck;
    private boolean validateMessage;

    @Asynchronous
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            doWork();

        } catch (Exception e) {
            LOG.error("Error", e);
        }

    }

    private void doWork() throws Exception{
        LogUtil.entryTrace("execute", LOG);

        long start = System.nanoTime();

        setUpConfig();

        List<SmsQueue> smsQueueList = smsQueueDao.getNextBatchFromQueue(Integer.parseInt(loadWindow), Integer.parseInt(maxLoad));

        Integer size = smsQueueList.size();

        if (size > 0) {

            LOG.debug("No. of sms fetched " + size);
        }

        while(true) {

            if (smsQueueList.isEmpty()) {
                break;
            }

            List<SmsQueue> partialList;

            if (smsQueueList.size() > 50) {
                partialList = smsQueueList.subList(0, 49);
            }
            else {
                partialList = smsQueueList;
            }

            Map<SmsQueue, SmsValidationResult> smsValidationResultMap = new HashMap<>();

            final List<SmsQueue> finalPartialList = partialList;

            Thread validationThread = threadFactory.newThread(() -> finalPartialList.forEach((smsQueue) -> {
                SmsValidationResult smsValidationResult = smsEngineUtil.getValidationResult(smsQueue.getMobileNumber(),
                        smsQueue.getUser(), smsQueue.getMessage(), smsQueue.getReceiverType(),
                        smsQueue.getSmsGroup(), validatePhoneNumber, validateMessage, doDndCheck);

                smsValidationResultMap.put(smsQueue, smsValidationResult);

            }));

            validationThread.start();
            validationThread.join();

            smsValidationResultMap.forEach((smsQueue, smsValidationResult) -> {
                if (smsValidationResult.isValid()) {
                    SmsRecord record = smsRecordDao.create(createNewSmsRecord(smsQueue));
                    smsEngineUtil.createNewSmsAssembly(record);
                    smsAuditDao.create(record);
                } else {
                    SmsRecord smsRecord = createNewSmsRecord(smsQueue);
                    smsRecord.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.LOADED_FOR_CREATION);
                    smsRecord.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.NEVER_SENT_FOR_SUBMISSION);
                    smsRecord.setStatusReason(smsValidationResult.getReason());
                    smsRecord = smsRecordDao.create(smsRecord);
                    smsAuditDao.create(smsRecord);
                    LOG.debug("Validation Failed " + smsValidationResult.getReason() + ". Sms record id " + smsRecord.getId());
                }
            });

            smsQueueDao.delete(partialList);

            partialList.clear();
        }

        long end = System.nanoTime();

        if (size > 0) {

            LOG.debug("SMS Queue size: " + size + " Loaded in: " +
                            TimeUnit.SECONDS.convert((end - start), TimeUnit.NANOSECONDS) +
                            " seconds"
            );
        }
        LogUtil.exitTrace("execute", LOG);

    }

    private void setUpConfig() {



        loadWindow = smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                NEW_SMS_LOAD_WINDOW ,null, ConfigEngineParam.NEW_SMS_LOAD_WINDOW.getDefaultValue());

        maxLoad = smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                NEW_SMS_LOAD_LIMIT ,null, ConfigEngineParam.NEW_SMS_LOAD_LIMIT.getDefaultValue());

        validatePhoneNumber = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_PHONE_NUMBER_VALIDATION, null, ConfigEngineParam.NEW_SMS_PHONE_NUMBER_VALIDATION.getDefaultValue()));

        validateMessage = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_MESSAGE_FORMAT_VALIDATION, null, ConfigEngineParam.NEW_SMS_MESSAGE_FORMAT_VALIDATION.getDefaultValue()));

        doDndCheck = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_USER_DND_VALIDATION, null, ConfigEngineParam.NEW_SMS_USER_DND_VALIDATION.getDefaultValue()));

        // TODO Write code to adapt to current load which influences number of new SMS being loaded
    }

    private SmsRecord createNewSmsRecord(SmsQueue smsQueue) {
        SmsRecord smsRecord = new SmsRecord();

        com.portea.commp.smsen.domain.Brand brand = smsQueue.getBrand();
        if (brand == null) {
            brand = brandDao.find(Brand.PORTEA.getName());
        }
        String mobileNumber = smsQueue.getMobileNumber();
        String curatedPhoneNumber =
                (mobileNumber.length() == 10) ? "91" + mobileNumber : mobileNumber;
        smsRecord.setBrand(brand);
        smsRecord.setLastUpdatedOn(new Date());
        smsRecord.setMessage(smsQueue.getMessage());

        smsRecord.setUser(smsQueue.getUser());
        smsRecord.setReceiverType(smsQueue.getReceiverType());
        smsRecord.setMobileNumber(curatedPhoneNumber);

        smsRecord.setCountryCode(smsQueue.getCountryCode());
        smsRecord.setScheduledTime(smsQueue.getScheduledTime());
        smsRecord.setSendBefore(smsQueue.getSendBefore());

        smsRecord.setScheduledId(smsQueue.getScheduleId());
        smsRecord.setScheduledTimeZone(smsQueue.getScheduledTimeZone());
        smsRecord.setScheduledType(smsQueue.getScheduleType());

        smsRecord.setSmsGroup(smsQueue.getSmsGroup());
        smsRecord.setSmsTemplate(smsQueue.getSmsTemplate());
        smsRecord.setMessageHash(smsQueue.getMessage().hashCode());

        smsRecord.setSourceName(SmsSource.DB_QUEUE);
        smsRecord.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.CREATED_FOR_SUBMISSION);

        return smsRecord;
    }

}