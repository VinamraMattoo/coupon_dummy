package com.portea.commp.service.domain;

import com.portea.commp.smsen.domain.NormalizedSmsStatus;

public class SmsStatusResponse {

    private NormalizedSmsStatus status;
    private String reason;

    public SmsStatusResponse() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NormalizedSmsStatus getStatus() {
        return status;
    }

    public void setStatus(NormalizedSmsStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SmsStatusResponse{" +
                "reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
