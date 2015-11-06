package com.portea.commp.smsen.engine;

import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;

@Startup
@Singleton
@ApplicationScoped
@DependsOn("SmsQueueManager")
public class SmsQueueProcessorManager {

    private final Logger LOG = LoggerFactory.getLogger(SmsQueueProcessorManager.class);

    private final HashMap<SmsQueuePriority, SmsQueueProcessorManager> smsQueueProcessors;

    @Inject
    SmsQueueProcessorFactory processorFactory;

    @Inject
    SmsQueueManager smsQueueManager;

    @Resource
    ManagedThreadFactory threadFactory;

    public SmsQueueProcessorManager() {
        smsQueueProcessors = new HashMap<>(SmsQueuePriority.values().length);
    }

    @PostConstruct
    public void setupProcessors() {
        LogUtil.entryTrace("setupProcessors", LOG);

        SmsQueuePriority[] queuePriorities = SmsQueuePriority.values();

        for (SmsQueuePriority qp : queuePriorities) {

            SmsQueueProcessor queueProcessor = processorFactory.createSmsQueueProcessor();
            queueProcessor.setSmsQueuePriority(qp);
            queueProcessor.setQueue(smsQueueManager.getPrimaryQueue(qp));
            queueProcessor.setSmsQueueProcessorManager(this);

            Thread thread = threadFactory.newThread(queueProcessor);
            thread.setName(queueProcessor.getName());
            thread.start();
        }

        for (SmsQueuePriority qp : queuePriorities) {

            SmsQueueProcessor retryQueueProcessor = processorFactory.createSmsQueueProcessor();
            retryQueueProcessor.setSmsQueuePriority(qp);
            retryQueueProcessor.setQueue(smsQueueManager.getRetryQueue(qp));
            retryQueueProcessor.setRetryQueueProcessor();
            retryQueueProcessor.setSmsQueueProcessorManager(this);

            Thread thread = threadFactory.newThread(retryQueueProcessor);
            thread.setName(retryQueueProcessor.getName());
            thread.start();
        }

        LogUtil.exitTrace("setupProcessors", LOG);
    }
}
