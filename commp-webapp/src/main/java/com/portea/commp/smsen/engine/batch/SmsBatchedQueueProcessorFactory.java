package com.portea.commp.smsen.engine.batch;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class SmsBatchedQueueProcessorFactory {

    @Inject
    Instance<SmsBatchedQueueProcessor> processors;

    public SmsBatchedQueueProcessorFactory() {}

    public SmsBatchedQueueProcessor createSmsQueueProcessor() {
        for (SmsBatchedQueueProcessor processor : processors) {
            return processor; // return the first processor as we have only one type of processor
        }

        return null;
    }
}
