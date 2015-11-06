package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InactiveCouponException extends CouponApplicationException {

    private static final long serialVersionUID = -8445692252685443547L;

    private String couponCode;

    public InactiveCouponException(String couponCode) {
        super();
        this.couponCode = couponCode;
    }

    public InactiveCouponException(String couponCode, String message) {
        super(message);
        this.couponCode = couponCode;
    }

    public InactiveCouponException(String couponCode, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_INACTIVE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is deactivated::{0}",
                couponCode
        );
    }
}