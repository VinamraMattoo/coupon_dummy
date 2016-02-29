package com.portea.cpnen.rapi.domain;

import java.util.List;

public class CouponExpiryWeeklyData {

    public List<CouponExpiryData> getWeeklyData() {
        return weeklyData;
    }

    public void setWeeklyData(List<CouponExpiryData> weeklyData) {
        this.weeklyData = weeklyData;
    }

    List<CouponExpiryData> weeklyData;
}
