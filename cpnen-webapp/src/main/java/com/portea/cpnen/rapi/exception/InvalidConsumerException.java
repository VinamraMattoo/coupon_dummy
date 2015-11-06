package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InvalidConsumerException extends CouponApplicationException {

    private static final long serialVersionUID = -6210016666598067541L;

    private String userId;

    public InvalidConsumerException(String userId) {
        super();
        this.userId = userId;
    }

    public InvalidConsumerException(String userId, String message) {
        super(message);
        this.userId = userId;
    }

    public InvalidConsumerException(String userId, String message, Throwable cause) {
        super(message, cause);
        this.userId = userId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.CONSUMER_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Consumer id {0} is invalid::{0}",
                userId
        );
    }
}