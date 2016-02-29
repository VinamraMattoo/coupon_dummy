package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponDiscountingRule;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface CouponDiscountingRuleDao extends Dao<Integer, CouponDiscountingRule> {

    CouponDiscountingRule getRule(Coupon coupon) throws NoResultException;

    void delete(Coupon coupon);
}
