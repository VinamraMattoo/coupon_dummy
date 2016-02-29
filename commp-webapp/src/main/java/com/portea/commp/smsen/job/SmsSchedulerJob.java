package com.portea.commp.smsen.job;

import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.ConfigTargetType;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.engine.batch.SmsBatchedQueueManager;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.quartz.DisallowConcurrentExecution;
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
@DisallowConcurrentExecution
public class SmsSchedulerJob implements Job {

    private Logger LOG = LoggerFactory.getLogger(SmsSchedulerJob.class);

    @Inject
    private SmsBatchedQueueManager smsBatchedQueueManager;


    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject
    private SmsEngineUtil smsEngineUtil;

    @Asynchronous
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            doWork();

        }
        catch (Exception e) {
            LOG.error("Error", e);
        }

    }

    private void doWork() throws Exception{
        LogUtil.entryTrace("execute", LOG);

        long start = System.nanoTime();

        Integer batchWindow = Integer.parseInt(smsEngineUtil.
                getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.NEXT_SMS_BATCH_LOAD_WINDOW,
                        null, ConfigEngineParam.NEXT_SMS_BATCH_LOAD_WINDOW.getDefaultValue()));

        Integer maxBatchLimit = smsBatchedQueueManager.getMaxSmsToLoadForSubmission();

        List<SmsAssembly> smsAssemblyList = smsAssemblyDao.getNextBatchFromAssembly(batchWindow, maxBatchLimit);

        LOG.trace("No. of sms fetched from assembly " + smsAssemblyList.size());

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

            smsBatchedQueueManager.queueSmsForSubmission(partialList);

            partialList.clear();
        }



        long end = System.nanoTime();
        LOG.trace("SMS Load time : " +
                        TimeUnit.SECONDS.convert((end-start), TimeUnit.NANOSECONDS) +
                        " seconds"
        );
        LogUtil.exitTrace("execute", LOG);

    }
}
