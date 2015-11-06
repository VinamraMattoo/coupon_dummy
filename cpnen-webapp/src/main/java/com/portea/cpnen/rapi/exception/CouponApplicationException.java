package com.portea.cpnen.rapi.exception;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

public abstract class CouponApplicationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 4448697508094008037L;
    private final Response.Status status = Response.Status.CONFLICT;
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");

    public CouponApplicationException() {
        super();
    }

    public CouponApplicationException(String message) {
        super(message);
    }

    public CouponApplicationException(String message, Throwable cause) {
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
