package com.portea.cpnen.web.rapi.exception;

import com.portea.cpnen.rapi.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class CouponCreationException extends CouponWebApplicationException {
    private static final long serialVersionUID = 3129038476893576050L;
    private String errorMsg;


    public CouponCreationException(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }

    public CouponCreationException(String errorMsg, String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    public CouponCreationException(String errorMsg, String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = errorMsg;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_CREATION_FAILED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon creation failed: {0}",
                errorMsg
        );
    }
}
