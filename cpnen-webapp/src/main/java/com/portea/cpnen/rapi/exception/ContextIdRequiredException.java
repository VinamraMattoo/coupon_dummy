package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class ContextIdRequiredException extends CouponApplicationException{

    private static final long serialVersionUID = 547191480564334880L;
    private String cdrId;

    public ContextIdRequiredException(String cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public ContextIdRequiredException(String cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public ContextIdRequiredException(String cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.CONTEXT_ID_REQUIRED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Context id for coupon discount request with id {0} is needed to commit the request::{0}",
                cdrId
        );
    }
}