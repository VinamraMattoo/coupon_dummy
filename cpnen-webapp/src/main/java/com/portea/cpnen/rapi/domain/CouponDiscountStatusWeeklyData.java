package com.portea.cpnen.rapi.domain;

import java.util.ArrayList;
import java.util.List;

public class CouponDiscountStatusWeeklyData {

    private List<CouponDiscountStatusData> discountData = new ArrayList<>();

    public List<CouponDiscountStatusData> getDiscountData() {
        return discountData;
    }

    public void setDiscountData(List<CouponDiscountStatusData> discountData) {
        this.discountData = discountData;
    }

}
