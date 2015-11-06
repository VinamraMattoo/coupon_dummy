package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class CouponCodeTimeoutException extends CouponApplicationException {

    private static final long serialVersionUID = 7503155243617947051L;

    private String couponCode;

    public CouponCodeTimeoutException(String couponCode) {
        super();
        this.couponCode = couponCode;
    }

    public CouponCodeTimeoutException(String couponCode, String message) {
        super(message);
        this.couponCode = couponCode;
    }

    public CouponCodeTimeoutException(String couponCode, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_CODE_TIMEOUT;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} has timed-out::{0}",
                couponCode
        );
    }
}