package com.portea.cpnen.rapi.exception;

import java.text.MessageFormat;
import java.util.Arrays;

public class CouponNthTimeApplicationException extends CouponApplicationException {

    private static final long serialVersionUID = 8822496168137357150L;
    private String applicationType;

    private Boolean nthRecurring;
    private String nthTime;
    private Integer[] purchaseCount;

    public CouponNthTimeApplicationException(String applicationType, String nthTime, Boolean nthRecurring,  Integer[] purchaseCount) {
        super();
        this.applicationType = applicationType;
        this.nthTime = nthTime;
        this.purchaseCount = purchaseCount;
        this.nthRecurring = nthRecurring;
    }

    public CouponNthTimeApplicationException(String applicationType, String nthTime, Boolean nthRecurring, Integer[] purchaseCount, String message) {
        super(message);
        this.applicationType = applicationType;
        this.nthTime = nthTime;
        this.purchaseCount = purchaseCount;
        this.nthRecurring = nthRecurring;
    }

    public CouponNthTimeApplicationException(String applicationType, String nthTime, Boolean nthRecurring, Integer[] purchaseCount, String message, Throwable cause) {
        super(message, cause);
        this.applicationType = applicationType;
        this.nthTime = nthTime;
        this.purchaseCount = purchaseCount;
        this.nthRecurring = nthRecurring;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.NON_NTH_TIME_TRANSACTION;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "This coupon having nth-time as {0} and recurring as {1} is not applicable for purchase count span: {2}::{0}|{1}|{2}",
                nthTime, nthRecurring, Arrays.toString(purchaseCount)
        );
    }

}
