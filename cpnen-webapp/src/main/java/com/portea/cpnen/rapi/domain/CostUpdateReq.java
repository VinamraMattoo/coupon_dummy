package com.portea.cpnen.rapi.domain;


public class CostUpdateReq {

    private Double totalCost;

    public CostUpdateReq() { }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String inspectNullParameters() {

        if (this.totalCost == null) {
            return "totalCost";
        }

        return null;
    }

    @Override
    public String toString() {
        return "CouponDiscountRequestUpdateReq{" +
                "  totalCost=" + totalCost +
                '}';
    }
}
