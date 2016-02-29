package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Brand;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponBrandMapping;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface CouponBrandMappingDao extends Dao<Integer, CouponBrandMapping> {

    List<CouponBrandMapping> getMappings(Coupon coupon);

    void delete(Coupon coupon);

    CouponBrandMapping find(Coupon coupon, Brand brand) throws NoResultException;

}
