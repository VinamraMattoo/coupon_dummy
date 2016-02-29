package com.portea.cpnen.rapi.domain;

public class ApplicableDiscountResp {

    private Double discountAmount;

    public ApplicableDiscountResp() {}

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "ApplicableDiscountResp{" +
                "discountAmount=" + discountAmount +
                '}';
    }
}
