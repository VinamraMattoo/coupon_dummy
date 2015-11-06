package com.portea.commp.smsen.job;

import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.dao.SmsQueueDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsQueue;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A job which retrieves a new batch of SMS to be sent from the queue in the database.
 * The retrieved SMS are moved to assembly for processing.
 */
public class SmsLoaderJob implements Job {

    private static Logger LOG = LoggerFactory.getLogger(SmsLoaderJob.class);

    @Inject @JpaDao
    private SmsQueueDao smsQueueDao;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject
    private JobManager jobManager;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LogUtil.entryTrace("execute", LOG);

        if (jobManager.isLoaderJobRunning()) {
            LogUtil.exitTrace("execute", LOG, "Another job instance is running");
            return;
        }

        jobManager.setLoaderJobRunning(true);
        long start = System.nanoTime();

        // TODO Write code to adapt to current load which influences number of new SMS being loaded
        List<SmsQueue> smsQueueList = smsQueueDao.getNextBatchFromQueue(15, 20000);

        if (LOG.isDebugEnabled()) LOG.debug("No. of sms fetched " + smsQueueList.size());

        while(true) {

            if (smsQueueList.isEmpty()) {
                break;
            }

            List<SmsQueue> partialList = null;

            if (smsQueueList.size() > 50) {
                partialList = smsQueueList.subList(0, 49);
            }
            else {
                partialList = smsQueueList;
            }

            for (SmsQueue smsQueue : partialList) {
                smsAssemblyDao.create(createNewSmsAssembly(smsQueue));
            }

            smsQueueDao.delete(partialList);

            partialList.clear();
        }

        long end = System.nanoTime();
        if (LOG.isDebugEnabled()) LOG.debug("SMS Load time : " +
                TimeUnit.SECONDS.convert((end-start), TimeUnit.NANOSECONDS) +
                " seconds"
        );
        LogUtil.exitTrace("execute", LOG);
        jobManager.setLoaderJobRunning(false);
    }

    private SmsAssembly createNewSmsAssembly(final SmsQueue smsQueue) {

        SmsAssembly smsAssembly = new SmsAssembly();

        smsAssembly.setBrand(smsQueue.getBrand());
        smsAssembly.setCreatedOn(smsQueue.getCreatedOn());
        smsAssembly.setMessage(smsQueue.getMessage());

        smsAssembly.setUser(smsQueue.getUser());
        smsAssembly.setReceiverType(smsQueue.getReceiverType());
        smsAssembly.setMobileNumber(smsQueue.getMobileNumber());

        smsAssembly.setCountryCode(smsQueue.getCountryCode());
        smsAssembly.setScheduledTime(smsQueue.getScheduledTime());
        smsAssembly.setSendBefore(smsQueue.getSendBefore());

        smsAssembly.setScheduledId(smsQueue.getScheduledId());
        smsAssembly.setScheduledTimeZone(smsQueue.getScheduledTimeZone());
        smsAssembly.setScheduledType(smsQueue.getScheduledType());

        smsAssembly.setSmsGroup(smsQueue.getSmsGroup());
        smsAssembly.setSmsTemplate(smsQueue.getSmsTemplate());

        smsAssembly.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.CREATED);

        return smsAssembly;
    }
}