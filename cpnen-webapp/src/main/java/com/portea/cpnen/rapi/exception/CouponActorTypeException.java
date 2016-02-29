package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class CouponActorTypeException extends CouponApplicationException{

    private static final long serialVersionUID = 6328739634507599891L;

    private String userId;

    public CouponActorTypeException(String userId) {
        super();
        this.userId = userId;
    }

    public CouponActorTypeException(String userId, String message) {
        super(message);
        this.userId = userId;
    }

    public CouponActorTypeException(String userId, String message, Throwable cause) {
        super(message, cause);
        this.userId = userId;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_ACTOR_TYPE_INVALID;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Requester id {0} of actor type CUSTOMER cannot apply coupon code of type STAFF::{0}",
                userId
        );
    }

}
