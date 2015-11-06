package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class ProductCountOutOfRangeException extends CouponApplicationException {

    private static final long serialVersionUID = 2642303917688995401L;

    private String couponCode;
    private String productId;
    private int rangeStart;
    private int rangeEnd;

    public ProductCountOutOfRangeException(String couponCode, String productId, int rangeStart, int rangeEnd) {
        super();
        this.productId = productId;
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public ProductCountOutOfRangeException(String couponCode, String productId, int rangeStart, int rangeEnd, String message) {
        super(message);
        this.productId = productId;
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public ProductCountOutOfRangeException(String couponCode, String productId, int rangeStart, int rangeEnd, String message, Throwable cause) {
        super(message, cause);
        this.productId = productId;
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.PRODUCT_COUNT_OUT_OF_RANGE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Count for product {0} is out of range for coupon code {1}. " +
                        "The range is from {2} to {3}::{0}|{1}|{2}|{3}",
                productId, couponCode, rangeStart, rangeEnd
        );
    }
}