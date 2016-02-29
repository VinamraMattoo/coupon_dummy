package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class IllegalCdrCommitStateException extends CouponApplicationException {

    private static final long serialVersionUID = 5316331712264667371L;
    private String cdrId;

    public IllegalCdrCommitStateException(String cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public IllegalCdrCommitStateException(String cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public IllegalCdrCommitStateException(String cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.ILLEGAL_CDR_COMMIT_STATE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon discount request with id {0} should be applied to be committed::{0}",
                cdrId
        );
    }
}

