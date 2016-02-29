package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class IllegalCdrCancelStateException extends CouponApplicationException {

    private static final long serialVersionUID = -4032330256602216153L;
    private String cdrId;

    public IllegalCdrCancelStateException(String cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public IllegalCdrCancelStateException(String cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public IllegalCdrCancelStateException(String cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.ILLEGAL_CDR_CANCEL_STATE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon discount request with id {0} is already canceled::{0}",
                cdrId
        );
    }
}
