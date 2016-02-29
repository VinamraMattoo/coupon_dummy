package com.portea.commp.service.exception;

import java.text.MessageFormat;

public class InvalidCredentialException extends CommpApplicationException {


    private static final long serialVersionUID = 3725030561447772399L;
    private final String name;

    public InvalidCredentialException(String name) {
        this.name = name;
    }

    public InvalidCredentialException(String name, String message) {
        super(message);
        this.name = name;
    }

    public InvalidCredentialException(String name, String message, Throwable cause) {
        super(message, cause);
        this.name = name;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.INVALID_CREDENTIALS;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("The username {0} and key provided do not match::{0}", this.name);
    }
}
