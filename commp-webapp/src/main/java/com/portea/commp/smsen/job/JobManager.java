package com.portea.commp.smsen.job;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class JobManager {

    private volatile boolean loaderJobRunning = false;

    private volatile boolean schedulerJobRunning = false;

    private volatile boolean statusSchedulerJobRunning = false;

    public JobManager() {}

    public boolean isLoaderJobRunning() {
        return loaderJobRunning;
    }

    public void setLoaderJobRunning(boolean loaderJobRunning) {
        this.loaderJobRunning = loaderJobRunning;
    }

    public boolean isSchedulerJobRunning() {
        return schedulerJobRunning;
    }

    public void setSchedulerJobRunning(boolean schedulerJobRunning) {
        this.schedulerJobRunning = schedulerJobRunning;
    }

    public boolean isStatusSchedulerJobRunning() {
        return statusSchedulerJobRunning;
    }

    public void setStatusSchedulerJobRunning(boolean statusSchedulerJobRunning) {
        this.statusSchedulerJobRunning = statusSchedulerJobRunning;
    }
}
