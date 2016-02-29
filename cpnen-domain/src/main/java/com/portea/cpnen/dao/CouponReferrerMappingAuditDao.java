package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponAreaMappingAudit;
import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponReferrerMappingAudit;
import com.portea.dao.Dao;

public interface CouponReferrerMappingAuditDao extends Dao<Integer, CouponReferrerMappingAudit> {

    void delete(CouponAudit coupon);
}
