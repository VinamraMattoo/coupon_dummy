package com.portea.commp.smsen.engine.batch;

import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.service.ejb.SmsQueueManagerHelper;
import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.dao.SmsSentCoolingDataDao;
import com.portea.commp.smsen.domain.ConfigTargetType;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
import com.portea.commp.smsen.engine.SmsQueuePriority;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

@Startup
@Singleton
@ApplicationScoped
public class SmsBatchedQueueManager {

    private final Logger LOG = LoggerFactory.getLogger(SmsBatchedQueueManager.class);

    private final int INITIAL_QUEUE_CAPACITY = 1000;

    /**
     * All the primary queues
     */
    private HashMap<SmsQueuePriority, PriorityBlockingQueue<SmsInAssembly>> primarySubmissionQueues;
    private HashMap<SmsQueuePriority, PriorityBlockingQueue<SmsInAssembly>> primaryStatusQueues;

    /**
     * All the retry queues
     */
    private HashMap<SmsQueuePriority, PriorityBlockingQueue<SmsInAssembly>> retrySubmissionQueues;

    @EJB
    private SmsQueueManagerHelper smsQueueManagerHelper;

    @Inject
    @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject @JpaDao
    private SmsSentCoolingDataDao smsSentCoolingDataDao;

    @Inject
    private SmsEngineUtil smsEngineUtil;

    public SmsBatchedQueueManager() {}

    @PostConstruct
    public void setupSmsQueues() {

        LogUtil.entryTrace("setupSmsQueues", LOG);

        SmsQueuePriority[] queuePriorities = SmsQueuePriority.values();

        if (primarySubmissionQueues == null) {
            primarySubmissionQueues = new HashMap<>();

            for (SmsQueuePriority qp : queuePriorities) {
                primarySubmissionQueues.put(qp, new PriorityBlockingQueue<>(INITIAL_QUEUE_CAPACITY));
            }
        }

        if (retrySubmissionQueues == null) {
            retrySubmissionQueues = new HashMap<>();

            for (SmsQueuePriority qp : queuePriorities) {
                retrySubmissionQueues.put(qp, new PriorityBlockingQueue<>(INITIAL_QUEUE_CAPACITY));

            }
        }

        if (primaryStatusQueues == null) {
            primaryStatusQueues = new HashMap<>();

            for (SmsQueuePriority qp : queuePriorities) {
                primaryStatusQueues.put(qp, new PriorityBlockingQueue<>(INITIAL_QUEUE_CAPACITY));
            }
        }

        LogUtil.exitTrace("setupSmsQueues", LOG);
    }

    public void queueSmsForSubmission(List<SmsAssembly> smsAssemblyCollection) {
        LOG.debug("Submission: Found sms batch size: " + smsAssemblyCollection.size() + " sending it to processing queues");
        smsQueueManagerHelper.moveSmsAssemblyToQueuedStatus(smsAssemblyCollection,
                SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, SmsSecondaryProcessingState.QUEUED_FOR_SUBMISSION);
        LOG.trace("Moved to queued-for-submission");
        moveToSubmissionQueues(smsAssemblyCollection);
    }

    public void queueSmsForStatusCheck(List<SmsAssembly> smsAssemblies) {
        LOG.debug("Status check: Found sms batch size: "+smsAssemblies.size()+" sending it to processing queues");
        smsQueueManagerHelper.moveSmsAssemblyToQueuedStatus(smsAssemblies,
                SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS, SmsSecondaryProcessingState.QUEUED_FOR_STATUS_CHECK);

        moveToStatusCheckQueues(smsAssemblies);
    }

    private void moveToStatusCheckQueues(List<SmsAssembly> smsAssemblies) {
        smsAssemblies.forEach((smsAssembly -> {
            SmsInAssembly smsInAssembly = new SmsInAssembly();
            smsInAssembly.updateSmsAssembly(smsAssemblyDao.find(smsAssembly.getId()));

            primaryStatusQueues.get(
                    translateQueuePriority(
                            smsInAssembly.getSmsAssembly().getSmsGroup().getPriority())
            ).add(smsInAssembly);
        }));
    }

