package com.portea.cpnen.rapi.domain;

import java.util.ArrayList;
import java.util.List;

public class CouponDiscountWeeklyData {

    private List<CouponDiscountData> discountData = new ArrayList<>();

    public List<CouponDiscountData> getDiscountData() {
        return discountData;
    }

    public void setDiscountData(List<CouponDiscountData> discountData) {
        this.discountData = discountData;
    }


}
