package com.portea.cpnen.domain;

public enum ContextType {

    /**
     * This applies to subscription context, If a coupon context is subscription it can be applied
     * during subscription as well as appointment.
     */
    SUBSCRIPTION,

    /**
     *This applies to appointment context. If a coupon context is appointment it is only applicable
     * during appointment.
     */
    APPOINTMENT,

    ;
}
