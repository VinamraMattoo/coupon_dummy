package com.portea.cpnen.web.rapi.service;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.rapi.domain.*;
import com.portea.cpnen.web.rapi.domain.*;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface CouponWebRequestProcessor {

    Coupon createCoupon(Integer userId, CouponCRUDRequest couponCRUDRequest);

    CouponVO getCouponVO(Integer couponId);

    void updateCoupon(Integer userId, Integer couponId, CouponCRUDRequest couponCRUDRequest);

    void publishCoupon(Integer userId, Integer couponId, Long lastUpdatedOn);

    CouponListResponse getCoupons(Integer userId, String name, String draft, String published,
                                  String deactivated, String createdFrom, String createdTill,
                                  String inclusive, String updateFrom, String updateTill,
                                  String deactivateFrom, String deactivateTill, String appDurationFrom,
                                  String appDurationTill, String publishedFrom, String publishedTill,
                                  String discountFrom, String discountTill, String transactionFrom,
                                  String transactionTill, String couponAppType, String actor, String
                                          contextType, String limit, String offset, String active, String global, String order, String sort);

    CouponCodesResponse getCouponCodes(Integer couponId, Integer offset, Integer limit);

    CouponCodesResponse getCouponCodes(Integer userId, String offset, String limit, String name, String active,
                                       String deactivated, String createdFrom, String createdTill,
                                       String deactivateFrom, String deactivateTill, String channel, String order, String sort);

    /**
     * Returns a sub set of products (services, packages)
     * @param limit the maximum records to fetch
     * @param offset the starting record number in the result set to fetch from
     */
    ProductListResponse getProducts(Integer offset, Integer limit);

    List<ProductVO> getProducts();

    List<BrandVo> getBrands();

    List<AreaVo> getAreas();

    List<ReferrersVo> getReferrers();

    void deactivateCoupon(Integer userId, Integer couponId, Long lastUpdatedOn);

    void deleteCoupon(Integer couponId, Long lastUpdatedOn);

    /**
     * Creates coupon code and reservations if specified.
     * If the reservation id is not valid no reservation will be done
     */
    Integer createCouponCode(Integer couponId, CouponCodeCreationRequest couponCodeCreationRequest, Integer userId);

    void createReservation(Integer couponId, Integer codeId, CouponCodeCreationRequest.Reservation reservation, Integer userId);

    void extendValidity(Integer userId, Long epochTime, Integer couponId, Long lastUpdatedOn);

    void deactivateCouponCode(Integer userId, Integer couponId, Integer codeId);

    CouponCodeVO getCouponCode(Integer couponId, Integer codeId);

    List<String> getUserNames(String name, Integer limit);

    Integer getUserId(String name);

    /**
     * Returns coupon id for a given coupon name OR null if given name is not found or when more than one
     * coupon is found for a given name.
     */
    Integer getCouponId(String name);

    /**
     * Returns active code id for a given code name or null if given name is not found.
     */
    Integer getCouponCodeId(String code);

    CouponCategoriesResponse getCategories(Integer userId);

    /**
     * Returns list of active coupons created by actor(STAFF/CUSTOMER)
     */
    List<CouponActorData> getNumOfCouponsCreatedByActorDetails();

    /**
     * Returns list of active coupons created by a Department(SALES/MARKETING/OPs/ENGAGEMENT)
     */
    List<CouponCategoryData> getNumOfCouponsCreatedByCategoryDetails();

    /**
     * Returns list of active coupons created for SUBSCRIPTION/APPOINTMENT
     */
    List<CouponContextTypeData> getNumOfCouponsCreatedByContextTypeDetails();

    /**
     * Returns list of active coupons created for areas(BANGALORE/HYDERABAD/COCHIN etc)
     */
    List<CouponAreaData> getNumOfCouponsCreatedByAreaDetails();

    /**
     * Returns list of discount amount given by area
     */
    List<CouponDiscountAreaData> getCouponDiscountsGivenByArea();

    /**
     * Returns list of discount amount given by brand
     */
    List<CouponDiscountBrandData> getCouponDiscountsGivenByBrand();

    /**
     * Returns number of discounts given by area
     */
    List<CouponAreaData> getNumOfCouponDiscountsGivenByArea();

    /**
     * Returns number of discounts given by brand
     */
    List<CouponBrandData> getNumOfCouponDiscountsGivenByBrand();

    /**
     * Returns number of discounts given by referrer type(B2C/B2B)
     */
    List<CouponReferrerData> getNumOfCouponDiscountsGivenByReferrerType();

    /**
     * Returns weekly discount request status(APPLIED/CANCELED/REQUESTED) for each day
     */
    CouponDiscountStatusWeeklyData getCouponDiscReqStatusWeeklyDetails();

    /**
     * Returns weekly discount(min/max/avg) for each day
     */
    CouponDiscountWeeklyData getCouponDiscountWeeklyDetails();

    /**
     * Returns list of coupons getting expired in the weeks to come for a month
     */
    CouponExpiryWeeklyData getCouponExpiryDetails();

    /**
     * Returns list of coupons within a given range distribution
     */
    List<CouponDiscountsRangeData> getNumOfCouponsGivenRangeDetails();
}
