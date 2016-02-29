package com.portea.commp.service.exception;

import java.text.MessageFormat;

public class MessageEmptyException extends CommpApplicationException {

    private static final long serialVersionUID = 6464113699404941517L;
    private final String paramName;

    public MessageEmptyException(String paramName) {
        this.paramName = paramName;
    }

    public MessageEmptyException(String paramName, String message) {
        super(message);
        this.paramName = paramName;
    }

    public MessageEmptyException(String paramName, String message, Throwable cause) {
        super(message, cause);
        this.paramName = paramName;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.MESSAGE_IS_EMPTY;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("The message at {0} is empty::{0}", paramName);
    }
}
