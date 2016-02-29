package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponDiscountingRuleAudit;
import com.portea.dao.Dao;


public interface CouponDiscountingRuleAuditDao extends Dao<Integer, CouponDiscountingRuleAudit> {

    void delete(CouponAudit couponAudit);
}
