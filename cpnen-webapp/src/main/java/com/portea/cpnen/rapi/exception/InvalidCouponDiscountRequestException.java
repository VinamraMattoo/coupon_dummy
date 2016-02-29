package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InvalidCouponDiscountRequestException extends CouponApplicationException {

    private static final long serialVersionUID = -3664838406265270868L;

    private String cdrId;

    public InvalidCouponDiscountRequestException(String cdrId) {
        super();
        this.cdrId = cdrId;
    }

    public InvalidCouponDiscountRequestException(String cdrId, String message) {
        super(message);
        this.cdrId = cdrId;
    }

    public InvalidCouponDiscountRequestException(String cdrId, String message, Throwable cause) {
        super(message, cause);
        this.cdrId = cdrId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_DISCOUNT_REQUEST_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon discount request id {0} is invalid::{0}",
                cdrId
        );
    }
}