package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class RequestAlreadyCompletedException extends CouponApplicationException {

    private static final long serialVersionUID = -1508183332302038161L;

    private String couponDiscountRequestUri;

    public RequestAlreadyCompletedException(String couponDiscountRequestUri) {
        super();
        this.couponDiscountRequestUri = couponDiscountRequestUri;
    }

    public RequestAlreadyCompletedException(String couponDiscountRequestUri, String message) {
        super(message);
        this.couponDiscountRequestUri = couponDiscountRequestUri;
    }

    public RequestAlreadyCompletedException(String couponDiscountRequestUri, String message, Throwable cause) {
        super(message, cause);
        this.couponDiscountRequestUri = couponDiscountRequestUri;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.REQUEST_ALREADY_COMPLETED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon discount request already marked completed::{0}",
                couponDiscountRequestUri
        );
    }
}