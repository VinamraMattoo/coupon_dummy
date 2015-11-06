package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class MultipleExclusiveCouponsException extends CouponApplicationException {

    private static final long serialVersionUID = 3190473945558115110L;

    private String conflictingCouponCode;
    private String requestedCode;

    public MultipleExclusiveCouponsException(String conflictingCouponCode, String requestedCode) {
        super();
        this.conflictingCouponCode = conflictingCouponCode;
        this.requestedCode = requestedCode;
    }

    public MultipleExclusiveCouponsException(String conflictingCouponCode, String requestedCode, String message) {
        super(message);
        this.conflictingCouponCode = conflictingCouponCode;
        this.requestedCode = requestedCode;
    }

    public MultipleExclusiveCouponsException(String conflictingCouponCode, String requestedCode, String message, Throwable cause) {
        super(message, cause);
        this.conflictingCouponCode = conflictingCouponCode;
        this.requestedCode = requestedCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.MULTIPLE_EXCLUSIVE_COUPONS;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is exclusive and in conflict with {1}::{0}|{1}",
                requestedCode, conflictingCouponCode
        );
    }
}