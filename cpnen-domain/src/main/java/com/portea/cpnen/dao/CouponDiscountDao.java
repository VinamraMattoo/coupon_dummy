package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscount;
import com.portea.dao.Dao;

import java.util.Date;
import java.util.List;

public interface CouponDiscountDao extends Dao<Integer, CouponDiscount> {

    /**
     * Returns coupon discount details by area
     */
    List<Object[]> getCouponDiscountDetailsByArea(Date startDate, Date endDate);

    /**
     * Returns coupon discount details by brand
     */
    List<Object[]> getCouponDiscountDetailsByBrand(Date startDate, Date endDate);

    /**
     * Returns coupon discount details by referrer type
     */
    List<Object[]> getCouponDiscountDetailsByReferrerType(Date startDate, Date endDate);

    /**
     * Returns coupon discount min, max, avg details
     */
    List<Object[]> getCouponDiscountDetails(Date startDate, Date endDate);

    /**
     * Returns coupon discount amounts given by area
     */
    List<Object[]> getCouponDiscountsGivenByArea(Date startDate, Date endDate);

    /**
     * Returns coupon discount amounts given by brand
     */
    List<Object[]> getCouponDiscountsGivenByBrand(Date startDate, Date endDate);

}
