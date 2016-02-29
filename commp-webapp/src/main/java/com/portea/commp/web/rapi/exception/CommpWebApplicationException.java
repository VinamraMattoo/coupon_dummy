package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.MessageFormat;

public abstract class CommpWebApplicationException  extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 8538849022895057331L;
    private final Response.Status status = Response.Status.CONFLICT;

    public CommpWebApplicationException() {
        super();
    }

    public CommpWebApplicationException(String message) {
        super(message);
    }

    public CommpWebApplicationException(String message, Throwable cause) {
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