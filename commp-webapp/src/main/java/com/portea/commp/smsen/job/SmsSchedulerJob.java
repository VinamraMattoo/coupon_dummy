package com.portea.commp.smsen.job;

import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsQueue;
import com.portea.commp.smsen.engine.SmsQueueManager;
import com.portea.commp.smsen.engine.SmsQueuePriority;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Asynchronous;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A job which retrieves a new batch of SMS to be queued for processing. The queued SMS are
 */
public class SmsSchedulerJob implements Job {

    private Logger LOG = LoggerFactory.getLogger(SmsSchedulerJob.class);

    @Inject
    private SmsQueueManager smsQueueManager;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject
    private JobManager jobManager;

    @Asynchronous
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LogUtil.entryTrace("execute", LOG);

        if (jobManager.isSchedulerJobRunning()) {
            LogUtil.exitTrace("execute", LOG, "Another job instance is running");
            return;
        }

        jobManager.setSchedulerJobRunning(true);
        long start = System.nanoTime();

        List<SmsAssembly> smsAssemblyList = smsAssemblyDao.getNextBatchFromAssembly(5, 500);

        if (LOG.isDebugEnabled()) LOG.debug("No. of sms fetched from assembly " + smsAssemblyList.size());

        while(true) {

            if (smsAssemblyList.isEmpty()) {
                break;
            }

            List<SmsAssembly> partialList = null;

            if (smsAssemblyList.size() > 50) {
                partialList = smsAssemblyList.subList(0, 50);
            }
            else {
                partialList = smsAssemblyList.subList(0, smsAssemblyList.size());
            }

            smsQueueManager.queueSms(partialList);

            partialList.clear();
        }



        long end = System.nanoTime();
        if (LOG.isDebugEnabled()) LOG.debug("SMS Load time : " +
                        TimeUnit.SECONDS.convert((end-start), TimeUnit.NANOSECONDS) +
                        " seconds"
        );
        LogUtil.exitTrace("execute", LOG);
        jobManager.setSchedulerJobRunning(false);
    }
}
