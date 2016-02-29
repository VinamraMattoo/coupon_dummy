package com.portea.commp.smsen.engine.batch;


import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.dao.ConfigTargetTypeDao;
import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.engine.SmsValidationResult;
import com.portea.commp.smsen.gw.GatewaySmsSubmissionResponse;
import com.portea.commp.smsen.gw.RejectedSmsRetryPolicy;
import com.portea.commp.smsen.gw.SmsGatewayHandler;
import com.portea.commp.smsen.gw.SmsGatewayManager;
import com.portea.commp.smsen.util.ConnectionHolder;
import com.portea.commp.smsen.util.DateUtil;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SmsSubmissionWorker implements ISmsBatchWorker {

    private static final Integer COOLING_PERIOD_MAX_CHECK_COUNT = 11;
    public static AtomicInteger workerCounter = new AtomicInteger(0);
    public static AtomicInteger loadCounter = new AtomicInteger(0);
    private List<SmsInAssembly> smsInAssemblies;
    private Logger LOG;

    @Inject
    private SmsBatchedQueueManager smsBatchedQueueManager;
    @Inject
    private SmsGatewayManager smsGatewayManager;
    @Inject
    @JpaDao
    private SmsAssemblyDao smsAssemblyDao;
    @Inject
    @JpaDao
    private SmsRecordDao smsRecordDao;
    @Inject
    @JpaDao
    private SmsAuditDao smsAuditDao;
    @Inject
    @JpaDao
    private PatientDetailDao patientDetailDao;
    @Inject
    @JpaDao
    private BrandDao brandDao;
    @Inject
    @JpaDao
    private SmsSentCoolingDataDao smsSentCoolingDataDao;
    @Inject
    @JpaDao
    private ConfigTargetTypeDao configTargetTypeDao;
    @Inject
    @JpaDao
    private ConfigParamDao configParamDao;
    @EJB
    private SmsEngineUtil smsEngineUtil;
    @Inject
    @JpaDao
    private SmsGatewayLogDao smsGatewayLogDao;
    @Inject
    @JpaDao
    private TargetConfigValueDao targetConfigValueDao;
    private String name = null;

    private Integer correlationIdMaxCheckCount;
    private Integer correlationIdMinWaitTime;
    private Integer gatewayFindingFailureMaxCount;
    private Integer gatewayFailureMaxCount;
    private Integer sleepDuration;
    private Integer maxRetryCount;
    private String rejectionPolicy;

    public SmsSubmissionWorker() {
    }

    public void setSmsInAssembly(List<SmsInAssembly> smsInAssemblies) {
        this.smsInAssemblies = smsInAssemblies;
    }

    @Override
    public void run() {

        try {

            LOG = LoggerFactory.getLogger(
                    getName() != null ? getName() :
                            SmsSubmissionWorker.class.getSimpleName() + "." + UUID.randomUUID());

            LogUtil.entryTrace("run", LOG);

            long beginTime = System.currentTimeMillis();

            correlationIdMaxCheckCount = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.CORRELATION_ID_CHECK_MAX_COUNT, null, ConfigEngineParam.
                            CORRELATION_ID_CHECK_MAX_COUNT.getDefaultValue()));

            correlationIdMinWaitTime = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.CORRELATION_ID_CHECK_MIN_WAIT_TIME, null, ConfigEngineParam.
                            CORRELATION_ID_CHECK_MIN_WAIT_TIME.getDefaultValue()));

            gatewayFindingFailureMaxCount = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.GATEWAY_FINDING_FAILURE_RETRY_THRESHOLD, null, ConfigEngineParam.
                            GATEWAY_FINDING_FAILURE_RETRY_THRESHOLD.getDefaultValue()));

            gatewayFailureMaxCount = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.SMS_SUBMISSION_GATEWAY_FAILURE_COUNT_THRESHOLD, null, ConfigEngineParam.
                            SMS_SUBMISSION_GATEWAY_FAILURE_COUNT_THRESHOLD.getDefaultValue()));

            sleepDuration = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.SMS_SUBMISSION_WORKER_BATCH_SLEEP_TIME, null, ConfigEngineParam.
                            SMS_SUBMISSION_WORKER_BATCH_SLEEP_TIME.getDefaultValue()));

            maxRetryCount = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.RETRY_COUNT, null, ConfigEngineParam.RETRY_COUNT.getDefaultValue()));

            rejectionPolicy = smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                    UNSENT_SMS_RETRIAL_POLICY, null, ConfigEngineParam.UNSENT_SMS_RETRIAL_POLICY.getDefaultValue());

            Integer processingSize = smsInAssemblies.size();

            LOG.debug("processing size: " + smsInAssemblies.size() + " by worker " + getName());


            while (! smsInAssemblies.isEmpty()) {
                LOG.trace("entering work assembly size " + smsInAssemblies.size());
                doWork();
            }


            long endTime = System.currentTimeMillis();
            LOG.trace("Processed " + processingSize + " Sms in " + (endTime - beginTime) + " ms");

            int currentCount = workerCounter.incrementAndGet();

            if (currentCount % 1000 == 0) {
                if (LOG.isDebugEnabled()) LOG.debug("Worker number " + currentCount + " finished.");
            }

            LogUtil.exitTrace("run", LOG);

        } catch (RuntimeException | InterruptedException e) {
            LOG.error("Error", e);
        }
    }

    private void doWork() throws InterruptedException {

        Iterator<SmsInAssembly> smsInAssemblyIterator = smsInAssemblies.iterator();

        while (smsInAssemblyIterator.hasNext()) {
            SmsInAssembly smsInAssembly = smsInAssemblyIterator.next();
            if (smsInAssembly.getSubmissionConnectionHolder() == null) {

                validateAndSubmitSms(smsInAssembly, smsInAssemblyIterator);
            }
            else {

                checkForCorrelationId(smsInAssembly, smsInAssemblyIterator);
            }
        }
        Thread.yield();
        Thread.sleep(sleepDuration);
    }


    private void validateAndSubmitSms(SmsInAssembly smsInAssembly, Iterator<SmsInAssembly> smsInAssemblyIterator) {
        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());
        smsInAssembly.updateSmsAssembly(currSms);

        if (! smsInAssembly.isSubmissionValidationDone()) {

            if (currSms.isQueued()) {
                smsEngineUtil.persistSmsStatus(smsInAssembly, SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS,
                        SmsSecondaryProcessingState.LOADED_FOR_SUBMISSION,
                        SmsProcessingStatusReason.LOADED_TO_WORKER.status());

                SmsValidationResult validationResult = validateSms(smsInAssembly);


                if (validationResult.isValid()) {

                    LOG.debug(" sms is valid now finding gateway");

                    findGatewayAndSubmitSms(smsInAssembly ,smsInAssemblyIterator);

                }
                else {
                    LOG.debug("Validation Failed "+validationResult.getReason()+
                            ". Sms record id "+currSms.getSmsRecord().getId());

                    smsEngineUtil.removeSmsFromAssembly(smsInAssembly, SmsPrimaryProcessingState.SUBMISSION_COMPLETED,
                            SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, validationResult.getReason());
                    smsInAssemblyIterator.remove();
                }

            } else {
                // Probably another worker is processing this SMS
                String statusReason = "Received SMS is not queued for submission," +
                        " possibly being handled by another worker , id=" +
                        smsInAssembly.getSmsAssembly().getId() + " primary " +
                        smsInAssembly.getSmsAssembly().getSmsPrimaryProcessingState() +
                        " secondary " + smsInAssembly.getSmsAssembly().getSmsSecondaryProcessingState();

                smsEngineUtil.persistSmsStatus(smsInAssembly, SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS,
                        SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, statusReason);

                LOG.error(statusReason);
                smsInAssemblyIterator.remove();
            }
            smsInAssembly.setSubmissionValidationDone(true);
        }
        else {
            findGatewayAndSubmitSms(smsInAssembly ,smsInAssemblyIterator);
        }
    }

    private void findGatewayAndSubmitSms(SmsInAssembly smsInAssembly, Iterator<SmsInAssembly> smsInAssemblyIterator) {

        SmsGroupGatewayMapping mapping = findGatewayMapping(smsInAssembly);

        if (mapping == null) {

            updateGatewayFindingFailureCount(smsInAssembly, smsInAssemblyIterator);

        }
        else {
            LOG.debug("Received, gateway " + mapping.getSmsGateway().getName() + " for group " +
                    smsInAssembly.getSmsAssembly().getSmsGroup().getId());

            storeSmsConnectionReader(smsInAssembly, mapping);
        }
        smsInAssembly.setLastGatewayQueriedOn(new Date());
    }

    private void updateGatewayFindingFailureCount(SmsInAssembly smsInAssembly,
                                                  Iterator<SmsInAssembly> smsInAssemblyIterator) {

        smsInAssembly.setGatewayFindingFailureCount(smsInAssembly.getGatewayFindingFailureCount() + 1);

        LOG.warn("Failed to find a Gateway, current trial count " + smsInAssembly.getGatewayFindingFailureCount());

        if (smsInAssembly.getGatewayFindingFailureCount() > gatewayFindingFailureMaxCount) {

            checkGatewayRejectionPolicy(smsInAssembly, SmsPrimaryProcessingState.SUBMISSION_COMPLETED,
                    SmsSecondaryProcessingState.FAILED_SUBMISSION,
                    SmsProcessingStatusReason.GATEWAY_MAPPING_NOT_FOUND.status());
            smsInAssemblyIterator.remove();
        }
    }

    private void checkGatewayRejectionPolicy(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryState,
                                             SmsSecondaryProcessingState secondaryState, String reason) {

        checkGatewayRejectionPolicy(smsInAssembly, primaryState, secondaryState, reason, null, null);
    }

    private void checkGatewayRejectionPolicy(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryState,
                                             SmsSecondaryProcessingState secondaryState, String reason, String responseCode,
                                             String responseMessage) {

        RejectedSmsRetryPolicy retryPolicy = RejectedSmsRetryPolicy.find(rejectionPolicy);
        switch (retryPolicy) {
            case NO_RETRY:
                LOG.debug("No retry, removing sms "+smsInAssembly.getSmsAssembly().getId());
                smsEngineUtil.removeSmsFromAssembly(smsInAssembly,primaryState ,secondaryState,
                        reason, responseCode, responseMessage);
                break;
            case RETRY:
                LOG.debug("Sending to retry queue");

                Integer currentRetryCount = smsInAssembly.getSmsAssembly().getRetryCount();

                currentRetryCount = (currentRetryCount == null) ? 0 : currentRetryCount;
                if (currentRetryCount >= maxRetryCount) {
                    LOG.debug("No retry, removing sms "+smsInAssembly.getSmsAssembly().getId());
                    smsEngineUtil.removeSmsFromAssembly(smsInAssembly,primaryState ,secondaryState,
                            reason, responseCode, responseMessage);
                }
                else {
                    SmsInAssembly newSmsInAssembly = smsEngineUtil.createNewSmsForRetrial(smsInAssembly);
                    smsBatchedQueueManager.queueSmsToBeRetriedForSubmission(newSmsInAssembly);
                    smsEngineUtil.removeSmsFromAssembly(smsInAssembly,primaryState ,secondaryState,
                            reason, responseCode, responseMessage);
                }
                break;
        }
    }

    private void checkForCorrelationId(SmsInAssembly smsInAssembly,
                                       Iterator<SmsInAssembly> smsInAssemblyIterator) {
        boolean hasMinWaitTimeElapsed = DateUtil.hasElapsed(
                smsInAssembly.getLastGatewayQueriedOn(), correlationIdMinWaitTime);

        if (! hasMinWaitTimeElapsed) {

            LOG.debug("Waiting for minimum time to elapse before requesting for status response, remaining time "
                    +DateUtil.getRemainingTime(smsInAssembly.getLastGatewayQueriedOn(), correlationIdMinWaitTime));
        }
        else {
            try{
                smsInAssembly.setCorrelationIdCheckTrialCount(smsInAssembly.getCorrelationIdCheckTrialCount() + 1);
                checkCorrelationTrialCount(smsInAssembly, smsInAssemblyIterator);

                checkConnectionReaderForCorrelationId(smsInAssembly, smsInAssemblyIterator);
            }
            catch (IOException e) {

                LOG.error("IOException occurred while checking sms " + smsInAssembly.getSmsAssembly().getId() +
                        " for correlationId reason, Error msg: " + e.getMessage());

                smsEngineUtil.intermediateStatusUpdate(smsInAssembly, SmsPrimaryProcessingState.
                                SUBMISSION_UNDER_PROCESS, SmsSecondaryProcessingState.INTERMEDIATE_SUBMISSION_UPDATE,
                        SmsProcessingStatusReason.IO_EXCEPTION.status(), "Io Exception");
            }
            finally {
                smsInAssembly.setLastGatewayQueriedOn(new Date());
            }
        }
    }

    private void checkConnectionReaderForCorrelationId(SmsInAssembly smsInAssembly,
                                                       Iterator<SmsInAssembly> smsInAssemblyIterator) throws IOException{

        BufferedReader br = smsInAssembly.getSubmissionConnectionReader();

        LOG.trace("checking if buffered reader is ready for id "+smsInAssembly.getSmsAssembly().getId());
        if (br == null || br.ready()) {

            validateAndPersistCorrelationId(smsInAssembly, smsInAssemblyIterator);

        }
    }

    private void checkCorrelationTrialCount(SmsInAssembly smsInAssembly,
                                            Iterator<SmsInAssembly> smsInAssemblyIterator) {


        if (smsInAssembly.getCorrelationIdCheckTrialCount() > correlationIdMaxCheckCount) {
            updateGatewayFailureTrialCount(smsInAssembly);

            checkGatewayRejectionPolicy(smsInAssembly, SmsPrimaryProcessingState.SUBMISSION_COMPLETED,
                    SmsSecondaryProcessingState.FAILED_SUBMISSION,
                    SmsProcessingStatusReason.FAILED_TO_RECEIVE_CORRELATION_ID.status());
            smsInAssemblyIterator.remove();
        }
        else {
            LOG.debug("Input stream not ready skipping for now and increasing trial count to "
                    + smsInAssembly.getCorrelationIdCheckTrialCount());
        }
    }

    private void updateGatewayFailureTrialCount(SmsInAssembly smsInAssembly) {
        //Update Gateway Failure:
        SmsGroupGatewayMapping mapping = smsInAssembly.getGatewayMapping();
        Integer currentFailureCount = (mapping.getSmsGateway().getFailureCount() == null) ? 0 : mapping.getSmsGateway().getFailureCount();
        //TODO update gateway failure count
        if (currentFailureCount > gatewayFailureMaxCount) {
            //TODO Mark gateway as inactive and send notification.
        }
    }

    private void validateAndPersistCorrelationId(SmsInAssembly smsInAssembly,
                                                 Iterator<SmsInAssembly> smsInAssemblyIterator) throws IOException {

        ConnectionHolder connectionHolder = smsInAssembly.getSubmissionConnectionHolder();
        SmsGroupGatewayMapping mapping = smsInAssembly.getGatewayMapping();
        SmsGatewayHandler smsGatewayHandler = smsGatewayManager.getGatewayHandler(mapping);

        GatewaySmsSubmissionResponse response = smsGatewayHandler.
                readSubmissionResponse(connectionHolder);

        if(response.isError()) {

            String reason = "Error while submitting sms error code: "+response.
                    getResponseCode()+" msg "+response.getResponseMessage();

            LOG.debug(reason);
            checkGatewayRejectionPolicy(smsInAssembly, SmsPrimaryProcessingState.SUBMISSION_COMPLETED,
                    SmsSecondaryProcessingState.FAILED_SUBMISSION, reason, response.getResponseCode(),
                    response.getResponseMessage());
        }
        else {



            String correlationId = response.getCorrelationId();
            LOG.info("Received correlation ID " + correlationId);
            smsEngineUtil.persistSmsGatewayLogData(smsInAssembly, correlationId);
            smsEngineUtil.persistSmsCorrelationId(
                    smsInAssembly, correlationId, response.getResponseCode(), response.getResponseMessage());

        }
        smsInAssembly.closeSubmissionConnection();
        LOG.trace("Sms Assembly id " + smsInAssembly.getSmsAssembly().getId() + " buffered reader is closed");
        LOG.trace("current assembly size " + smsInAssemblies.size());

        smsInAssemblyIterator.remove();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name != null) {
            throw new IllegalStateException("Name cannot be changed. Existing name="
                    + this.name + ",new name=" + name);
        }

        if (name == null || 0 == name.trim().length()) {
            throw new IllegalArgumentException("Name to be set cannot be null or empty");
        }

        this.name = name;
    }

    private void storeSmsConnectionReader(SmsInAssembly smsInAssembly, SmsGroupGatewayMapping mapping) {

        SmsGatewayHandler smsGwHandler = smsGatewayManager.getGatewayHandler(mapping);

        LOG.debug("Sending Sms to gateway " + smsGwHandler.getSmsGatewayName());

        ConnectionHolder connectionHolder = smsGwHandler.getSubmissionConnectionHolder(smsInAssembly.getSmsAssembly());//todo increase gateway failure count if br is null

        smsEngineUtil.persistSmsSubmittedAt(smsInAssembly, mapping.getSmsGateway());
        smsInAssembly.setSubmissionConnectionHolder(connectionHolder);

        LOG.trace("Setting Buffered reader for smsInAssembly " + smsInAssembly.getSmsAssembly().getId());
    }

    private SmsGroupGatewayMapping findGatewayMapping(SmsInAssembly smsInAssembly) {

        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());
        LOG.trace("Requesting, gateway for the given group " + currSms.getSmsGroup().getName());
        SmsGroupGatewayMapping mapping = smsGatewayManager.getSmsGroupGatewayMapping(smsInAssembly);

        return mapping;
    }

    private SmsValidationResult validateSms(SmsInAssembly smsInAssembly) {
        SmsAssembly smsAssembly = smsInAssembly.getSmsAssembly();

        LOG.trace("Validating SmsInAssembly " + smsAssembly.getId());
        User user = smsAssembly.getUser();
        String mobileNumber = smsAssembly.getMobileNumber();
        Integer messageHash = smsAssembly.getMessageHash();
        SmsGroup smsGroup = smsAssembly.getSmsGroup();
        SmsType smsType = smsGroup.getSmsType();
        String message = smsAssembly.getMessage();
        String scheduledTimeZone = smsAssembly.getScheduledTimeZone();

        Boolean isPhoneNumberValidationDone = Boolean.parseBoolean(
                      smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                              NEW_SMS_PHONE_NUMBER_VALIDATION, null, ConfigEngineParam.NEW_SMS_PHONE_NUMBER_VALIDATION.getDefaultValue()));

        if(! isPhoneNumberValidationDone && ! smsEngineUtil.isValidPhoneNumber(smsInAssembly)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.INVALID_PHONE_NUMBER.status());
        }

        Boolean isMessageValidationDone = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_MESSAGE_FORMAT_VALIDATION, null, ConfigEngineParam.NEW_SMS_MESSAGE_FORMAT_VALIDATION.getDefaultValue()));

        if(! isMessageValidationDone){
            SmsValidationResult messageValidationResult = smsEngineUtil.validateMessage(smsInAssembly);

            if (! messageValidationResult.isValid()) {

                return messageValidationResult;
            }
        }

        if (smsEngineUtil.hasSmsExpired(smsInAssembly)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.SMS_EXPIRED.status());
        }
        if (smsEngineUtil.isDNDUser(smsInAssembly)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.DND_USER.status());
        }

        SmsValidationResult coolingPeriodValidationResult = null;
        Integer coolingPeriodCheckCount = 0;
        while (coolingPeriodValidationResult == null) {
            if (coolingPeriodCheckCount >= COOLING_PERIOD_MAX_CHECK_COUNT) {
                coolingPeriodValidationResult = new
                        SmsValidationResult(false, SmsProcessingStatusReason.EXHAUSTED_COOLING_PERIOD_MAX_CHECK_COUNT.getReason());
                break;
            }
            coolingPeriodCheckCount++;
            coolingPeriodValidationResult = checkCoolingPeriod(
                    user, smsGroup, smsType, mobileNumber, messageHash, message, scheduledTimeZone);
        }
        if (! coolingPeriodValidationResult.isValid()) {
            return coolingPeriodValidationResult;
        }

        SmsValidationResult throttlingValidationResult = null;
        Integer throttlingCheckCount = 0;
        while (throttlingValidationResult == null) {
            if (throttlingCheckCount >= COOLING_PERIOD_MAX_CHECK_COUNT) {
                throttlingValidationResult =
                        new SmsValidationResult(false, SmsProcessingStatusReason.EXHAUSTED_SMS_THROTTLING_MAX_CHECK_COUNT.getReason());
                break;
            }
            throttlingCheckCount++;
            throttlingValidationResult = checkSmsThrottling(user, mobileNumber, smsType);
        }

        if (! throttlingValidationResult.isValid()) {
            return throttlingValidationResult;
        }

        return new SmsValidationResult(true, null);
    }

    private SmsValidationResult checkSmsThrottling(User user, String mobileNumber, SmsType smsType) {
        try {

            return smsEngineUtil.checkSmsThrottling(user, mobileNumber, smsType);
        } catch (Exception e) {
            Throwable cause = e;
            LOG.warn(cause.getClass().getName()+": @ submissionWorker->checkSmsThrottling()"
                    + e.getMessage());
            while (true) {
                if (cause instanceof PersistenceException) {
                    return null;
                } else {

                    if (cause.getCause() == null) {
                        LOG.warn("PersistenceException not found," +
                                " sms throttling validation failed with the exception "
                                + e.getMessage());
                        return new SmsValidationResult(false,
                                SmsProcessingStatusReason.SMS_THROTTLING_VALIDATION_FAILED.getReason());
                    } else {
                        cause = cause.getCause();
                    }
                }
            }
        }
    }

    private SmsValidationResult checkCoolingPeriod(User user, SmsGroup smsGroup, SmsType smsType, String mobileNumber,
                                                   Integer messageHash, String message, String scheduledTimeZone) {
        try {

            return smsEngineUtil.checkCoolingPeriod(
                    user, smsGroup, smsType, mobileNumber, messageHash, message, scheduledTimeZone);
        } catch (Exception e) {
            Throwable cause = e;
            LOG.warn(cause.getClass().getName()+": @ submissionWorker->checkCoolingPeriod()" + e.getMessage());
            while (true) {
                if (cause instanceof OptimisticLockException ||
                        cause instanceof PersistenceException) {
                    return null;
                } else {
                    if (cause.getCause() == null) {
                        LOG.warn("Expected OptimisticException/PersistenceException," +
                                " sms sent cooling period validation failed with the exception " + e.getMessage());
                        return new SmsValidationResult(false,
                                SmsProcessingStatusReason.COOLING_PERIOD_VALIDATION_FAILED.getReason());
                    } else {
                        cause = cause.getCause();
                    }
                }

            }
        }
    }

}