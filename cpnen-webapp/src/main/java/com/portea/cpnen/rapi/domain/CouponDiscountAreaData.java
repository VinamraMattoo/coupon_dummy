package com.portea.cpnen.rapi.domain;

import com.portea.cpnen.domain.Area;

public class CouponDiscountAreaData {

    private String area;

    private Double discountGiven;

    public Double getDiscountGiven() {
        return discountGiven;
    }

    public void setDiscountGiven(Double discountGiven) {
        this.discountGiven = discountGiven;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
