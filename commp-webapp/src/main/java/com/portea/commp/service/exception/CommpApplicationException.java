package com.portea.commp.service.exception;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.MessageFormat;

public abstract class CommpApplicationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -3234191250495225364L;
    private final Response.Status status = Response.Status.CONFLICT;

    public CommpApplicationException() {
        super();
    }

    public CommpApplicationException(String message) {
        super(message);
    }

    public CommpApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public Response.Status getStatus() {
        return status;
    }

    public abstract ExceptionalCondition.Error getError();

    public abstract String getExplanatoryMessage();

    public final String getErrorRepresentation() {
        return MessageFormat.format(
                "{0}::{1}",
                getError().getErrorCode(), getError().getErrorPhrase()
        );
    }
}
