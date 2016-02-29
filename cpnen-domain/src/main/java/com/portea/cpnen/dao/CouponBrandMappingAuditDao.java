package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponBrandMappingAudit;
import com.portea.dao.Dao;

public interface CouponBrandMappingAuditDao extends Dao<Integer, CouponBrandMappingAudit> {

    void delete(CouponAudit coupon);
}
