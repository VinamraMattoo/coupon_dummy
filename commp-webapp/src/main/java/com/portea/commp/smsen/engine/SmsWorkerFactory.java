package com.portea.commp.smsen.engine;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

public class SmsWorkerFactory {

    @Inject
    Instance<SmsWorker> workers;

    private AtomicInteger counter = new AtomicInteger();

    public SmsWorkerFactory() {}

    public SmsWorker createWorker() {
        for (SmsWorker worker : workers) {
            worker.setName(SmsWorker.class.getCanonicalName() + "." + counter.incrementAndGet());
            return worker; // return the first worker as we have only one type of worker
        }

        return null;
    }

}
