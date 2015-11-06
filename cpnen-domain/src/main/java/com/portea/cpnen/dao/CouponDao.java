package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponDao extends Dao<Integer, Coupon> {

    List<Coupon> getCoupons(Integer index, Integer requestedCount);

    List<Coupon> getCoupons(Integer maxRecords);

    Long getCouponCount();

}
