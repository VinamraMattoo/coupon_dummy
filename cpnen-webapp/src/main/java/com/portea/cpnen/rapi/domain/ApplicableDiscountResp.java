package com.portea.cpnen.rapi.domain;

public class ApplicableDiscountResp {

    int discountAmount;

    public ApplicableDiscountResp() {}

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "ApplicableDiscountResp{" +
                "discountAmount=" + discountAmount +
                '}';
    }
}
