package com.portea.commp.service.exception;

import java.text.MessageFormat;

public class SmsReceivedUpperLimitExceededException extends CommpApplicationException {

    private static final long serialVersionUID = 3098250562107327798L;
    private final Integer inputSize;
    private final Integer upperLimit;

    public SmsReceivedUpperLimitExceededException(Integer inputSize, Integer upperLimit) {
        this.inputSize = inputSize;
        this.upperLimit = upperLimit;
    }

    public SmsReceivedUpperLimitExceededException(Integer inputSize, Integer upperLimit, String message) {
        super(message);
        this.inputSize = inputSize;
        this.upperLimit = upperLimit;
    }

    public SmsReceivedUpperLimitExceededException(Integer inputSize, Integer upperLimit, String message, Throwable cause) {
        super(message, cause);
        this.inputSize = inputSize;
        this.upperLimit = upperLimit;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.SMS_RECEIVED_EXCEEDS_UPPER_LIMIT;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("Received sms count {0} exceeds the upper limit {1}::{0}|{1}", inputSize, upperLimit);
    }
}
