package com.portea.commp.smsen.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobCleaner extends JobListenerSupport {

    private QuartzCdiJobFactory factory;

    private Logger LOG = LoggerFactory.getLogger(JobCleaner.class);

    public JobCleaner(QuartzCdiJobFactory factory) {
        this.factory = factory;
    }

    @Override
    public String getName() {
        return "Portea-Commp-Job-Cleaner";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        Job jobInstance = context.getJobInstance();
        factory.getJobs().destroy(jobInstance);
        LOG.trace("A completed job was destroyed");
    }
}
