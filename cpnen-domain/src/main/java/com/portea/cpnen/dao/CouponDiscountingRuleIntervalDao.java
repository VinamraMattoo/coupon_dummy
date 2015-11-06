package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountingRuleInterval;
import com.portea.dao.Dao;

public interface CouponDiscountingRuleIntervalDao extends Dao<Integer, CouponDiscountingRuleInterval> {

    Integer getDiscount(int couponDiscountingRuleId, int totalCost, int productCount);

    Integer getIntervalCount(Integer cdrId);

}
