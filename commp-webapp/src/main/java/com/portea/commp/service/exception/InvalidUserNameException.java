package com.portea.commp.service.exception;

import java.text.MessageFormat;

public class InvalidUserNameException extends CommpApplicationException {

    private static final long serialVersionUID = 6617512931952770088L;
    private final String name;

    public InvalidUserNameException(String name) {
        this.name = name;
    }

    public InvalidUserNameException(String name, String message) {
        super(message);
        this.name = name;
    }

    public InvalidUserNameException(String name, String message, Throwable cause) {
        super(message, cause);
        this.name = name;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.INVALID_USER_NAME;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("Username {0} is not recognized::{0}", this.name);
    }
}
