package com.portea.commp.smsen.engine;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class SmsQueueProcessorFactory {

    @Inject
    Instance<SmsQueueProcessor> processors;

    public SmsQueueProcessorFactory() {}

    public SmsQueueProcessor createSmsQueueProcessor() {
        for (SmsQueueProcessor processor : processors) {
            return processor; // return the first processor as we have only one type of processor
        }

        return null;
    }
}
