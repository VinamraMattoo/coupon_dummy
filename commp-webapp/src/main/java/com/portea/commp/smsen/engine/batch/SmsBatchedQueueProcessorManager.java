package com.portea.commp.smsen.engine.batch;

import com.portea.commp.smsen.engine.SmsQueuePriority;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;

@Startup
@Singleton
@ApplicationScoped
@DependsOn("SmsBatchedQueueManager")
public class SmsBatchedQueueProcessorManager {

    private static final String EXECUTOR_SERVICE_JNDI_PREFIX = "java:jboss/ee/concurrency/executor/";
    private final Logger LOG = LoggerFactory.getLogger(SmsBatchedQueueProcessorManager.class);

    private final HashMap<SmsQueuePriority, SmsBatchedQueueProcessorManager> smsQueueProcessors;

    @Inject
    SmsBatchedQueueProcessorFactory processorFactory;

    @Inject
    SmsBatchedQueueManager smsBatchedQueueManager;

    @Resource
    ManagedThreadFactory threadFactory;

    public SmsBatchedQueueProcessorManager() {
        smsQueueProcessors = new HashMap<>(SmsQueuePriority.values().length);
    }

    @PostConstruct
    public void setupProcessors() {
        LogUtil.entryTrace("setupProcessors", LOG);

        SmsQueuePriority[] queuePriorities = SmsQueuePriority.values();
        InitialContext initialContext = null;
        try {
            initialContext = new InitialContext();

            //Creating submission queue processor.
            for (SmsQueuePriority qp : queuePriorities) {

                SmsBatchedQueueProcessor queueProcessor = processorFactory.createSmsQueueProcessor();
                queueProcessor.setSmsQueuePriority(qp);
                queueProcessor.setQueue(smsBatchedQueueManager.getSubmissionPrimaryQueue(qp));
                queueProcessor.setSmsQueueProcessorManager(this);
                queueProcessor.setExecutorService(
                        (ManagedExecutorService) initialContext.lookup(EXECUTOR_SERVICE_JNDI_PREFIX + qp.getSubmissionPoolName()));

                queueProcessor.setSmsQueuedForSubmission(true);

                Thread thread = threadFactory.newThread(queueProcessor);
                thread.setName(queueProcessor.getName());
                thread.start();
            }

            //Creating submission retry queue processor.
            for (SmsQueuePriority qp : queuePriorities) {

                SmsBatchedQueueProcessor retryQueueProcessor = processorFactory.createSmsQueueProcessor();
                retryQueueProcessor.setSmsQueuePriority(qp);
                retryQueueProcessor.setQueue(smsBatchedQueueManager.getSubmissionRetryQueue(qp));
                retryQueueProcessor.setRetryQueueProcessor();
                retryQueueProcessor.setSmsQueueProcessorManager(this);
                retryQueueProcessor.setExecutorService(
                        (ManagedExecutorService) initialContext.lookup(EXECUTOR_SERVICE_JNDI_PREFIX + qp.getSubmissionPoolName()));


                retryQueueProcessor.setSmsQueuedForSubmission(true);

                Thread thread = threadFactory.newThread(retryQueueProcessor);
                thread.setName(retryQueueProcessor.getName());
                thread.start();
            }

            //Creating Status check queue processor.
            for (SmsQueuePriority qp : queuePriorities) {

                SmsBatchedQueueProcessor queueProcessor = processorFactory.createSmsQueueProcessor();
                queueProcessor.setSmsQueuePriority(qp);
                queueProcessor.setQueue(smsBatchedQueueManager.getStatusPrimaryQueue(qp));
                queueProcessor.setSmsQueueProcessorManager(this);
                queueProcessor.setExecutorService(
                        (ManagedExecutorService) initialContext.lookup(EXECUTOR_SERVICE_JNDI_PREFIX + qp.getStatusPoolName()));


                queueProcessor.setSmsQueuedForSubmission(false);

                Thread thread = threadFactory.newThread(queueProcessor);
                thread.setName(queueProcessor.getName());
                thread.start();
            }
        } catch (NamingException e) {
            LOG.error("Error", e);
        }

        LogUtil.exitTrace("setupProcessors", LOG);
    }
}
