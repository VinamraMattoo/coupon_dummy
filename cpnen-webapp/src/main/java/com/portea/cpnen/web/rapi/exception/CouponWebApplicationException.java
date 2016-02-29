package com.portea.cpnen.web.rapi.exception;

import com.portea.cpnen.rapi.exception.ExceptionalCondition;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

public abstract class CouponWebApplicationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -6705209164692737047L;
    private final Response.Status status = Response.Status.CONFLICT;

    public CouponWebApplicationException() {
        super();
    }

    public CouponWebApplicationException(String message) {
        super(message);
    }

    public CouponWebApplicationException(String message, Throwable cause) {
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

