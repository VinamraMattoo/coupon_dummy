package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InvalidProductException extends CouponApplicationException {

    private static final long serialVersionUID = 6380879078274462056L;

    private String productCode;

    public InvalidProductException(String productCode) {
        super();
        this.productCode = productCode;
    }

    public InvalidProductException(String productCode, String message) {
        super(message);
        this.productCode = productCode;
    }

    public InvalidProductException(String productCode, String message, Throwable cause) {
        super(message, cause);
        this.productCode = productCode;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.PRODUCT_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Product code {0} is invalid::{0}",
                productCode
        );
    }
}