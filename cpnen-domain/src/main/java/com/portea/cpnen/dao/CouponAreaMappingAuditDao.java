package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponAreaMappingAudit;
import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponBrandMappingAudit;
import com.portea.dao.Dao;

public interface CouponAreaMappingAuditDao extends Dao<Integer, CouponAreaMappingAudit> {

    void delete(CouponAudit coupon);
}