    private void moveToSubmissionQueues(List<SmsAssembly> smsAssemblies) {
        for (SmsAssembly smsAssembly : smsAssemblies) {
            SmsInAssembly smsInAssembly = new SmsInAssembly();
            smsInAssembly.updateSmsAssembly(smsAssemblyDao.find(smsAssembly.getId())); // Get updated copy from db

            primarySubmissionQueues.get(
                    translateQueuePriority(
                            smsInAssembly.getSmsAssembly().getSmsGroup().getPriority())).add(smsInAssembly);
        }

    }


    public void queueSmsToBeRetriedForSubmission(SmsInAssembly smsInAssembly) {

        retrySubmissionQueues.get(
                translateQueuePriority(
                        smsInAssembly.getSmsAssembly().getSmsGroup().getPriority())).add(smsInAssembly);
    }


    public void queueDirectSms(SmsAssembly smsAssembly) {

        LOG.debug("Submitting direct sms to queue ");

        SmsInAssembly smsInAssembly = new SmsInAssembly();
        smsInAssembly.updateSmsAssembly(smsAssembly);

        primarySubmissionQueues.get(SmsQueuePriority.SMS_QUEUE_PRIORITY_DIRECT).add(smsInAssembly);
    }

    public void queueDirectSmsToBeRetried(SmsInAssembly smsInAssembly) {
        queueSmsToBeRetriedForSubmission(smsInAssembly);
    }

    public BlockingQueue<SmsInAssembly> getSubmissionPrimaryQueue(SmsQueuePriority smsQueuePriority) {
        return primarySubmissionQueues.get(smsQueuePriority);
    }

    public BlockingQueue<SmsInAssembly> getStatusPrimaryQueue(SmsQueuePriority smsQueuePriority) {
        return primaryStatusQueues.get(smsQueuePriority);
    }

    public BlockingQueue<SmsInAssembly> getSubmissionRetryQueue(SmsQueuePriority smsQueuePriority) {
        return retrySubmissionQueues.get(smsQueuePriority);
    }

    private SmsQueuePriority translateQueuePriority(int queuePriority) {
        switch (queuePriority) {
            case 1:
            case 2:
            case 3:
                return SmsQueuePriority.SMS_QUEUE_PRIORITY_HIGH;
            case 4:
            case 5:
            case 6:
                return SmsQueuePriority.SMS_QUEUE_PRIORITY_MEDIUM;
            case 7:
            case 8:
            case 9:
            case 10:
                return SmsQueuePriority.SMS_QUEUE_PRIORITY_LOW;
            default:
                return SmsQueuePriority.SMS_QUEUE_PRIORITY_LOW;
        }
    }

    public Integer getMaxSmsToLoadForSubmission() {
        Integer maxSmsCount = Integer.parseInt(smsEngineUtil.
                getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        MAX_SUBMISSION_COUNT_LOADED_TO_QUEUES, null, ConfigEngineParam.MAX_SUBMISSION_COUNT_LOADED_TO_QUEUES.getDefaultValue()));
        Integer currentSmsQueued = 0;

        currentSmsQueued += getQueueSize(primarySubmissionQueues);
        currentSmsQueued += getQueueSize(retrySubmissionQueues);

        return (maxSmsCount - currentSmsQueued) < 0 ? 0 : (maxSmsCount - currentSmsQueued);
    }

    public Integer getMaxSmsToLoadForStatusCheck() {
        Integer maxSmsCount = Integer.parseInt(smsEngineUtil.
                getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.MAX_STATUS_CHECK_COUNT_LOADED_TO_QUEUES
                        , null, ConfigEngineParam.MAX_STATUS_CHECK_COUNT_LOADED_TO_QUEUES.getDefaultValue()));
        Integer currentSmsQueued = 0;

        currentSmsQueued += getQueueSize(primaryStatusQueues);

        return (maxSmsCount - currentSmsQueued) < 0 ? 0 : (maxSmsCount - currentSmsQueued);
    }

    private Integer getQueueSize(HashMap<SmsQueuePriority, PriorityBlockingQueue<SmsInAssembly>> queues) {
        Integer size = 0;
        for (SmsQueuePriority smsQueuePriority : primaryStatusQueues.keySet()) {
            BlockingQueue<SmsInAssembly> blockingQueue = primaryStatusQueues.get(smsQueuePriority);
            size += blockingQueue.size();
        }
        return size;
    }
}