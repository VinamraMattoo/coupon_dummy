package com.portea.commp.smsen.domain;

public enum SmsSecondaryProcessingState {

    //Submission
    //Failure
    NEVER_SENT_FOR_SUBMISSION(SmsPrimaryProcessingState.LOADED_FOR_CREATION, NormalizedSmsStatus.FAILURE),
    NEVER_SENT_DURING_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_COMPLETED, NormalizedSmsStatus.FAILURE),
    FAILED_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_COMPLETED, NormalizedSmsStatus.FAILURE),

    //Pending
    QUEUED_FOR_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    LOADED_FOR_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    SUBMITTED_TO_GATEWAY(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    QUEUED_IN_RETRY_FOR_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    SENDING_TO_GATEWAY_FOR_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    SENT_TO_GATEWAY_FOR_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    INTERMEDIATE_SUBMISSION_UPDATE(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    RECEIVED_CORRELATION_ID(SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, NormalizedSmsStatus.PENDING),

    //Success
    SUCCESSFUL_SUBMISSION(SmsPrimaryProcessingState.SUBMISSION_COMPLETED, NormalizedSmsStatus.PENDING),

    //Status check
    //Success
    SUCCESSFUL_DELIVERY(SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED, NormalizedSmsStatus.DELIVERED),

    //Failure
    NEVER_SENT_DURING_STATUS_CHECK(SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS, NormalizedSmsStatus.FAILURE),
    STATUS_CHECK_TIME_OUT(SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED, NormalizedSmsStatus.FAILURE),
    FAILED_TO_DELIVER(SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED, NormalizedSmsStatus.FAILURE),

    //Pending
    READY_FOR_STATUS_CHECK(SmsPrimaryProcessingState.SUBMISSION_COMPLETED, NormalizedSmsStatus.PENDING),
    QUEUED_FOR_STATUS_CHECK(SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    LOADED_FOR_STATUS_CHECK(SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS, NormalizedSmsStatus.PENDING),
    INTERMEDIATE_STATUS_UPDATE(SmsPrimaryProcessingState.STATUS_CHECK_UNDER_PROCESS, NormalizedSmsStatus.PENDING),


    ;
    private final SmsPrimaryProcessingState primaryProcessingState;
    private final NormalizedSmsStatus normalizedStatus;

    SmsSecondaryProcessingState(SmsPrimaryProcessingState primaryProcessingState, NormalizedSmsStatus normalizedStatus) {
        this.normalizedStatus = normalizedStatus;
        this.primaryProcessingState = primaryProcessingState;
    }

    public NormalizedSmsStatus getNormalizedStatus() {
        return normalizedStatus;
    }
}
