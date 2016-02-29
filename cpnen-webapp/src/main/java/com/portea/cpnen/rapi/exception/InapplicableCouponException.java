package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InapplicableCouponException extends CouponApplicationException {

    private static final long serialVersionUID = -4387927316003189046L;

    private String couponCode;
    private String productId;
    private String productType;

    public InapplicableCouponException(String couponCode, String productId, String productType) {
        super();
        this.couponCode = couponCode;
        this.productId = productId;
        this.productType = productType;
    }

    public InapplicableCouponException(String couponCode, String productId, String productType, String message) {
        super(message);
        this.couponCode = couponCode;
        this.productId = productId;
        this.productType = productType;
    }

    public InapplicableCouponException(String couponCode, String productId, String productType, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.productId = productId;
        this.productType = productType;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_INAPPLICABLE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is not applicable on the product {1} of type {2}::{0}|{1}|{2}",
                couponCode, productId, productType
        );
    }
}