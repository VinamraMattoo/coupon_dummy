package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class InvalidRequestException extends CommpWebApplicationException {


    private static final long serialVersionUID = 8966133506485672350L;
    private String parameterName;
    private String parameterValue;

    public InvalidRequestException(String parameterName, String parameterValue) {
        super();
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public InvalidRequestException(String parameterName, Integer parameterValue) {
        super();
        this.parameterName = parameterName;
        this.parameterValue = String.valueOf(parameterValue);
    }

    public InvalidRequestException(String parameterName, String parameterValue, String message) {
        super(message);
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public InvalidRequestException(String parameterName, String parameterValue, String message, Throwable cause) {
        super(message, cause);
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.INVALID_WEB_REQUEST;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "The given value {0} for the parameter {1} is invalid::{1}|{0}",
                parameterValue,parameterName
        );
    }
}