package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class CouponBrandMappingException extends CouponApplicationException {

    private static final long serialVersionUID = 3353021159093358674L;

    private String couponCode;
    private String brandId;

    public CouponBrandMappingException(String couponCode, String brandId) {
        super();
        this.couponCode = couponCode;
        this.brandId = brandId;
    }

    public CouponBrandMappingException(String couponCode, String brandId, String message) {
        super(message);
        this.couponCode = couponCode;
        this.brandId = brandId;
    }

    public CouponBrandMappingException(String couponCode, String brandId, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.brandId = brandId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_BRAND_MAPPING_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is not applicable on the brand {1}::{0}|{1}",
                couponCode, brandId
        );
    }

}
