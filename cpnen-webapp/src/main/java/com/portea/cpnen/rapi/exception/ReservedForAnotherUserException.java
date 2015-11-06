package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class ReservedForAnotherUserException extends CouponApplicationException {

    private static final long serialVersionUID = -4073286903124563425L;

    private String couponCode;

    public ReservedForAnotherUserException(String couponCode) {
        super();
        this.couponCode = couponCode;
    }

    public ReservedForAnotherUserException(String couponCode, String message) {
        super(message);
        this.couponCode = couponCode;
    }

    public ReservedForAnotherUserException(String couponCode, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.RESERVED_FOR_ANOTHER_USER;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is reserved for another user::{0}",
                couponCode
        );
    }
}