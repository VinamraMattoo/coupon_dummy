package com.portea.commp.service.exception;

public final class ExceptionalCondition {

    public interface ErrorType {
        String getErrorCode();
        String getErrorPhrase();
    }

    /**
     * All error codes starting from 1 to 200 should be consumed by 3rd Party users, sending direct Sms REST API users.
     * All error codes starting from 201 should be consumed by web application.
     */
    public enum Error implements ErrorType {
        INCOMPLETE_REQUEST("SMSE001", "Request is incomplete"),
        INVALID_REQUEST("SMSE002", "Request is invalid"),
        INVALID_USER_NAME("SMSE003", "Invalid username"),
        INVALID_CREDENTIALS("SMSE004", "Invalid credentials"),
        MESSAGE_IS_EMPTY("SMSE005", "Message is empty"),
        SMS_RECEIVED_EXCEEDS_UPPER_LIMIT("SMSE006", "Sms received exceeds upper limit" ),
        MESSAGE_LENGTH_EXCEEDS_MAX_LIMIT("SMSE007", "Sms message length exceeds upper limit"),

        INCOMPLETE_WEB_REQUEST("SMSE201", "Request is incomplete"),
        INVALID_WEB_REQUEST("SMSE202", "Request is invalid"),
        MULTIPLE_GATEWAY_HAVING_SAME_PRIORITY("SMSE203","Multiple Gateways have same priority"),
        MISSING_GATEWAY_MAPPING_FOR_PRIORITY("SMSE0204", "Missing gateway mapping for priority"),
        USER_NAME_ALREADY_EXISTS("SMSE205", "Username already exists"),


        USER_ALREADY_IN_STATE("SMSE206", "No change in user state"),
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
    }
}