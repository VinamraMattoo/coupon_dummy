package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class UserAlreadyInStateException extends CommpWebApplicationException {

    private static final long serialVersionUID = -3381320418484207954L;
    public static final String ACTIVE = "active";
    public static final String DEACTIVATED = "deactivated";
    private final String name;
    private final String state;

    public UserAlreadyInStateException(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public UserAlreadyInStateException(String name, String state, String message) {
        super(message);
        this.name = name;
        this.state = state;

    }

    public UserAlreadyInStateException(String name, String state, String message, Throwable cause) {
        super(message, cause);
        this.name = name;
        this.state = state;

    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.USER_ALREADY_IN_STATE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("User {0} is already {1}::{0}|{1}", name, state);
    }
}
