package com.portea.cpnen.dao;

import com.portea.cpnen.domain.*;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface CouponReferrerMappingDao extends Dao<Integer, CouponReferrerMapping> {

    CouponReferrerMapping find(Coupon coupon, Referrer referrer) throws NoResultException;

    void delete(Coupon coupon);

    List<CouponReferrerMapping> getMappings(Coupon coupon);

}
