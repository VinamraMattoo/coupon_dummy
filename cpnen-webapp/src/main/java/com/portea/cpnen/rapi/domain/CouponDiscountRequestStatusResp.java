package com.portea.cpnen.rapi.domain;

public class CouponDiscountRequestStatusResp {

    private String status;

    public CouponDiscountRequestStatusResp() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CouponDiscountRequestStatusResp{" +
                "status=" + status +
                '}';
    }
}
