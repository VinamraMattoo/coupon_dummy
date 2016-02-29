package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.Date;
import java.util.List;

public interface CouponDao extends Dao<Integer, Coupon> {

    List<Coupon> getCoupons(Integer index, Integer requestedCount, String name, Boolean published, Boolean draft, Boolean deactivated);

    List<Coupon> getCoupons(Integer maxRecords);

    Long getCouponCount();

    Integer getCouponId(String name) throws NoResultException, NonUniqueResultException;

    List<Coupon> find(String name);

    /**
     * Returns coupon actor details
     */
    List<Object[]> getCouponActorDetails(Date date);

    /**
     * Returns coupon category details
     */
    List<Object[]> getCouponCategoryDetails(Date date);

    /**
     * Returns coupon context type details
     */
    List<Object[]> getCouponContextTypeDetails(Date date);

    /**
     * Returns coupon discount details
     */
    Long getCouponDiscountDetails(Date date, Integer startRange, Integer endRange);

    /**
     * Returns coupon expiry details
     */
    List<Object[]> getCouponExpiryDetails(Date startDate, Date endDate);


}
