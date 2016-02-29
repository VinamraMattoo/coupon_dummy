package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class ContextTypeInapplicableException extends CouponApplicationException{

    private static final long serialVersionUID = -4791432944125641304L;

    private String cdrContextType;
    private String couponContextType;

    public ContextTypeInapplicableException(String cdrContextType, String couponContextType) {
        super();
        this.cdrContextType = cdrContextType;
        this.couponContextType = couponContextType;
    }

    public ContextTypeInapplicableException(String cdrContextType, String couponContextType, String message) {
        super(message);
        this.cdrContextType = cdrContextType;
        this.couponContextType = couponContextType;
    }

    public ContextTypeInapplicableException(String cdrContextType, String couponContextType, String message, Throwable cause) {
        super(message, cause);
        this.cdrContextType = cdrContextType;
        this.couponContextType = couponContextType;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.CONTEXT_TYPE_INAPPLICABLE;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Coupon Discount Request of type {0} is not applicable for coupon context Type {1}::{0}|{1}",
                cdrContextType, couponContextType
        );
    }

}
