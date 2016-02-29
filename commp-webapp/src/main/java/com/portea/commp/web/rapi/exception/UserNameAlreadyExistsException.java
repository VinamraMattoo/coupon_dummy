package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class UserNameAlreadyExistsException extends CommpWebApplicationException {
    private static final long serialVersionUID = -6455050516916229469L;
    private final String name;

    public UserNameAlreadyExistsException(String name) {
        this.name = name;
    }

    public UserNameAlreadyExistsException(String name, String message) {
        super(message);
        this.name = name;
    }

    public UserNameAlreadyExistsException(String name, String message, Throwable cause) {
        super(message, cause);
        this.name = name;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.USER_NAME_ALREADY_EXISTS;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("A user account with username {0} already exists::{0}", name);
    }
}
