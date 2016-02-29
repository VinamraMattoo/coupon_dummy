package com.portea.cpnen.rapi.domain;


import com.portea.cpnen.domain.CouponCategory;

public class CouponCategoryData {

    private CouponCategory couponCategory;

    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public CouponCategory getCouponCategory() {
        return couponCategory;
    }

    public void setCouponCategory(CouponCategory couponCategory) {
        this.couponCategory = couponCategory;
    }
}
