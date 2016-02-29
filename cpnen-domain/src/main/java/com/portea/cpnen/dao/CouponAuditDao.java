package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponAudit;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponAuditDao extends Dao<Integer, CouponAudit> {

    List<CouponAudit> find(Coupon coupon);

    void delete(Coupon coupon);

}
