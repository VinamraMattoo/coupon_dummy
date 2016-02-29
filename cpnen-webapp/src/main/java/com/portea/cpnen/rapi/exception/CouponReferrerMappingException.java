package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class CouponReferrerMappingException extends CouponApplicationException {

    private static final long serialVersionUID = -1209872779071073927L;
    private String couponCode;
    private String referrerId;

    public CouponReferrerMappingException(String couponCode, String referrerId) {
        super();
        this.couponCode = couponCode;
        this.referrerId = referrerId;
    }

    public CouponReferrerMappingException(String couponCode, String referrerId, String message) {
        super(message);
        this.couponCode = couponCode;
        this.referrerId = referrerId;
    }

    public CouponReferrerMappingException(String couponCode, String referrerId, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.referrerId = referrerId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_REFERRER_MAPPING_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is not applicable on the referrer source {1}::{0}|{1}",
                couponCode, referrerId
        );
    }

}
