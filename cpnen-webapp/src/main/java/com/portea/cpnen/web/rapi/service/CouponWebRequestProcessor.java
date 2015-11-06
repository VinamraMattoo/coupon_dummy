package com.portea.cpnen.web.rapi.service;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.web.rapi.domain.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CouponWebRequestProcessor {

    Coupon createCoupon(Integer userId, CouponCreationRequest couponCreationRequest);

    CouponVO getCoupon(Integer couponId);

    void updateCoupon(Integer userId, Integer couponId, CouponUpdateRequest couponUpdateRequest);

    void publishCoupon(Integer userId, int couponId);

    CouponListResponse getCoupons(CouponListRequest couponListRequest);

}
