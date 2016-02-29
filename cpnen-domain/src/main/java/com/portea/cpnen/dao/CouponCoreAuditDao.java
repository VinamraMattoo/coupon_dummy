package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponCoreAudit;
import com.portea.dao.Dao;

public interface CouponCoreAuditDao extends Dao<Integer, CouponCoreAudit>{

    void delete(CouponAudit couponAudit);
}
