package com.portea.commp.smsen.domain;

public enum SmsSecondaryProcessingState {

    QUEUED(SmsPrimaryProcessingState.UNDER_PROCESS),
    LOADED(SmsPrimaryProcessingState.UNDER_PROCESS),
    QUEUED_IN_RETRY(SmsPrimaryProcessingState.UNDER_PROCESS),
    SENT_TO_GATEWAY(SmsPrimaryProcessingState.UNDER_PROCESS),
    RECEIVED_CORRELATION_ID(SmsPrimaryProcessingState.UNDER_PROCESS),
    INTERMEDIATE_STATUS_UPDATE(SmsPrimaryProcessingState.UNDER_PROCESS),
    NEVER_SENT(SmsPrimaryProcessingState.COMPLETED),
    SUCCESSFUL(SmsPrimaryProcessingState.COMPLETED),
    FAILED(SmsPrimaryProcessingState.COMPLETED);

    private final SmsPrimaryProcessingState primaryProcessingState;

    SmsSecondaryProcessingState(SmsPrimaryProcessingState primaryProcessingState) {
        this.primaryProcessingState = primaryProcessingState;
    }
}
