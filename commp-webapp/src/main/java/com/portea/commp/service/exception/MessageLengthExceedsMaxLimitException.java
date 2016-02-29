package com.portea.commp.service.exception;

import java.text.MessageFormat;

public class MessageLengthExceedsMaxLimitException extends CommpApplicationException {

    private final Integer messageLength;
    private final Integer maxMessageLength;

    public MessageLengthExceedsMaxLimitException(Integer messageLength, Integer maxMessageLength) {
        this.messageLength = messageLength;
        this.maxMessageLength = maxMessageLength;
    }

    public MessageLengthExceedsMaxLimitException(Integer messageLength, Integer maxMessageLength, String paramName, String message) {
        super(message);
        this.messageLength = messageLength;
        this.maxMessageLength = maxMessageLength;
    }

    public MessageLengthExceedsMaxLimitException(Integer messageLength, Integer maxMessageLength,
                                                 String paramName, String message, Throwable cause) {
        super(message, cause);
        this.messageLength = messageLength;
        this.maxMessageLength = maxMessageLength;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.MESSAGE_LENGTH_EXCEEDS_MAX_LIMIT;
    }

    @Override
    public String getExplanatoryMessage() {

        return MessageFormat.format("The length of message at {0} exceeds upper limit of {1} characters::{0}|{1}",
                messageLength, maxMessageLength);
    }
}
