package com.portea.commp.smsen.domain;

/**
 * Specifies the various types of Cooling Period unit. A cooling period specifies the duration within which a repeating
 * SMS that matches an SMS sent earlier to a user by message content or type of SMS, is dropped by the system.
 */
public enum CoolingPeriodUnit {

    /**
     * A calendar day is unit of time between 12:00:00 AM & 11:59:59 PM. The calendar day is applied in the specified
     * time zone.
     *
     * The cooling period can be specified in terms of calendar days which will result in limitation of an SMS
     * being sent once during the no. of calendar days specified to a user. Any SMS that matches previously sent SMS by
     * message content or the type of SMS, will be dropped by the system if its scheduled time lies within the remaining
     * duration of the specified calendar days.
     */
    CALENDAR_DAY,

    /**
     * Absolute period is unit of time which can range from few milliseconds onwards.
     *
     * Absolute period specifies the duration that must elapse after sending an SMS, after which another SMS that matches
     * by message content or type of SMS, can be sent to the same user. Any SMS that matches previously sent SMS by message
     * content or the type of SMS will be dropped by the system if its scheduled time lies within the specified absolute
     * period.
     */
    ABSOLUTE_PERIOD
}