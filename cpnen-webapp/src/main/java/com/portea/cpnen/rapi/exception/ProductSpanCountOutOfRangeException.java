package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class ProductSpanCountOutOfRangeException extends CouponApplicationException {


    private static final long serialVersionUID = 1019889249589962527L;

    private String couponCode;
    private int rangeStart;
    private int rangeEnd;

    public ProductSpanCountOutOfRangeException(String couponCode, int rangeStart, int rangeEnd) {
        super();
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public ProductSpanCountOutOfRangeException(String couponCode, int rangeStart, int rangeEnd, String message) {
        super(message);
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public ProductSpanCountOutOfRangeException(String couponCode, int rangeStart, int rangeEnd, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.PRODUCT_SPAN_COUNT_OUT_OF_RANGE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Span count is out of range for coupon code {0}. " +
                        "The range is from {1} to {2}::{0}|{1}|{2}",
                couponCode, rangeStart, rangeEnd
        );
    }
}