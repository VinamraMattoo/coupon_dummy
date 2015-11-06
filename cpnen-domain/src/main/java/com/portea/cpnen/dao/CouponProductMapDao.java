package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponProductMap;
import com.portea.dao.Dao;

import java.util.Set;

public interface CouponProductMapDao extends Dao<Integer, CouponProductMap> {

    boolean isAnyProductApplicable(int couponId, Set<Integer> productIds);

}
