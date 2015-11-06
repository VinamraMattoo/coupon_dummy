package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InvalidCouponException extends CouponApplicationException {

    private static final long serialVersionUID = 6380879078274462056L;

    private String couponCode;

    public InvalidCouponException(String couponCode) {
        super();
        this.couponCode = couponCode;
    }

    public InvalidCouponException(String couponCode, String message) {
        super(message);
        this.couponCode = couponCode;
    }

    public InvalidCouponException(String couponCode, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is invalid::{0}",
                couponCode
        );
    }
}