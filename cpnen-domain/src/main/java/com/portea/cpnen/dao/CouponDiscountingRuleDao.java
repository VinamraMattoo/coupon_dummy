package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountingRule;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponDiscountingRuleDao extends Dao<Integer, CouponDiscountingRule> {

    List<Integer> getRuleIds(int couponId);

}
