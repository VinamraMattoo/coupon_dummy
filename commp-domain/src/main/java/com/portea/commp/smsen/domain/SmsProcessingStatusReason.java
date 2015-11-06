package com.portea.commp.smsen.domain;

public enum SmsProcessingStatusReason {

    GATEWAY_DND(SmsSecondaryProcessingState.FAILED,"Gateway User Do not Disturb"),
    GATEWAY_TIME_OUT(SmsSecondaryProcessingState.FAILED,"Gateway Time out");

    private final SmsSecondaryProcessingState secondaryProcessingState;
    private final String reason;

    SmsProcessingStatusReason(SmsSecondaryProcessingState secondaryProcessingState, String reason){
        this.secondaryProcessingState = secondaryProcessingState;
        this.reason = reason;
    }

}
