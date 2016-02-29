package com.portea.commp.smsen.gw;

/**
 * A type that denotes various failure scenarios
 */
public enum GssFailureType implements GatewaySmsStatusType {

    FAILURE_CARRIER,
    FAILURE_EXPIRED,
    FAILURE_SYNTAX,
    FAILURE_DELETED,
    FAILURE_UNDELIVERED,
    FAILURE_GENERAL,
    FAILURE_REJECTED,
    FAILURE_DND
    ;

    @Override
    public boolean isSuccess() {
        return false;
    }
}
