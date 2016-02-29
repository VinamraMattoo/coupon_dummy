package com.portea.commp.smsen.engine.batch;

import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.smsen.domain.ConfigTargetType;
import com.portea.commp.smsen.engine.SmsQueuePriority;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * A processor that retrieves available SMS from designated queues (based in priority)
 * and hands them over to a <code>SmsSubmissionWorker</code>
 * TODO : Should we have worker registered and when they complete they can be removed from the list
 */
@ManagedBean
public class SmsBatchedQueueProcessor implements Runnable {

    private SmsQueuePriority smsQueuePriority;
    private BlockingQueue<SmsInAssembly> queue;
    private SmsBatchedQueueProcessorManager manager;

    private Logger LOG;

    private boolean isRetryQueueProcessor;
    private String name = null;

    @Inject
    private SmsBatchedWorkerFactory smsBatchedWorkerFactory;

    @EJB
    private SmsEngineUtil smsEngineUtil;

    private ManagedExecutorService executorService;

    private Integer smsBatchSize;
    private boolean smsQueuedForSubmission;

    public SmsBatchedQueueProcessor() {}

    @Override
    public void run() {
        LOG = LoggerFactory.getLogger(getName());

        LOG.info("Starting...");

        //TODO this configuration has to be updated when a change happens in config param value.
        //can all the configurations be in one model class?
        this.smsBatchSize = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                ConfigEngineParam.SMS_SUBMISSION_WORKER_BATCH_MAX_SIZE, null, ConfigEngineParam.SMS_SUBMISSION_WORKER_BATCH_MAX_SIZE.getDefaultValue()));

        while(false == Thread.currentThread().isInterrupted()) {
            try {
                doWork();
            } catch (InterruptedException e) {
                // TODO Cleanup thread and exit. Inform the ProcessorManager
                LOG.warn("Caught Interrupted Exception");
            }
        }
    }

    private void doWork() throws InterruptedException {
        LogUtil.entryTrace("doWork", LOG);

        List<SmsInAssembly> smsInAssemblyList = new ArrayList<>(smsBatchSize);

        if (queue.size() > 1) {
            queue.drainTo(smsInAssemblyList, smsBatchSize);

            if (LOG.isTraceEnabled()) LOG.trace("Draining as more than one SMS. Count of drained SMS : " + smsInAssemblyList.size());
        }
        else {
            if (LOG.isTraceEnabled()) LOG.trace("Less than 2 SMS. Will execute blocking poll()");

            SmsInAssembly smsInAssembly = queue.poll(3000, TimeUnit.MILLISECONDS);

            if (smsInAssembly != null) {
                queue.drainTo(smsInAssemblyList, smsBatchSize);
                smsInAssemblyList.add(smsInAssembly);
            }
        }

        if (smsInAssemblyList.size() > 0) {

            if (LOG.isTraceEnabled()) LOG.trace("No of retrieved SMS:" + smsInAssemblyList.size());

            ISmsBatchWorker smsBatchedWorker = smsBatchedWorkerFactory.createWorker(isSmsQueuedForSubmission());
            smsBatchedWorker.setSmsInAssembly(smsInAssemblyList);
            if (LOG.isTraceEnabled())  LOG.trace("Submitted an sms worker : " + smsBatchedWorker.getName());
            submitWorkerForProcessing(smsBatchedWorker);

        }

        Thread.yield(); // This worker thread is trying to be nice by yielding

        LogUtil.exitTrace("doWork", LOG);
    }

    public BlockingQueue<SmsInAssembly> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<SmsInAssembly> queue) {

        if (this.queue != null) {
            throw new IllegalStateException("Queue has already been set");
        }

        if (queue == null) {
            throw new IllegalArgumentException("Queue to be set cannot be null");
        }

        this.queue = queue;
    }

    public SmsQueuePriority getSmsQueuePriority() {
        return smsQueuePriority;
    }

    public void setSmsQueuePriority(SmsQueuePriority smsQueuePriority) {

        if (this.smsQueuePriority != null) {
            throw new IllegalStateException("Queue Priority to be processed has already been set");
        }

        if (smsQueuePriority == null) {
            throw new IllegalArgumentException("Queue Priority to be set cannot be null");
        }

        this.smsQueuePriority = smsQueuePriority;
    }

    public void setRetryQueueProcessor() {
        this.isRetryQueueProcessor = true;
    }

    public boolean isRetryQueueProcessor() {
        return this.isRetryQueueProcessor;
    }

    public String getName() {
        if (name == null) {
            // Using dot as separator as it plays nicely (to model hierarchy) with log settings
            name = SmsBatchedQueueProcessor.class.getName()
                    + '.' + (isRetryQueueProcessor() ? "RETRY" : "PRIMARY")
                    + '.' + smsQueuePriority;
        }

        return name;
    }

    public void setSmsQueueProcessorManager(SmsBatchedQueueProcessorManager manager) {
        this.manager = manager;
    }

    private void submitWorkerForProcessing(ISmsBatchWorker runnable) throws InterruptedException {
        while (true) {
            try {
                executorService.submit(runnable);
                return; // Submission successful, return
            }
            catch(RejectedExecutionException ree) {
                // TODO this event needs to be a feedback, so that rate of new SMS being fed in the queue is regulated
                LOG.warn("Unable to submit "+getName()+" for processing.");

                // Sleep for some time, so that system catches up
                Thread.currentThread().sleep(3000);
            }
        }
    }

    public void setSmsQueuedForSubmission(boolean smsQueuedForSubmission) {
        this.smsQueuedForSubmission = smsQueuedForSubmission;
    }

    public boolean isSmsQueuedForSubmission() {
        return smsQueuedForSubmission;
    }

    public void setExecutorService(ManagedExecutorService executorService) {
        this.executorService = executorService;
    }
}
