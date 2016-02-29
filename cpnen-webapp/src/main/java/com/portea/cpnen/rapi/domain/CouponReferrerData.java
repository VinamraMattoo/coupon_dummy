package com.portea.cpnen.rapi.domain;

public class CouponReferrerData {

    private String referrerType;

    private Long count;

    public String getReferrerType() {
        return referrerType;
    }

    public void setReferrerType(String referrerType) {
        this.referrerType = referrerType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
