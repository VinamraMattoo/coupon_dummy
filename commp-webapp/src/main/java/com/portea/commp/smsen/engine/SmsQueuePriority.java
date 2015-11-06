package com.portea.commp.smsen.engine;

public enum SmsQueuePriority {

    /**
     * SMS that are sent to be processed through HTTp service are given this priority.
     * Such SMS are processed as soon as they are received.
     */
    SMS_QUEUE_PRIORITY_DIRECT,

    /**
     * This is a relative SMS priority. The priority of an SMS determines the quickness with which
     * it will be handled
     */
    SMS_QUEUE_PRIORITY_HIGH,

    /**
     * This is a relative SMS priority. The priority of an SMS determines the quickness with which
     * it will be handled
     */
    SMS_QUEUE_PRIORITY_MEDIUM,

    /**
     * This is a relative SMS priority. The priority of an SMS determines the quickness with which
     * it will be handled
     */
    SMS_QUEUE_PRIORITY_LOW,
}
