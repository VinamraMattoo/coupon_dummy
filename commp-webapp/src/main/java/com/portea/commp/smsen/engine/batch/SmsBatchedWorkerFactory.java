package com.portea.commp.smsen.engine.batch;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

public class SmsBatchedWorkerFactory {

    @Inject
    Instance<SmsSubmissionWorker> submissionWorkers;

    @Inject
    Instance<SmsStatusWorker> statusWorkers;

    private AtomicInteger counter = new AtomicInteger();

    public SmsBatchedWorkerFactory() {}

    public ISmsBatchWorker createWorker(boolean isSubmissionWorker) {

        Instance<? extends ISmsBatchWorker> smsBatchWorker = (isSubmissionWorker)?submissionWorkers:statusWorkers;

        for (ISmsBatchWorker worker : smsBatchWorker) {
            worker.setName(worker.getClass().getCanonicalName() + "." + counter.incrementAndGet());
            return worker; // return the first worker as we have only one type of worker
        }

        return null;
    }

}

