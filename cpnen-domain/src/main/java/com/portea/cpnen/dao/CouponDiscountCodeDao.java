package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponDiscountCode;
import com.portea.cpnen.domain.User;
import com.portea.dao.Dao;

public interface CouponDiscountCodeDao extends Dao<Integer, CouponDiscountCode> {

    Long getCouponAppliedCount(Coupon coupon);

    Long getCouponAppliedCount(Coupon coupon, User user);

    Long getCodeAppliedCount(String code);

    Long getCodeAppliedCount(String code, User user);
}
