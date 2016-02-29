package com.portea.commp.smsen.engine;

public enum SmsQueuePriority {

    /**
     * SMS that are sent to be processed through HTTp service are given this priority.
     * Such SMS are processed as soon as they are received.
     */
    SMS_QUEUE_PRIORITY_DIRECT("Submission-Direct", "Status-Direct"),

    /**
     * This is a relative SMS priority. The priority of an SMS determines the quickness with which
     * it will be handled
     */
    SMS_QUEUE_PRIORITY_HIGH("Submission-High", "Status-High"),

    /**
     * This is a relative SMS priority. The priority of an SMS determines the quickness with which
     * it will be handled
     */
    SMS_QUEUE_PRIORITY_MEDIUM("Submission-Medium", "Status-Medium"),

    /**
     * This is a relative SMS priority. The priority of an SMS determines the quickness with which
     * it will be handled
     */
    SMS_QUEUE_PRIORITY_LOW("Submission-Low", "Status-Low"),

    ;
    /**
     * This refers to the managed executor service thread pool name.
     */
    private final String submissionPool;
    private final String statusPool;

    SmsQueuePriority(String submissionPool, String statusPool) {
        this.submissionPool = submissionPool;
        this.statusPool = statusPool;
    }

    public String getSubmissionPoolName() {
        return submissionPool;
    }

    public String getStatusPoolName() {
        return statusPool;
    }
}
