package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponCode;
import com.portea.cpnen.domain.User;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

public interface CouponCodeDao extends Dao<Integer, CouponCode> {

    List<CouponCode> getCouponCodes(String[] codes);

    CouponCode getCouponCode(String code) throws NoResultException;

    List<CouponCode> getCodes(Integer couponId, Integer offset, Integer limit);
    
    List<CouponCode> getCodes(Integer offset, Integer limit);

    Long getCodeCount(Integer couponId);

    Long getCodeCount();

    void delete(Coupon coupon);

    Integer getActiveCodeId(String code) throws NoResultException,NonUniqueResultException;

    List<Object[]> getCouponCodeMap(List<String> codes);

    void deactivate(Coupon coupon, User user);
}
