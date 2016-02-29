package com.portea.commp.service.domain;

import java.util.List;

public class BatchFailureResponse {
    private Integer total;
    private List<SmsFailureReason> smsFailures;

    public BatchFailureResponse() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SmsFailureReason> getSmsFailures() {
        return smsFailures;
    }

    public void setSmsFailures(List<SmsFailureReason> smsFailures) {
        this.smsFailures = smsFailures;
    }

    public static class SmsFailureReason {

        private String id;
        private String reason;

        public SmsFailureReason() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
