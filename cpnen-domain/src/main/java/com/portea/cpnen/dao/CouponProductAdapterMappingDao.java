package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponProductAdapterMapping;
import com.portea.cpnen.domain.ProductAdapter;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface CouponProductAdapterMappingDao extends Dao<Integer, CouponProductAdapterMapping>{

    List<CouponProductAdapterMapping> getMappings(Coupon coupon);

    void delete(Coupon coupon);

    CouponProductAdapterMapping find(Coupon coupon, ProductAdapter product) throws NoResultException;

}
