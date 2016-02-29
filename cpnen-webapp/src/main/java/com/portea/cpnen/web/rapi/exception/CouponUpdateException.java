package com.portea.cpnen.web.rapi.exception;

import com.portea.cpnen.rapi.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class CouponUpdateException extends CouponWebApplicationException {

    private static final long serialVersionUID = -685265195798204711L;
    private String errorMsg;


    public CouponUpdateException(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }

    public CouponUpdateException(String errorMsg, String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    public CouponUpdateException(String errorMsg, String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = errorMsg;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_UPDATE_FAILED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon update failed: {0}",
                errorMsg
        );
    }
}