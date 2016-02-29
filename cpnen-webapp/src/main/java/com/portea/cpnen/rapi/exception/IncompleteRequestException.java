package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class IncompleteRequestException extends CouponApplicationException {

    private static final long serialVersionUID = 3587555760145390260L;

    private String parameterName;

    public IncompleteRequestException(String parameterName) {
        super();
        this.parameterName = parameterName;
    }

    public IncompleteRequestException(String parameterName, String message) {
        super(message);
        this.parameterName = parameterName;
    }

    public IncompleteRequestException(String parameterName, String message, Throwable cause) {
        super(message, cause);
        this.parameterName = parameterName;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.INCOMPLETE_REQUEST;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Parameter {0} is incomplete::{0}",
                parameterName
        );
    }
}