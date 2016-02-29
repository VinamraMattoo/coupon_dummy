package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.domain.CouponDiscountRequestCode;
import com.portea.cpnen.domain.CouponDiscountRequestStatus;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponDiscountRequestCodeDao extends Dao<Integer, CouponDiscountRequestCode> {

    List<String> getCouponCodes(Integer cdrId);

    CouponDiscountRequestCode getRequestCodeId(Integer cdrId, Integer codeId);

    List<CouponDiscountRequestCode> getCouponCodes(CouponDiscountRequest couponDiscountRequest);

}
