package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class IllegalCdrApplyStateException extends CouponApplicationException {


    private static final long serialVersionUID = -8564251380916440146L;
    private String cdrId;

    public IllegalCdrApplyStateException(String cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public IllegalCdrApplyStateException(String cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public IllegalCdrApplyStateException(String cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.ILLEGAL_CDR_APPLY_STATE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon discount request with id {0} is already applied ::{0}",
                cdrId
        );
    }
}
