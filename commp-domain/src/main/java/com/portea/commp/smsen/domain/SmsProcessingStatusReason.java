package com.portea.commp.smsen.domain;

public enum SmsProcessingStatusReason {

    INVALID_PHONE_NUMBER(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Phone number not valid"),

    DND_USER(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "User DND"),

    MESSAGE_HAS_UNACCEPTED_BRAND_NAME(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Message contains unacceptable brand name"),

    CANNOT_SEND_SMS_TO_BRAND(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Cannot send sms to brand"),

    SMS_TYPE_IN_COOLING_PERIOD(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Cooling period applicable for sms type"),

    SMS_MESSAGE_CONTENT_IN_COOLING_PERIOD(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Cooling period applicable for message content"),

    SMS_THROTTLING_APPLIED(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Sms throttling applied"),

    RETRY_COUNT_LIMIT_EXCEEDED(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Retry count limit exceeded"),

    SMS_EXPIRED(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Sms is expired"),

    LOADED_TO_WORKER(SmsSecondaryProcessingState.LOADED_FOR_SUBMISSION, "Loaded to worker"),

    QUEUE_COUNT_UPDATE(SmsSecondaryProcessingState.LOADED_FOR_SUBMISSION, "Queue count update"),

    SENT_TO_GATEWAY(SmsSecondaryProcessingState.SENT_TO_GATEWAY_FOR_SUBMISSION, "Sent to gateway"),

    RECEIVED_CORRELATION_ID(SmsSecondaryProcessingState.RECEIVED_CORRELATION_ID, "Received correlation id"),

    FAILED_TO_RECEIVE_CORRELATION_ID(SmsSecondaryProcessingState.FAILED_SUBMISSION, "Failed to receive correlation id"),

    GATEWAY_DND(SmsSecondaryProcessingState.FAILED_SUBMISSION,"Gateway User Do not Disturb"),

    GATEWAY_TIME_OUT(SmsSecondaryProcessingState.FAILED_SUBMISSION,"Gateway Time out"),

    GATEWAY_RESPONSE_TIME_OUT_LIMIT_EXCEEDED(SmsSecondaryProcessingState.FAILED_SUBMISSION, "Sms status gateway response time out limit exceeded"),

    QUERY_RETRY_COUNT_LIMIT_EXCEEDED(SmsSecondaryProcessingState.FAILED_SUBMISSION, "Sms status query retry count limit exceeded"),

    SENDING_TO_GATEWAY(SmsSecondaryProcessingState.SENDING_TO_GATEWAY_FOR_SUBMISSION, "Sms sending to gateway"),

    FAILED_TO_RECEIVE_SMS_STATUS(SmsSecondaryProcessingState.INTERMEDIATE_STATUS_UPDATE, "Failed to receive sms status"),

    SUCCESSFULLY_SENT(SmsSecondaryProcessingState.SUCCESSFUL_SUBMISSION, "Successfully delivered"),

    GATEWAY_MAPPING_NOT_FOUND(SmsSecondaryProcessingState.FAILED_SUBMISSION, "Gateway mapping not found"),

    MESSAGE_BAD_FORMAT(SmsSecondaryProcessingState.FAILED_SUBMISSION, "Bad message Format"),

    LOADED_FOR_STATUS_CHECK(SmsSecondaryProcessingState.LOADED_FOR_STATUS_CHECK, "Loaded for status check"),

    SUCCESSFUL_SUBMISSION(SmsSecondaryProcessingState.SUCCESSFUL_SUBMISSION, "successfully submitted"),

    READY_FOR_STATUS_CHECK(SmsSecondaryProcessingState.READY_FOR_STATUS_CHECK, "Waiting for processor to pickup for status check"),

    MESSAGE_HAS_PLACEHOLDER_TOKEN(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Message contains place holder"),

    INTERMEDIATE_STATUS_UPDATE(SmsSecondaryProcessingState.INTERMEDIATE_STATUS_UPDATE, "Intermediate status update"),

    IO_EXCEPTION(SmsSecondaryProcessingState.INTERMEDIATE_STATUS_UPDATE, "Io exception"),

    SUBMITTED_TO_GATEWAY(SmsSecondaryProcessingState.LOADED_FOR_SUBMISSION,"Sms submitted to gateway" ),

    UPDATE_SUBMISSION_TRIAL_COUNT(SmsSecondaryProcessingState.LOADED_FOR_SUBMISSION,"Updating submission trial count" ),

    COOLING_PERIOD_VALIDATION_FAILED(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Cooling period validation failed"),

    EXHAUSTED_COOLING_PERIOD_MAX_CHECK_COUNT(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION,
            "PersistenceException occurred more than a specified number of times while check for smsSentCoolingPeriodData"),

    SMS_THROTTLING_VALIDATION_FAILED(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION, "Sms throttling validation failed"),

    EXHAUSTED_SMS_THROTTLING_MAX_CHECK_COUNT(SmsSecondaryProcessingState.NEVER_SENT_DURING_SUBMISSION,
            "PersistenceException/OptimisticLockException occurred more than a specified number of times while check for userSmsThrottlingData"),

    ;

    private final SmsSecondaryProcessingState secondaryProcessingState;
    private final String reason;

    SmsProcessingStatusReason(SmsSecondaryProcessingState secondaryProcessingState, String reason){
        this.secondaryProcessingState = secondaryProcessingState;
        this.reason = reason;
    }

    public SmsSecondaryProcessingState getSecondaryProcessingState() {
        return secondaryProcessingState;
    }

    public String status() {
        return getReason();
    }

    public String getReason() {
        return reason;
    }
}
