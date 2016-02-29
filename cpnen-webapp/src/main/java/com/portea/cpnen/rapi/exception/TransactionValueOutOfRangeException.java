package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class TransactionValueOutOfRangeException extends CouponApplicationException {

    private static final long serialVersionUID = -8529155022321892953L;

    private String couponCode;
    private Integer rangeStart;
    private Integer rangeEnd;

    public TransactionValueOutOfRangeException(String couponCode, Integer rangeStart, Integer rangeEnd) {
        super();
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public TransactionValueOutOfRangeException(String couponCode, Integer rangeStart, Integer rangeEnd, String message) {
        super(message);
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public TransactionValueOutOfRangeException(String couponCode, Integer rangeStart, Integer rangeEnd, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.TRANSACTION_VALUE_OUT_OF_RANGE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Transaction value is out of range for coupon code {0}. " +
                        "The range is from {1} to {2}::{0}|{1}|{2}",
                couponCode, rangeStart, rangeEnd
        );
    }
}