package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class InvalidProductException extends CouponApplicationException {

    private static final long serialVersionUID = 6380879078274462056L;

    private String productCode;
    private String productType;

    public InvalidProductException(String productCode, String productType) {
        super();
        this.productCode = productCode;
        this.productType = productType;
    }

    public InvalidProductException(String productCode, String productType, String message) {
        super(message);
        this.productCode = productCode;
        this.productType = productType;
    }

    public InvalidProductException(String productCode, String productType, String message, Throwable cause) {
        super(message, cause);
        this.productCode = productCode;
        this.productType = productType;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.PRODUCT_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Product code {0} of type {1} is invalid::{0}|{1}",
                productCode, productType
        );
    }
}