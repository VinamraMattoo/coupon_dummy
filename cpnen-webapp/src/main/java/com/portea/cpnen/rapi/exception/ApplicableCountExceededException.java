package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class ApplicableCountExceededException extends CouponApplicationException {


    private static final long serialVersionUID = 8045510782614992055L;
    private Integer applicableCount;
    private String code;

    public ApplicableCountExceededException(Integer applicableCount, String code) {
        super();
        this.applicableCount = applicableCount;
        this.code = code;
    }

    public ApplicableCountExceededException(Integer applicableCount, String code, String message) {
        super(message);
        this.applicableCount = applicableCount;
        this.code = code;
    }

    public ApplicableCountExceededException(Integer applicableCount, String code, String message, Throwable cause) {
        super(message, cause);
        this.applicableCount = applicableCount;
        this.code = code;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.APPLICABLE_COUNT_EXCEEDED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "The maximum applicable count {0} has exhausted for this coupon::{0}|{1}",
                applicableCount, code
        );
    }
}
