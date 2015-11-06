package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponCategoryMap;
import com.portea.dao.Dao;

import java.util.Set;

public interface CouponCategoryMapDao extends Dao<Integer, CouponCategoryMap> {

    boolean isAnyCategoryApplicable(int couponId, Set<Integer> categoryIds);

}
