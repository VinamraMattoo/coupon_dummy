package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class CouponDiscountRequestTimeoutException extends CouponApplicationException{

    private int cdrId;

    public CouponDiscountRequestTimeoutException(int cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public CouponDiscountRequestTimeoutException(int cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public CouponDiscountRequestTimeoutException(int cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_DISCOUNT_REQUEST_TIMEOUT;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon discount request id {0} has timed-out::{0}",
                cdrId

        );
    }
}
