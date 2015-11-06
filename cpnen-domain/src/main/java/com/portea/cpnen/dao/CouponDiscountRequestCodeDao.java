package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountRequestCode;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponDiscountRequestCodeDao extends Dao<Integer, CouponDiscountRequestCode> {

    boolean isCodeRejected(Integer cdrId);

    List<String> getCouponCodes(Integer cdrId);

}
