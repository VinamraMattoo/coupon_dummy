package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;

public class CouponNthTimeNotWithinSubscriptionException extends CouponApplicationException{

    private static final long serialVersionUID = -4791432944125641304L;

    private String cdrContextType;

    public CouponNthTimeNotWithinSubscriptionException(String cdrContextType) {
        super();
        this.cdrContextType = cdrContextType;
    }

    public CouponNthTimeNotWithinSubscriptionException(String cdrContextType, String message) {
        super(message);
        this.cdrContextType = cdrContextType;
    }

    public CouponNthTimeNotWithinSubscriptionException(String cdrContextType, String message, Throwable cause) {
        super(message, cause);
        this.cdrContextType = cdrContextType;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.NOT_WITHIN_SUBSCRIPTION;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Not applicable as given cdr context {0} is not within subscription::{0}",
                cdrContextType
        );
    }

}

