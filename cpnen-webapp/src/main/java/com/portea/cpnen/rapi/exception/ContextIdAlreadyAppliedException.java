package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class ContextIdAlreadyAppliedException extends CouponApplicationException {

    private static final long serialVersionUID = -7809929786660306042L;
    private String cdrId;

    public ContextIdAlreadyAppliedException(String cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public ContextIdAlreadyAppliedException(String cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public ContextIdAlreadyAppliedException(String cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.CONTEXT_ID_INAPPLICABLE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Context id for coupon discount request with id {0} is already set::{0}",
                cdrId
        );
    }
}
