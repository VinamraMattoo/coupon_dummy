package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class InvalidProductTypeException extends CouponApplicationException{

    private static final long serialVersionUID = -3790584485167813746L;

    private int productId;
    private String productType;

    public InvalidProductTypeException(int productId, String productType) {
        super();
        this.productId = productId;
        this.productType = productType;
    }

    public InvalidProductTypeException(int productId, String productType, String message) {
        super(message);
        this.productId = productId;
        this.productType = productType;
    }

    public InvalidProductTypeException(int productId, String productType, String message, Throwable cause) {
        super(message, cause);
        this.productId = productId;
        this.productType = productType;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.INVALID_PRODUCT_TYPE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Product Id {0} is of invalid product type {1}::{0}|{1}",
                productId, productType
        );
    }
}
