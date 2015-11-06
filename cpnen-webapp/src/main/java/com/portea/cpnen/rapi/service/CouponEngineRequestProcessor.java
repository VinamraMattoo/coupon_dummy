package com.portea.cpnen.rapi.service;

import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.rapi.domain.ApplicableDiscountResp;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestCreateReq;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestUpdateReq;

import javax.ejb.Local;

@Local
public interface CouponEngineRequestProcessor {

    CouponDiscountRequest createCouponDiscountRequest(CouponDiscountRequestCreateReq request);

    void updateCouponDiscountRequest(CouponDiscountRequestUpdateReq request);

    void deleteCouponDiscountRequest(Integer cdrId);

    ApplicableDiscountResp getCurrentApplicableDiscount(Integer cdrId);

}
