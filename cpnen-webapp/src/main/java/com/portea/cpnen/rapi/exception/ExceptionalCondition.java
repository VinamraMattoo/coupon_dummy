package com.portea.cpnen.rapi.exception;

import javax.validation.constraints.NotNull;

public final class ExceptionalCondition {

    public interface ErrorType {
        String getErrorCode();
        String getErrorPhrase();
    }

    /**
     * All error codes starting from 1 to 200 should be consumed by CDR REST API users.
     * All error codes starting from 201 should be consumed by web application.
     */
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
        COUPON_DISCOUNT_REQUEST_INVALID("CPNE13", "Coupon discount request is invalid"),
        COUPON_BRAND_MAPPING_INVALID("CPNE14", "Coupon is not applicable on a specified brand"),
        INVALID_PRODUCT_TYPE("CPNE15", "Product Type and Product Id have a mismatch"),
        CONTEXT_TYPE_INAPPLICABLE("CPNE16", "Coupon Discount Request is not applicable for the context type"),
        SOURCE_NAME_UNRECOGNIZED("CPNE17", "Source name is invalid"),
        COUPON_DISCOUNT_REQUEST_TIMEOUT("CPNE18", "Coupon discount request has timed out"),
        COUPON_ACTOR_TYPE_INVALID("CPNE19", "Coupon actor type is invalid"),
        INCOMPLETE_REQUEST("CPNE20", "Request is incomplete"),
        APPLICABLE_COUNT_EXCEEDED("CPNE21", "Coupon applicability count exceeded"),
        MULTIPLE_CODES_FOR_COUPON("CPNE22", "Multiple codes of the same coupon are not allowed"),
        NON_NTH_TIME_TRANSACTION("CPNE23", "Discount cannot be applied as product is not being purchased nth time"),
        ILLEGAL_CDR_APPLY_STATE("CPNE24", "Coupon discount request is already applied"),
        NOT_WITHIN_SUBSCRIPTION("CPNE25", "Coupon discount request is not within subscription"),
        INVALID_REQUEST("CPNE26", "Request is invalid"),
        ILLEGAL_CDR_CANCEL_STATE("CPNE27", "Coupon discount request is already canceled"),
        ILLEGAL_CDR_COMMIT_STATE("CPNE28", "Coupon discount request has to be applied to be committed"),
        CONTEXT_ID_INAPPLICABLE("CPNE29", "Context id for coupon discount request is already set"),
        CONTEXT_ID_REQUIRED("CPNE30", "Context id for coupon discount request is needed to commit request"),
        COUPON_AREA_MAPPING_INVALID("CPNE31", "Coupon is not applicable on a specified area"),
        COUPON_REFERRER_MAPPING_INVALID("CPNE32", "Coupon is not applicable on a specified referrer source"),

        COUPON_CREATION_FAILED("CPNE201","Coupon creation failed "),
        CODE_CREATION_FAILED("CPNE202","Code creation failed "),
        COUPON_UPDATE_FAILED("CPNE203","Coupon update failed ")

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
                    return COUPON_DISCOUNT_REQUEST_INVALID;
                case "CPNE14":
                    return COUPON_BRAND_MAPPING_INVALID;
                case "CPNE15":
                    return INVALID_PRODUCT_TYPE;
                case "CPNE16":
                    return CONTEXT_TYPE_INAPPLICABLE;
                case "CPNE17":
                        return SOURCE_NAME_UNRECOGNIZED;
                case "CPNE18":
                    return COUPON_DISCOUNT_REQUEST_TIMEOUT;
                case "CPNE19":
                    return COUPON_ACTOR_TYPE_INVALID;
                case "CPNE20":
                    return INCOMPLETE_REQUEST;
                case "CPNE21":
                    return APPLICABLE_COUNT_EXCEEDED;

                default:
                    throw new IllegalArgumentException(code + ":Error code not recognized");
            }
        }
    }
}
