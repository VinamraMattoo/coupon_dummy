package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class CouponAreaMappingException extends CouponApplicationException {

    private static final long serialVersionUID = -8334612290988054092L;
    private String couponCode;
    private String areaId;

    public CouponAreaMappingException(String couponCode, String areaId) {
        super();
        this.couponCode = couponCode;
        this.areaId = areaId;
    }

    public CouponAreaMappingException(String couponCode, String areaId, String message) {
        super(message);
        this.couponCode = couponCode;
        this.areaId = areaId;
    }

    public CouponAreaMappingException(String couponCode, String areaId, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.areaId = areaId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_AREA_MAPPING_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon code {0} is not applicable on the area {1}::{0}|{1}",
                couponCode, areaId
        );
    }

}
