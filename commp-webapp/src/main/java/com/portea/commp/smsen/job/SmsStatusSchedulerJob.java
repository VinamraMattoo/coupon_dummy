package com.portea.commp.smsen.job;

import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.engine.batch.SmsBatchedQueueManager;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DisallowConcurrentExecution
public class SmsStatusSchedulerJob implements Job {

    private Logger LOG = LoggerFactory.getLogger(SmsStatusSchedulerJob.class);
    
    @EJB
    private SmsEngineUtil smsEngineUtil;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject
    private SmsBatchedQueueManager smsBatchedQueueManager;

    @Asynchronous
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try{

            doWork();

        }
        catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    private void doWork() throws Exception{
        LogUtil.entryTrace("execute", LOG);

        long start = System.nanoTime();

        Integer smsToBeLoaded = smsBatchedQueueManager.getMaxSmsToLoadForStatusCheck();

        List<SmsAssembly> smsAssemblies = smsAssemblyDao.getNextSmsBatchForStatusCheck(smsToBeLoaded);

        while(true) {

            if (smsAssemblies.isEmpty()) {
                break;
            }

            List<SmsAssembly> partialList = null;

            if (smsAssemblies.size() > 50) {
                partialList = smsAssemblies.subList(0, 50);
            }
            else {
                partialList = smsAssemblies.subList(0, smsAssemblies.size());
            }

            smsBatchedQueueManager.queueSmsForStatusCheck(partialList);

            partialList.clear();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOG.error("Error", e);
        }
        long end = System.nanoTime();
        LOG.trace("SMS Load time : " +
                        TimeUnit.SECONDS.convert((end-start), TimeUnit.NANOSECONDS) +
                        " seconds"
        );
        LogUtil.exitTrace("execute", LOG);

    }

}
