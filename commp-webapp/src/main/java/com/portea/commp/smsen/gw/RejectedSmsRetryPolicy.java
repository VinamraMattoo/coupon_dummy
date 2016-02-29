package com.portea.commp.smsen.gw;

public enum RejectedSmsRetryPolicy {

    RETRY,

    NO_RETRY

    ;

    public static RejectedSmsRetryPolicy find(String name) {

        RejectedSmsRetryPolicy[] retryPolicies = RejectedSmsRetryPolicy.values();

        for (RejectedSmsRetryPolicy rejectedSmsRetryPolicy : retryPolicies) {
            if (rejectedSmsRetryPolicy.name().equals(name)) {

                return rejectedSmsRetryPolicy;
            }
        }
        return NO_RETRY;
    }
}
