package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class IncompleteRequestException extends CommpWebApplicationException {


    private static final long serialVersionUID = -3268323484107336809L;
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
        return ExceptionalCondition.Error.INCOMPLETE_WEB_REQUEST;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Parameter {0} is incomplete::{0}",
                parameterName
        );
    }
}
