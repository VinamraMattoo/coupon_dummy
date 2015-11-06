package com.portea.commp.smsen.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * A job factory which is CDI aware and is setup with the Quartz scheduler. As this factory is
 * CDI aware, the jobs created also are CDI aware and can use injected resources
 */
public class QuartzCdiJobFactory implements JobFactory {

    @Inject
    @Any
    private Instance<Job> jobs;

    private static Logger LOG = LoggerFactory.getLogger(QuartzCdiJobFactory.class);

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        final JobDetail jobDetail = bundle.getJobDetail();
        final Class<? extends Job> jobClass = jobDetail.getJobClass();

        for (Job job : jobs) {
            if (job.getClass().isAssignableFrom(jobClass)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Returning job with class : " + job.getClass());
                }

                return job;
            }
        }

        throw new RuntimeException("Cannot create a job of type " + jobClass);
    }
}
