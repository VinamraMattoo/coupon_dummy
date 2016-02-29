package com.portea.commp.smsen.engine;

public class SmsValidationResult {

    private boolean valid;
    private String reason;

    public SmsValidationResult(boolean valid, String reason) {
        this.valid = valid;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
