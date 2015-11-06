package com.portea.commp.smsen.engine;

import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * A processor that retrieves available SMS from designated queues (based in priority)
 * and hands them over to a <code>SmsWorker</code>
 * TODO : Should we have worker registered and when they complete they can be removed from the list
 */
@ManagedBean
public class SmsQueueProcessor implements Runnable {

    private SmsQueuePriority smsQueuePriority;
    private BlockingQueue<SmsInAssembly> queue;
    private SmsQueueProcessorManager manager;

    private Logger LOG;

    private boolean isRetryQueueProcessor;
    private String name = null;

    @Inject
    private SmsWorkerFactory smsWorkerFactory;

    @Resource
    private ManagedExecutorService executorService;

    // TODO make this count configurable
    private final static int SMS_RETRIEVAL_MAX_COUNT = 5;

    SmsQueueProcessor() {}

    @Override
    public void run() {
        LOG = LoggerFactory.getLogger(getName());

        LOG.info("Starting...");

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

        List<SmsInAssembly> smsInAssemblyList = new ArrayList<>(SMS_RETRIEVAL_MAX_COUNT);

        if (queue.size() > 1) {
            queue.drainTo(smsInAssemblyList, SMS_RETRIEVAL_MAX_COUNT);

            if (LOG.isTraceEnabled()) LOG.trace("Draining as more than one SMS. Count of drained SMS : " + smsInAssemblyList.size());
        }
        else {
            if (LOG.isTraceEnabled()) LOG.trace("Less than 2 SMS. Will execute blocking poll()");

            SmsInAssembly smsInAssembly = queue.poll(3000, TimeUnit.MILLISECONDS);

            if (smsInAssembly != null) {
                smsInAssemblyList.add(smsInAssembly);
            }
        }

        if (smsInAssemblyList.size() > 0) {

            if (LOG.isTraceEnabled()) LOG.trace("No of retrieved SMS:" + smsInAssemblyList.size());

            for (SmsInAssembly smsInAssembly : smsInAssemblyList) {
                SmsWorker smsWorker = smsWorkerFactory.createWorker();
                smsWorker.setSmsInAssembly(smsInAssembly);
                submitWorkerForProcessing(smsWorker);
                if (LOG.isTraceEnabled())  LOG.trace("Submitted an sms worker : " + smsWorker.getName());
            }

            smsInAssemblyList.clear();
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
            name = SmsQueueProcessor.class.getName()
                    + '.' + (isRetryQueueProcessor() ? "RETRY" : "PRIMARY")
                    + '.' + smsQueuePriority;
        }

        return name;
    }

    public void setSmsQueueProcessorManager(SmsQueueProcessorManager manager) {
        this.manager = manager;
    }

    private void submitWorkerForProcessing(SmsWorker smsWorker) throws InterruptedException {
        while (true) {
            try {
                executorService.submit(smsWorker);
                return; // Submission successful, return
            }
            catch(RejectedExecutionException ree) {
                // TODO this event needs to be a feedback, so that rate of new SMS being fed in the queue is regulated
                LOG.warn("Unable to submit SmsWorker for processing.");

                // Sleep for some time, so that system catches up
                Thread.currentThread().sleep(3000);
            }
        }
    }
}