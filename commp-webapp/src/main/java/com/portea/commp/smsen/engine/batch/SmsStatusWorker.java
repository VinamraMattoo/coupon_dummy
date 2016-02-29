package com.portea.commp.smsen.engine.batch;

import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.dao.SmsRecordDao;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.gw.*;
import com.portea.commp.smsen.util.ConnectionHolder;
import com.portea.commp.smsen.util.DateUtil;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SmsStatusWorker implements ISmsBatchWorker {

    private String name;
    private List<SmsInAssembly> smsInAssemblies;
    private static final Logger LOG = LoggerFactory.getLogger(SmsStatusWorker.class);

    @Inject
    private SmsEngineUtil smsEngineUtil;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject @JpaDao
    private SmsRecordDao smsRecordDao;

    @Inject
    private SmsBatchedQueueManager smsBatchedQueueManager;

    @Inject
    private SmsGatewayManager smsGatewayManager;
    private Integer statusCheckMaxCount;
    private Integer statusCheckMinWaitTime;
    private Integer sleepDuration;
    private Integer timeOutDuration;
    private String rejectionPolicy;
    private Integer maxRetryCount;

    @Override
    public void setSmsInAssembly(List<SmsInAssembly> smsInAssemblyList) {
        this.smsInAssemblies = smsInAssemblyList;
        //checkIfSmsAlreadyExists();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        try {
            LOG.debug("Worker created for status check " + getName() + " batch size: " + smsInAssemblies.size());

            statusCheckMaxCount = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.STATUS_CHECK_MAX_COUNT, null, ConfigEngineParam.STATUS_CHECK_MAX_COUNT.getDefaultValue()));

            statusCheckMinWaitTime = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.STATUS_CHECK_MIN_WAIT_TIME, null, ConfigEngineParam.STATUS_CHECK_MIN_WAIT_TIME.getDefaultValue()));

            sleepDuration = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.SMS_STATUS_WORKER_BATCH_SLEEP_TIME, null, ConfigEngineParam.SMS_STATUS_WORKER_BATCH_SLEEP_TIME.getDefaultValue()));

            timeOutDuration = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.SMS_STATUS_CHECK_TIME_OUT_DURATION, null, ConfigEngineParam.SMS_STATUS_CHECK_TIME_OUT_DURATION.getDefaultValue()));

            maxRetryCount = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                    ConfigEngineParam.RETRY_COUNT, null, ConfigEngineParam.RETRY_COUNT.getDefaultValue()));

            rejectionPolicy = smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                    UNSENT_SMS_RETRIAL_POLICY, null, ConfigEngineParam.UNSENT_SMS_RETRIAL_POLICY.getDefaultValue());

            LOG.debug("Time out duration: " + timeOutDuration);

            while (!smsInAssemblies.isEmpty()) {
                doWork();
            }
        } catch (RuntimeException | InterruptedException e) {
            LOG.error("Error", e);
        }
    }

    private void doWork() throws InterruptedException {

        Iterator<SmsInAssembly> smsInAssemblyIterator = smsInAssemblies.iterator();

        while(smsInAssemblyIterator.hasNext()) {

            SmsInAssembly smsInAssembly = smsInAssemblyIterator.next();

            if(smsInAssembly.getStatusConnectionHolder() == null) {

                LOG.debug("Initiating status check for sms: " + smsInAssembly.getSmsAssembly().getId());
                initiateStatusCheck(smsInAssembly, smsInAssemblyIterator);
            }
            else {

                LOG.debug("Getting status response for sms: " + smsInAssembly.getSmsAssembly().getId());
                handleStatusCheck(smsInAssembly, smsInAssemblyIterator);
            }
        }

        Thread.yield();
        Thread.sleep(sleepDuration);
    }

    private void handleStatusCheck(SmsInAssembly smsInAssembly,
                                   Iterator<SmsInAssembly> smsInAssemblyIterator) {

        boolean hasMinWaitTimeElapsed = DateUtil.hasElapsed(smsInAssembly.getLastGatewayQueriedOn(), statusCheckMinWaitTime);

        if( ! hasMinWaitTimeElapsed) {

            LOG.debug("Waiting for minimum time to elapse before requesting for status response, remaining time "
                    +DateUtil.getRemainingTime(smsInAssembly.getLastGatewayQueriedOn(), statusCheckMinWaitTime));
        }
        else {
            try {
                smsInAssembly.setStatusCheckTrialCount(smsInAssembly.getStatusCheckTrialCount() + 1);
                checkStatusCheckTrialCount(smsInAssembly, smsInAssemblyIterator);

                checkSmsStatus(smsInAssembly, smsInAssemblyIterator);
            } catch (IOException e) {

                LOG.error("IOException occurred while checking sms " + smsInAssembly.getSmsAssembly().getId() +
                        " status, Error msg: " + e.getMessage());

                smsEngineUtil.intermediateStatusUpdate(smsInAssembly, SmsPrimaryProcessingState.
                                STATUS_CHECK_UNDER_PROCESS, SmsSecondaryProcessingState.INTERMEDIATE_STATUS_UPDATE,
                        SmsProcessingStatusReason.IO_EXCEPTION.status(), "Io Exception");
            }
            finally {

                smsInAssembly.setLastGatewayQueriedOn(new Date());
            }
        }
    }

    private void checkStatusCheckTrialCount(SmsInAssembly smsInAssembly, Iterator<SmsInAssembly> smsInAssemblyIterator) {
        Integer currentTrialCount = smsInAssembly.getStatusCheckTrialCount();

        if (currentTrialCount % statusCheckMaxCount == 0) {
            LOG.debug("Status is checked "+currentTrialCount+" times now removing from worker");
            //update gateway failure;
            smsEngineUtil.updateStatusCheckTrialCount(smsInAssembly);
            smsEngineUtil.persistSmsStatus(smsInAssembly, SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED,
                    SmsSecondaryProcessingState.READY_FOR_STATUS_CHECK, SmsProcessingStatusReason.READY_FOR_STATUS_CHECK.status());

            smsInAssemblyIterator.remove();
        }
    }

    private void checkSmsStatus(SmsInAssembly smsInAssembly, Iterator<SmsInAssembly> smsInAssemblyIterator) throws IOException{
        BufferedReader br = smsInAssembly.getStatusConnectionReader();

        if(br == null || br.ready()) {


            LOG.debug("Status is ready to be read ");

            SmsGatewayHandler smsGatewayHandler = getGatewayHandler(smsInAssembly.getSmsAssembly());

            LOG.debug("Received gateway: "+smsGatewayHandler.getSmsGatewayName());

            GatewaySmsStatusCheckResponse response = smsGatewayHandler.readStatusResponse(smsInAssembly.getStatusConnectionHolder());

            if (response.isError()) {
                String reason = "Error while delivering sms, error code: "+response.
                        getResponseCode()+" msg "+response.getResponseMessage();

                LOG.debug(reason);

                checkGatewayRejectionPolicy(smsInAssembly, SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED,
                        SmsSecondaryProcessingState.FAILED_TO_DELIVER, reason, response.getResponseCode(),
                        response.getResponseMessage());

                smsInAssemblyIterator.remove();
            }
            else {
                readGatewaySmsStatus(smsInAssembly, smsInAssemblyIterator, response);
            }
            smsInAssembly.closeStatusConnection();
            smsInAssembly.setStatusConnectionHolder(null);
        }
        else {
            LOG.debug("Status is not ready to be read ");
        }
    }

    private void readGatewaySmsStatus(SmsInAssembly smsInAssembly,
                                      Iterator<SmsInAssembly> smsInAssemblyIterator, GatewaySmsStatusCheckResponse response) {

        GatewaySmsStatus gatewaySmsStatus = response.getStatus();

        LOG.debug("Received gateway sms status: "+gatewaySmsStatus.getName());

        if(gatewaySmsStatus.isTerminal()) {
            if(gatewaySmsStatus.isSuccess()) {
                LOG.debug("Successfully delivered sms: "+smsInAssembly.getSmsAssembly().getId());

                smsEngineUtil.removeSmsFromAssembly(smsInAssembly, SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED,
                        SmsSecondaryProcessingState.SUCCESSFUL_DELIVERY, SmsProcessingStatusReason.SUCCESSFULLY_SENT.status(),
                        response.getResponseCode(), response.getResponseMessage(), gatewaySmsStatus.name());

            }
            else {
                LOG.debug("Failed to deliver delivered sms: "+ smsInAssembly.getSmsAssembly().getId()+
                        " reason: "+gatewaySmsStatus.getName());

                checkGatewayRejectionPolicy(smsInAssembly, SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED,
                        SmsSecondaryProcessingState.FAILED_TO_DELIVER, gatewaySmsStatus.getName(), response.getResponseCode(),
                        response.getResponseMessage(), gatewaySmsStatus.name());

            }
            smsInAssemblyIterator.remove();
        }
        else {
            LOG.debug("Sms: "+smsInAssembly.getSmsAssembly().getId()+ " Intermediate status "+gatewaySmsStatus.getName());

            smsEngineUtil.intermediateStatusUpdate(smsInAssembly, SmsPrimaryProcessingState.
                            STATUS_CHECK_UNDER_PROCESS, SmsSecondaryProcessingState.INTERMEDIATE_STATUS_UPDATE,
                    SmsProcessingStatusReason.INTERMEDIATE_STATUS_UPDATE.status(), gatewaySmsStatus.name(),
                    response.getResponseCode(), response.getResponseMessage());
        }
    }

    private void checkGatewayRejectionPolicy(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryState,
                                             SmsSecondaryProcessingState secondaryState, String reason, String responseCode,
                                             String responseMessage) {

        checkGatewayRejectionPolicy(
                smsInAssembly, primaryState, secondaryState, reason, responseCode, responseMessage, null);

    }

    private void checkGatewayRejectionPolicy(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryState,
                                             SmsSecondaryProcessingState secondaryState, String reason, String responseCode,
                                             String responseMessage, String gatewayStatus) {

        RejectedSmsRetryPolicy retryPolicy = RejectedSmsRetryPolicy.find(rejectionPolicy);
        switch (retryPolicy) {
            case NO_RETRY:
                LOG.debug("No retry, removing sms "+smsInAssembly.getSmsAssembly().getId());
                smsEngineUtil.removeSmsFromAssembly(smsInAssembly,primaryState ,secondaryState,
                        reason, responseCode, responseMessage, gatewayStatus);
                break;
            case RETRY:
                LOG.debug("Sending to retry queue");

                Integer currentRetryCount = smsInAssembly.getSmsAssembly().getRetryCount();
                currentRetryCount = (currentRetryCount == null) ? 0 : currentRetryCount;
                if (currentRetryCount >= maxRetryCount) {
                    LOG.debug("No retry, removing sms "+smsInAssembly.getSmsAssembly().getId());
                    smsEngineUtil.removeSmsFromAssembly(smsInAssembly,primaryState ,secondaryState,
                            reason, responseCode, responseMessage, gatewayStatus);
                }
                else {
                    SmsInAssembly newSmsInAssembly = smsEngineUtil.createNewSmsForRetrial(smsInAssembly);
                    smsBatchedQueueManager.queueSmsToBeRetriedForSubmission(newSmsInAssembly);
                    smsEngineUtil.removeSmsFromAssembly(smsInAssembly,primaryState ,secondaryState,
                            reason, responseCode, responseMessage, gatewayStatus);
                }
                break;
        }
    }

    private boolean checkTimeOut(SmsInAssembly smsInAssembly) {
        Date  receivedCorrelationIdAt =smsInAssembly.getSmsAssembly().getReceivedCorrelationIdAt();

        return DateUtil.hasElapsed(receivedCorrelationIdAt, timeOutDuration);
    }

    private void initiateStatusCheck(SmsInAssembly smsInAssembly, Iterator<SmsInAssembly> smsInAssemblyIterator) {

        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());

        validateQueuedSms(currSms, smsInAssembly, smsInAssemblyIterator);

        SmsGatewayHandler smsGatewayHandler = getGatewayHandler(currSms);

        String correlationId = currSms.getCorrelationId();
        ConnectionHolder connectionHolder = smsGatewayHandler.getStatusCheckConnectionHolder(correlationId);

        smsInAssembly.setStatusConnectionHolder(connectionHolder);
        smsInAssembly.setLastGatewayQueriedOn(new Date());
    }

    /**
     * Only First time sms status check will check if sms is queued.
     */
    private void validateQueuedSms(SmsAssembly currSms, SmsInAssembly smsInAssembly,
                                   Iterator<SmsInAssembly> smsInAssemblyIterator) {
        if(! smsInAssembly.isStatusValidationDone()) {
            if( ! currSms.isQueuedForStatusCheck()) {

                String statusReason = "Received SMS is not queued for status check, possibly being handled by another worker , id=" +
                        smsInAssembly.getSmsAssembly().getId() + " primary " + smsInAssembly.getSmsAssembly().getSmsPrimaryProcessingState() +
                        " secondary " + smsInAssembly.getSmsAssembly().getSmsSecondaryProcessingState();

                smsEngineUtil.persistSmsStatus(smsInAssembly, SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS,
                        SmsSecondaryProcessingState.NEVER_SENT_DURING_STATUS_CHECK, statusReason);

                LOG.error(statusReason);
                smsInAssemblyIterator.remove();
                return;
            }
            else {
                smsEngineUtil.persistSmsStatus(smsInAssembly,
                        SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS,
                        SmsSecondaryProcessingState.LOADED_FOR_STATUS_CHECK,
                        SmsProcessingStatusReason.LOADED_FOR_STATUS_CHECK.status());
            }

            if(checkTimeOut(smsInAssembly)) {
                String status = "sms "+smsInAssembly.getSmsAssembly().getId()+" timed out so removing from checking status";

                smsEngineUtil.removeSmsFromAssembly(smsInAssembly, SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED,
                        SmsSecondaryProcessingState.STATUS_CHECK_TIME_OUT, status);

                smsInAssemblyIterator.remove();
            }
            smsInAssembly.setStatusValidationDone(true);
        }
    }

    private SmsGatewayHandler getGatewayHandler(SmsAssembly currSms) {
        SmsRecord smsRecord = currSms.getSmsRecord();
        SmsRecord currRecord = smsRecordDao.find(smsRecord.getId());

        SmsGateway submittedGateway = currRecord.getSmsGateway();

        return smsGatewayManager.getGatewayHandler(submittedGateway.getId());
    }
}
