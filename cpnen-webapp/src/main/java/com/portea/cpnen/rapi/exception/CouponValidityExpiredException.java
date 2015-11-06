package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;
import java.util.Date;

public class CouponValidityExpiredException extends CouponApplicationException {

    private static final long serialVersionUID = -7708398168684488000L;

    private String couponCode;
    private Date validFrom;
    private Date validTill;


    public CouponValidityExpiredException(String couponCode, Date validFrom, Date validTill) {
        super();
        this.couponCode = couponCode;
        this.validFrom = validFrom;
        this.validTill = validTill;
    }

    public CouponValidityExpiredException(String couponCode, Date validFrom, Date validTill, String message) {
        super(message);
        this.couponCode = couponCode;
        this.validFrom = validFrom;
        this.validTill = validTill;
    }

    public CouponValidityExpiredException(String couponCode, Date validFrom, Date validTill, String message, Throwable cause) {
        super(message, cause);
        this.couponCode = couponCode;
        this.validFrom = validFrom;
        this.validTill = validTill;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.COUPON_VALIDITY_EXPIRED;
    }

    @Override
    public String getExplanatoryMessage() {

        return MessageFormat.format(
                "Coupon code {0} was valid from [{1}] to [{2}]::{0}|{1}|{2}",
                couponCode, DATE_FORMAT.format(validFrom), DATE_FORMAT.format(validTill)
        );
    }
}