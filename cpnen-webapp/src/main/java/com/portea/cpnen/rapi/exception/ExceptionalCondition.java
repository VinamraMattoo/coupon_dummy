package com.portea.cpnen.rapi.exception;

import javax.validation.constraints.NotNull;

public final class ExceptionalCondition {

    public interface ErrorType {
        String getErrorCode();
        String getErrorPhrase();
    }

    public enum Error implements ErrorType {

        RESERVED_FOR_ANOTHER_USER("CPNE01", "Reserved for another user"),
        COUPON_VALIDITY_EXPIRED("CPNE02", "Coupon validity has expired"),
        MULTIPLE_EXCLUSIVE_COUPONS("CPNE03", "Multiple exclusive coupons cannot be applied"),
        TRANSACTION_VALUE_OUT_OF_RANGE("CPNE04", "Transaction value is out of valid range"),
        COUPON_CODE_TIMEOUT("CPNE05", "Coupon code has timed out"),
        COUPON_INACTIVE("CPNE06", "Coupon is deactivated"),
        COUPON_EXHAUSTED("CPNE07", "Coupon usage is exhausted"),
        COUPON_INVALID("CPNE08", "Coupon code is not recognized"),
        REQUEST_ALREADY_COMPLETED("CPNE09", "Coupon discount request already marked completed. Create new request."),
        PRODUCT_INVALID("CPNE10", "Product code is not recognized"),
        CONSUMER_INVALID("CPNE11", "Consumer not recognized"),
        COUPON_INAPPLICABLE("CPNE12", "Coupon is not applicable on a specified product"),
        PRODUCT_COUNT_OUT_OF_RANGE("CPNE13", "Product count is out of range"),
        PRODUCT_SPAN_COUNT_OUT_OF_RANGE("CPNE14", "Product span count is out of range"),
        COUPON_DISCOUNT_REQUEST_INVALID("CPNE15", "Coupon discount request is invalid"),
        ;


        private final String code;
        private final String phrase;

        Error(final String code, final String phrase) {
            this.code = code;
            this.phrase = phrase;
        }

        public String getErrorCode() {
            return code;
        }

        public String getErrorPhrase() {
            return phrase;
        }

        public final static Error codeToEnum(@NotNull String code) {

            if (code == null) {
                throw new IllegalArgumentException("Error code is null");
            }

            switch(code) {
                case "CPNE01":
                    return RESERVED_FOR_ANOTHER_USER;
                case "CPNE02":
                    return COUPON_VALIDITY_EXPIRED;
                case "CPNE03":
                    return MULTIPLE_EXCLUSIVE_COUPONS;
                case "CPNE04":
                    return TRANSACTION_VALUE_OUT_OF_RANGE;
                case "CPNE05":
                    return COUPON_CODE_TIMEOUT;
                case "CPNE06":
                    return COUPON_INACTIVE;
                case "CPNE07":
                    return COUPON_EXHAUSTED;
                case "CPNE08":
                    return COUPON_INVALID;
                case "CPNE09":
                    return REQUEST_ALREADY_COMPLETED;
                case "CPNE10":
                    return PRODUCT_INVALID;
                case "CPNE11":
                    return CONSUMER_INVALID;
                case "CPNE12":
                    return COUPON_INAPPLICABLE;
                case "CPNE13":
                    return PRODUCT_COUNT_OUT_OF_RANGE;
                case "CPNE14":
                    return PRODUCT_SPAN_COUNT_OUT_OF_RANGE;
                case "CPNE15":
                    return COUPON_DISCOUNT_REQUEST_INVALID;
                default:
                    throw new IllegalArgumentException(code + ":Error code not recognized");
            }
        }
    }
}
