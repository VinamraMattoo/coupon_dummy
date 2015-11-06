package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class ExhaustedCouponException extends CouponApplicationException {

    private static final long serialVersionUID = 6380879078274462056L;

    private String couponCode;

    public ExhaustedCouponException(String couponCode) {
        super();
        this.couponCode = couponCode;
    }

    public ExhaustedCouponException(String couponCode, String message) {
        super(message);
        this.couponCode = couponCode;
    }

    public ExhaustedCouponException(String couponCode, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_EXHAUSTED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is exhausted::{0}",
                couponCode
        );
    }
}