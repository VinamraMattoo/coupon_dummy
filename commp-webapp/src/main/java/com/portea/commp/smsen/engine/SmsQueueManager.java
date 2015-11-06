package com.portea.commp.smsen.engine;

import com.portea.commp.service.ejb.SmsQueueManagerHelper;
import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.swing.text.html.parser.Entity;
import javax.transaction.TransactionScoped;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

@Startup
@Singleton
@ApplicationScoped
public class SmsQueueManager {

    private final Logger LOG = LoggerFactory.getLogger(SmsQueueManager.class);

    private final int INITIAL_QUEUE_CAPACITY = 1000;

    /**
     * All the primary queues
     */
    private HashMap<SmsQueuePriority, PriorityBlockingQueue<SmsInAssembly>> primaryQueues;

    /**
     * All the retry queues
     */
    private HashMap<SmsQueuePriority, PriorityBlockingQueue<SmsInAssembly>> retryQueues;

    @EJB
    private SmsQueueManagerHelper smsQueueManagerHelper;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    public SmsQueueManager() {}

    @PostConstruct
    public void setupSmsQueues() {

        LogUtil.entryTrace("setupSmsQueues", LOG);

        SmsQueuePriority[] queuePriorities = SmsQueuePriority.values();

        if (primaryQueues == null) {
            primaryQueues = new HashMap<>();

            for (SmsQueuePriority qp : queuePriorities) {
                primaryQueues.put(qp, new PriorityBlockingQueue<>(INITIAL_QUEUE_CAPACITY));
            }
        }

        if (retryQueues == null) {
            retryQueues = new HashMap<>();

            for (SmsQueuePriority qp : queuePriorities) {
                retryQueues.put(qp, new PriorityBlockingQueue<>(INITIAL_QUEUE_CAPACITY));

            }
        }

        LogUtil.exitTrace("setupSmsQueues", LOG);
    }

    public void queueSms(SmsAssembly smsAssembly) {
        ArrayList<SmsAssembly> list = new ArrayList<>(1);
        list.add(smsAssembly);
        queueSms(list);
    }

    public void queueSms(List<SmsAssembly> smsAssemblyCollection) {
        // TODO Complete the implementation for filtering sms and queuing them properly
        // ?? Possibly a thread worker can be created which does the job of filtering and queueing

        smsQueueManagerHelper.moveSmsAssemblyToQueuedStatus(smsAssemblyCollection);
        moveToQueues(smsAssemblyCollection);
    }

    private void moveToQueues(List<SmsAssembly> smsAssemblies) {
        for (SmsAssembly smsAssembly : smsAssemblies) {
            SmsInAssembly smsInAssembly = new SmsInAssembly();
            smsInAssembly.updateSmsAssembly(smsAssemblyDao.find(smsAssembly.getId())); // Get updated copy from db

            primaryQueues.get(
                    translateQueuePriority(
                            smsInAssembly.getSmsAssembly().getSmsGroup().getPriority())).add(smsInAssembly);
        }
    }

    public void queueSmsToBeRetried(SmsInAssembly smsInAssembly) {
        // TODO Complete the implementation of determining the retry queue and placing the sms in the queue
    }

    public void queueDirectSms(SmsAssembly smsAssembly) {
        // TODO Complete the implementation of queuing direct SMS
    }

    public void queueDirectSmsToBeRetried() {
        // TODO Complete the implementation of queing direct SMS to be retried
    }

    public BlockingQueue<SmsInAssembly> getPrimaryQueue(SmsQueuePriority smsQueuePriority) {
        return primaryQueues.get(smsQueuePriority);
    }

    public BlockingQueue<SmsInAssembly> getRetryQueue(SmsQueuePriority smsQueuePriority) {
        return retryQueues.get(smsQueuePriority);
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
}