package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponProductAdapterMappingAudit;
import com.portea.dao.Dao;

public interface CouponProductAdapterMappingAuditDao extends Dao<Integer, CouponProductAdapterMappingAudit> {

    void delete(CouponAudit coupon);

}
