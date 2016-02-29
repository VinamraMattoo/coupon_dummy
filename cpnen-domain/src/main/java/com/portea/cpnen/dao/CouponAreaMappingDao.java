package com.portea.cpnen.dao;

import com.portea.cpnen.domain.*;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public interface CouponAreaMappingDao extends Dao<Integer, CouponAreaMapping> {

    CouponAreaMapping find(Coupon coupon, Area area) throws NoResultException;

    void delete(Coupon coupon);

    List<CouponAreaMapping> getMappings(Coupon coupon);

    /**
     * Returns coupon area details
     */
    List<Object[]> getCouponAreaDetails(Date date);

}
