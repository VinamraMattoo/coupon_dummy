package com.portea.commp.smsen.gw;

/**
 * A type that denotes success scenarios when interacting with an SMS gateway.
 */
public enum GssSuccessType implements GatewaySmsStatusType {

    /**
     * Denotes normal success scenario
     */
    SUCCESS_COMPLETED,

    SUCCESS_IN_PROGRESS;

    @Override
    public boolean isSuccess() {
        return true;
    }

}
