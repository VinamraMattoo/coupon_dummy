package com.portea.commp.smsen.gw;

public enum GssUnknownType implements GatewaySmsStatusType {

    UNKNOWN;

    @Override
    public boolean isSuccess() {
        return false;
    }
}
