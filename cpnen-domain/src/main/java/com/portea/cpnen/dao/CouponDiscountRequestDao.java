package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.domain.CouponDiscountRequestStatus;
import com.portea.dao.Dao;

import java.util.Date;
import java.util.List;

public interface CouponDiscountRequestDao extends Dao<Integer, CouponDiscountRequest> {

    /**
     * Returns coupon discount request status objects
     */
    Long getCouponDiscountRequestStatusDetails(Date startDate, Date endDate, CouponDiscountRequestStatus status);
}
