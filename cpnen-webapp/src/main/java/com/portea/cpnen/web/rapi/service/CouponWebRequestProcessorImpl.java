package com.portea.cpnen.web.rapi.service;

import com.portea.cpnen.dao.CouponDao;
import com.portea.cpnen.dao.UserDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.User;
import com.portea.cpnen.web.rapi.domain.*;
import com.portea.dao.JpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class CouponWebRequestProcessorImpl implements CouponWebRequestProcessor {

    @Inject @JpaDao
    UserDao userDao;

    @Inject @JpaDao
    CouponDao couponDao;

    @Override
    public Coupon createCoupon(Integer userId, CouponCreationRequest couponCreationRequest) {

        if((couponCreationRequest.getApplicableFrom() != null && couponCreationRequest.getApplicableTill() != null)){
            if(couponCreationRequest.getApplicableFrom().compareTo(couponCreationRequest.getApplicableTill()) > 0){
                throw new RuntimeException();
            }
        }

        if(couponCreationRequest.getTransactionMinVal() != null && couponCreationRequest.getTransactionMaxVal() != null){
            if(couponCreationRequest.getTransactionMinVal() > couponCreationRequest.getTransactionMaxVal()){
                throw new RuntimeException();
            }
        }

        Coupon coupon = new Coupon();

        coupon.setName(couponCreationRequest.getName());
        coupon.setApplicationType(couponCreationRequest.getApplicationType());
        coupon.setApplicableFrom(couponCreationRequest.getApplicableFrom());

        coupon.setApplicableTill(couponCreationRequest.getApplicableTill());
        coupon.setTransactionMaxValue(couponCreationRequest.getTransactionMaxVal());
        coupon.setTransactionMinValue(couponCreationRequest.getTransactionMinVal());

        coupon.setDescription(couponCreationRequest.getDescription());
        coupon.setInclusive(couponCreationRequest.getInclusive());
        coupon.setChannelName(couponCreationRequest.getChannelName());

        coupon.setApplicableUseCount(couponCreationRequest.getApplicableUseCount());
        User user = userDao.find(userId);
        coupon.setCreatedBy(user);

        coupon.setCreatedOn(new Date());
        couponDao.create(coupon);

        return coupon;

    }

    @Override
    public CouponVO getCoupon(Integer couponId) {
        Coupon coupon = couponDao.find(couponId);

        CouponVO couponVO = new CouponVO();

        couponVO.setId(coupon.getId());
        couponVO.setName(coupon.getName());
        couponVO.setChannelName(coupon.getChannelName());

        couponVO.setDescription(coupon.getDescription());
        couponVO.setInclusive(coupon.isInclusive());
        couponVO.setApplicableFrom(formatDate(coupon.getApplicableFrom()));
        couponVO.setApplicableTill(formatDate(coupon.getApplicableTill()));

        couponVO.setApplicableUseCount(coupon.getApplicableUseCount());
        couponVO.setApplicationType(coupon.getApplicationType());
        couponVO.setCreatedBy(getUserId(coupon.getCreatedBy()));

        couponVO.setCreatedOn(formatDate(coupon.getCreatedOn()));
        couponVO.setDeactivatedOn(formatDate(coupon.getDeactivatedOn()));
        couponVO.setDeactivatedBy(getUserId(coupon.getDeactivatedBy()));

        couponVO.setTransactionMinValue(coupon.getTransactionMinValue());
        couponVO.setTransactionMaxValue(coupon.getTransactionMaxValue());
        couponVO.setPublishedBy(getUserId(coupon.getPublishedBy()));

        couponVO.setPublishedOn(formatDate(coupon.getPublishedOn()));

        return couponVO;
    }

    private Integer getUserId(User user) {
        if(user != null){
            return user.getId();
        }
        return null;
    }

    private String formatDate(Date givenDate) {
        if(givenDate == null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        return formatter.format(givenDate);
    }

    @Override
    public void updateCoupon(Integer userId, Integer couponId, CouponUpdateRequest couponUpdateRequest){

        if((couponUpdateRequest.getApplicableFrom() != null && couponUpdateRequest.getApplicableTill() != null)){
            if(couponUpdateRequest.getApplicableFrom().compareTo(couponUpdateRequest.getApplicableTill()) > 0){
                throw new RuntimeException();
            }
        }


        if(couponUpdateRequest.getTransactionMinVal() != null && couponUpdateRequest.getTransactionMaxVal() != null){
            if(couponUpdateRequest.getTransactionMinVal() > couponUpdateRequest.getTransactionMaxVal()){
                throw new RuntimeException();
            }
        }


        Coupon coupon = couponDao.find(couponId);
        if(coupon == null){
            throw new RuntimeException();
        }

        if(coupon.getPublishedBy() != null){
            throw new RuntimeException();
        }

        coupon.setName(couponUpdateRequest.getName());
        coupon.setApplicationType(couponUpdateRequest.getApplicationType());
        coupon.setApplicableFrom(couponUpdateRequest.getApplicableFrom());

        coupon.setApplicableTill(couponUpdateRequest.getApplicableTill());
        coupon.setTransactionMaxValue(couponUpdateRequest.getTransactionMaxVal());
        coupon.setTransactionMinValue(couponUpdateRequest.getTransactionMinVal());

        coupon.setDescription(couponUpdateRequest.getDescription());
        coupon.setInclusive(couponUpdateRequest.getInclusive());
        coupon.setChannelName(couponUpdateRequest.getChannelName());

        coupon.setApplicableUseCount(couponUpdateRequest.getApplicableUseCount());

        if(couponUpdateRequest.getPublished() != null && couponUpdateRequest.getPublished() == true){

            User user = userDao.find(userId);
            coupon.setPublishedBy(user);

            Date currentDate = new Date();
            coupon.setPublishedOn(currentDate);
        }

        couponDao.update(coupon);
    }

    @Override
    public CouponListResponse getCoupons(CouponListRequest couponListRequest) {
        Integer startIndex = couponListRequest.getStartIndex();
        Integer requestedCount = couponListRequest.getRequestedCount();

        if(startIndex < 0){
            Integer maxRecords = 150; //TODO hardcoded maxRecords of coupons to be sent get from config.

            List<Coupon> coupons = couponDao.getCoupons(maxRecords);
            return getCouponList(coupons);
        }

        List<Coupon> coupons =  couponDao.getCoupons(startIndex, requestedCount);
        if(coupons.size() == 0){
            return getCouponList(couponDao.getCoupons(1, requestedCount));
        }
        return getCouponList(coupons);
    }

    private CouponListResponse getCouponList(List<Coupon> coupons) {
        CouponListResponse couponListResponse = new CouponListResponse();
        List<CouponDataVO> list = new ArrayList<>();

        for(Coupon coupon : coupons){

            CouponDataVO couponDataVO = new CouponDataVO();
            couponDataVO.setId(coupon.getId());
            couponDataVO.setName(coupon.getName());

            couponDataVO.setFrom(coupon.getApplicableFrom());
            couponDataVO.setTill(coupon.getApplicableTill());

            couponDataVO.setCreatedOn(coupon.getCreatedOn());
            couponDataVO.setApplicationType(coupon.getApplicationType());
            couponDataVO.setAppUseCount(coupon.getApplicableUseCount());

            couponDataVO.setInclusive(coupon.isInclusive());
            couponDataVO.setGlobal(true);

            BrandVo brandVo = new BrandVo();
            brandVo.setName("Brand1");
            brandVo.setId(1);
            List<BrandVo> brandVos = new ArrayList<>();

            brandVos.add(brandVo);
            couponDataVO.setBrandVos(brandVos);
            couponDataVO.setChannel(coupon.getChannelName());


            couponDataVO.setCodes(3);
            couponDataVO.setPublishedBy(getUserId(coupon.getPublishedBy()));
            couponDataVO.setPublishedOn(coupon.getPublishedOn());

            couponDataVO.setDeactivatedBy(getUserId(coupon.getDeactivatedBy()));
            couponDataVO.setDeactivatedOn(coupon.getDeactivatedOn());
            couponDataVO.setDescription(coupon.getDescription());
            list.add(couponDataVO);
        }
        couponListResponse.setRows(list);
        Long count = couponDao.getCouponCount();
        couponListResponse.setTotal(count);
        return couponListResponse;
    }

    @Override
    public void publishCoupon(Integer userId, int couponId) {

        Coupon coupon = couponDao.find(couponId);
        if(coupon == null){
            throw new RuntimeException();
        }

        if(coupon.getPublishedBy() != null){
            throw new RuntimeException();
        }

        User user = userDao.find(userId);
        coupon.setPublishedBy(user);

        Date currentDate = new Date();
        coupon.setPublishedOn(currentDate);
    }
}
