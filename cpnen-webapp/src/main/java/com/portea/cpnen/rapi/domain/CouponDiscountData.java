package com.portea.cpnen.rapi.domain;

public class CouponDiscountData {

    private Double minDiscount;

    private Double maxDiscount;

    private Double avgDiscount;

    private String dayOfWeek;

    public Double getMinDiscount() {
        return minDiscount;
    }

    public void setMinDiscount(Double minDiscount) {
        this.minDiscount = minDiscount;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Double getAvgDiscount() {
        return avgDiscount;
    }

    public void setAvgDiscount(Double avgDiscount) {
        this.avgDiscount = avgDiscount;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

}
