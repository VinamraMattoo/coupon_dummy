package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponCode;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponCodeDao extends Dao<Integer, CouponCode> {

    List<CouponCode> getCouponCodes(String[] codes);

    CouponCode getCouponCode(String code);

}
