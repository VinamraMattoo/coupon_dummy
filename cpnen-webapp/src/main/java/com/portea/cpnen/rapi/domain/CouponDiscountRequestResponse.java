package com.portea.cpnen.rapi.domain;

public class CouponDiscountRequestResponse {
    private Integer cdrId;
    private Double discount;

    public CouponDiscountRequestResponse() {
    }

    public void setCdrId(Integer cdrId) {
        this.cdrId = cdrId;
    }

    public Integer getCdrId() {
        return cdrId;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscount() {
        return discount;
    }
}
