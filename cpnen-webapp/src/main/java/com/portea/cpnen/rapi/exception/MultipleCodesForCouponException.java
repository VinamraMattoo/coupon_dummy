package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class MultipleCodesForCouponException extends CouponApplicationException {

    private static final long serialVersionUID = 3190473945558115110L;

    private String conflictingCouponCode;
    private String code;

    public MultipleCodesForCouponException(String code, String conflictingCouponCode) {
        super();
        this.code = code;
        this.conflictingCouponCode = conflictingCouponCode;
    }

    public MultipleCodesForCouponException(String code, String conflictingCouponCode, String message) {
        super(message);
        this.code = code;
        this.conflictingCouponCode = conflictingCouponCode;
    }

    public MultipleCodesForCouponException(String code, String conflictingCouponCode, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.conflictingCouponCode = conflictingCouponCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.MULTIPLE_CODES_FOR_COUPON;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon codes {0} and {1} belong to same coupon::{0}|{1}",
                code, conflictingCouponCode
        );
    }
}