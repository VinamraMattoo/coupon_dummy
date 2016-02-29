package com.portea.cpnen.web.rapi.service;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.rapi.annotation.PATCH;
import com.portea.cpnen.rapi.domain.*;
import com.portea.cpnen.web.rapi.domain.*;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("web/rws")
public class CouponWebRestService {

    private static final Logger LOG = LoggerFactory.getLogger(CouponWebRestService.class);
    private static final Integer DEFAULT_USER_ID = 1;

    @EJB
    CouponWebRequestProcessor couponWebRequestProcessor;


    @POST
    @Path("/coupon")
    @Consumes("application/json")
    public String createCoupon(@Context HttpServletRequest request, @Context UriInfo uriInfo, CouponCRUDRequest couponCRUDRequest) {

        Integer userId = getUserId(request);

        Coupon coupon = couponWebRequestProcessor.createCoupon(userId, couponCRUDRequest);

        return String.valueOf(coupon.getId());
    }

    @GET
    @Path("/coupon/{id}/")
    @Produces("application/json")
    public CouponVO getCoupon(@PathParam("id") Integer couponId) {

        CouponVO coupon = couponWebRequestProcessor.getCouponVO(couponId);

        return coupon;
    }

    @PUT @PATCH
    @Path("/coupon/{id}")
    @Consumes("application/json")
    public Response updateCoupon(@Context HttpServletRequest request,
                                 @PathParam("id") Integer couponId, CouponCRUDRequest couponUpdateRequest) {

        Integer userId = getUserId(request);

        couponWebRequestProcessor.updateCoupon(userId, couponId, couponUpdateRequest);

        return Response.status(200).build();
    }

    @PUT @PATCH
    @Path("/coupon/{id}/publish")
    public Response publishCoupon(@Context HttpServletRequest request,
                                  @PathParam("id") Integer couponId, @QueryParam("lastUpdatedOn") Long lastUpdatedOn) {

        Integer userId = getUserId(request);

        couponWebRequestProcessor.publishCoupon(userId, couponId, lastUpdatedOn);

        return Response.status(200).build();
    }

    @PUT @PATCH
    @Path("/coupon/{id}/deactivate")
    public Response deactivateCoupon(@Context HttpServletRequest request,
                                     @PathParam("id") Integer couponId, @QueryParam("lastUpdatedOn") Long lastUpdatedOn) {

        Integer userId = getUserId(request);

        couponWebRequestProcessor.deactivateCoupon(userId, couponId, lastUpdatedOn);

        return Response.status(200).build();
    }

    @PUT @PATCH
    @Path("/coupon/{couponId}/code/{codeId}/deactivate")
    public Response deactivateCouponCode(@Context HttpServletRequest request, 
                                         @PathParam("couponId") Integer couponId, @PathParam("codeId") Integer codeId) {

        Integer userId = getUserId(request);

        couponWebRequestProcessor.deactivateCouponCode(userId, couponId, codeId);

        return Response.status(200).build();
    }

    @PUT @PATCH
    @Path("/coupon/{couponId}/extendValidity/{epochTime}")
    public Response extendValidity(@Context HttpServletRequest request, @PathParam("epochTime") Long epochTime,
                                   @PathParam("couponId") Integer couponId, @QueryParam("lastUpdatedOn")Long lastUpdatedOn) {

        Integer userId = getUserId(request);

        couponWebRequestProcessor.extendValidity(userId, epochTime, couponId, lastUpdatedOn);

        return Response.status(200).build();
    }

    @DELETE
    @Path("/coupon/{id}")
    public Response deleteCoupon(@PathParam("id") Integer couponId, @QueryParam("lastUpdatedOn")Long lastUpdatedOn) {
        couponWebRequestProcessor.deleteCoupon(couponId, lastUpdatedOn);

        return Response.status(200).build();
    }

    @GET
    @Path("/coupon/list")
    @Produces("application/json")
    public CouponListResponse listCoupons(@Context HttpServletRequest request,
            @QueryParam("name") String name, @QueryParam("draft") String draft,
            @QueryParam("published") String published, @QueryParam("deactivated") String deactivated,
            @QueryParam("from") String from, @QueryParam("till") String till,
            @QueryParam("inclusive") String inclusive, @QueryParam("updateFrom") String updateFrom,
            @QueryParam("updateTill") String updateTill, @QueryParam("deactivateFrom") String deactivateFrom,
            @QueryParam("deactivateTill") String deactivateTill, @QueryParam("appDurationFrom") String appDurationFrom,
            @QueryParam("appDurationTill") String appDurationTill, @QueryParam("publishedFrom") String publishedFrom,
            @QueryParam("publishedTill") String publishedTill, @QueryParam("discountFrom") String discountFrom,
            @QueryParam("discountTill") String discountTill, @QueryParam("transactionFrom") String transactionFrom,
            @QueryParam("transactionTill") String transactionTill, @QueryParam("couponAppType") String couponAppType,
            @QueryParam("actor") String actor, @QueryParam("contextType") String contextType,
            @QueryParam("order") String order, @QueryParam("limit") String limit,
            @QueryParam("offset") String offset, @QueryParam("active") String active,
            @QueryParam("global") String global, @QueryParam("sort")String sort
    ) {
        Integer userId = getUserId(request);

        CouponListResponse coupons = couponWebRequestProcessor.getCoupons(userId,
                name, draft, published , deactivated, from, till, inclusive, updateFrom, updateTill,
                deactivateFrom, deactivateTill, appDurationFrom, appDurationTill, publishedFrom, publishedTill,
                discountFrom, discountTill, transactionFrom, transactionTill, couponAppType, actor, contextType,
                limit, offset, active, global, order, sort
        );
        return coupons;
    }

    @GET
    @Path("/coupon/{id}/codes")
    @Produces("application/json")
    public CouponCodesResponse listCouponCodes(@QueryParam("offset") Integer offset,
                                               @QueryParam("limit") Integer limit, @PathParam("id") Integer couponId) {
        return couponWebRequestProcessor.getCouponCodes(couponId, offset, limit);
    }

    @GET
    @Path(("/coupon/codes"))
    @Produces("application/json")
    public CouponCodesResponse listCouponCodes(@Context HttpServletRequest request,
            @QueryParam("offset") String offset, @QueryParam("limit") String limit,
            @QueryParam("name") String name, @QueryParam("active") String active,
            @QueryParam("deactivated") String deactivated, @QueryParam("from") String createdFrom,
            @QueryParam("till") String createdTill, @QueryParam("deactivateFrom") String deactivateFrom,
            @QueryParam("deactivateTill") String deactivateTill, @QueryParam("channel") String channel,
            @QueryParam("order") String order, @QueryParam("sort") String sort
    ) {

        Integer userId = getUserId(request);

        return couponWebRequestProcessor.getCouponCodes(userId, offset, limit, name, active,
                deactivated, createdFrom, createdTill, deactivateFrom, deactivateTill, channel, order, sort);
    }

    @GET
    @Path("/products")
    @Produces("application/json")
    public List<ProductVO> listProducts(@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
        List<ProductVO> products = couponWebRequestProcessor.getProducts();
        return products;
    }

    @GET
    @Path("/brands")
    @Produces("application/json")
    public List<BrandVo> listBrands() {

        List<BrandVo> brandVos = couponWebRequestProcessor.getBrands();
        return brandVos;
    }

    @GET
    @Path("/areas")
    @Produces("application/json")
    public List<AreaVo> listAreas() {

        List<AreaVo> areaVos = couponWebRequestProcessor.getAreas();
        return areaVos;
    }

    @GET
    @Path("/getReferrers")
    @Produces("application/json")
    public List<ReferrersVo> listReferrers(@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {

        List<ReferrersVo> referrerVos = couponWebRequestProcessor.getReferrers();
        return referrerVos;
    }

    @POST
    @Path("/coupon/{id}/code")
    @Consumes("application/json")
    @Produces("application/json")
    public Integer createCouponCode(@Context HttpServletRequest request, @PathParam("id") Integer couponId,
                                     CouponCodeCreationRequest couponCodeCreationRequest) {

        Integer userId = getUserId(request);

        Integer codeId = couponWebRequestProcessor.createCouponCode(couponId, couponCodeCreationRequest, userId);

        return codeId;
    }


    @POST
    @Path("/coupon/{couponId}/code/{codeId}/reserve")
    @Consumes("application/json")
    public Response createReservation(@Context HttpServletRequest request, @PathParam("couponId") Integer couponId,
                                      @PathParam("codeId") Integer codeId, CouponCodeCreationRequest.Reservation reservation) {
        Integer userId = getUserId(request);

        couponWebRequestProcessor.createReservation(couponId, codeId, reservation, userId);

        return Response.status(200).build();
    }

    @GET
    @Path("/coupon/{couponId}/code/{codeId}")
    @Produces("application/json")
    public CouponCodeVO getCouponCodeVo(@PathParam("couponId") Integer couponId, @PathParam("codeId") Integer codeId) {

        CouponCodeVO couponCodeVO = couponWebRequestProcessor.getCouponCode(couponId, codeId);

        return couponCodeVO;
    }

    @GET
    @Path("/user/{name}/list")
    @Produces("application/json")
    public List<String> getUserNames(@PathParam("name") String name, @QueryParam("limit") Integer limit) {

        List<String> userNames = couponWebRequestProcessor.getUserNames(name, limit);

        return userNames;
    }

    @GET
    @Path("/user/{name}")
    @Produces("application/json")
    public Integer getUserId(@PathParam("name") String name) {

        Integer userId = couponWebRequestProcessor.getUserId(name);

        return userId;
    }

    @GET
    @Path("/coupon")
    @Produces("application/json")
    public Integer getCouponId(@QueryParam("name") String name) {

        Integer couponId = couponWebRequestProcessor.getCouponId(name);

        return couponId;
    }

    @GET
    @Path(("/couponCode"))
    @Produces("application/json")
    public Integer getCouponCodeId(@QueryParam("code") String code) {

        Integer codeId = couponWebRequestProcessor.getCouponCodeId(code);

        return codeId;
    }

    private Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Integer userId = DEFAULT_USER_ID;

        if(session != null) {  //TODO:: bypassing session, remove this later.
            userId = (Integer) session.getAttribute("userId");
            if(userId == null) {
                userId = DEFAULT_USER_ID;
            }
        }
        return userId;
    }

    @GET
    @Path("/coupon/getCategories")
    @Produces("application/json")
    public CouponCategoriesResponse getCategories(@Context HttpServletRequest request) {

        Integer userId = getUserId(request);

        CouponCategoriesResponse categories = couponWebRequestProcessor.getCategories(userId);

        return categories;
    }

    // .......................................... DASHBOARD ..........................................//

    @GET
    @Path("/coupon/actor/details")
    @Produces("application/json")
    public List<CouponActorData> getNumOfCouponsCreatedByActorDetails() {
        LogUtil.entryTrace("getNumOfCouponsCreatedByActorDetails", LOG);

        List<CouponActorData> couponActorData = couponWebRequestProcessor.getNumOfCouponsCreatedByActorDetails();

        LogUtil.exitTrace("getNumOfCouponsCreatedByActorDetails", LOG);
        return couponActorData;
    }

    @GET
    @Path("/coupon/category/details")
    @Produces("application/json")
    public List<CouponCategoryData> getNumOfCouponsCreatedByCategoryDetails() {
        LogUtil.entryTrace("getNumOfCouponsCreatedByCategoryDetails", LOG);

        List<CouponCategoryData> couponCategoryData = couponWebRequestProcessor.getNumOfCouponsCreatedByCategoryDetails();

        LogUtil.exitTrace("getNumOfCouponsCreatedByCategoryDetails", LOG);
        return couponCategoryData;
    }

    @GET
    @Path("/coupon/contextType/details")
    @Produces("application/json")
    public List<CouponContextTypeData> getNumOfCouponsCreatedByContextTypeDetails() {
        LogUtil.entryTrace("getNumOfCouponsCreatedByContextTypeDetails", LOG);

        List<CouponContextTypeData> couponContextTypeData = couponWebRequestProcessor.getNumOfCouponsCreatedByContextTypeDetails();

        LogUtil.exitTrace("getNumOfCouponsCreatedByContextTypeDetails", LOG);
        return couponContextTypeData;
    }

    @GET
    @Path("/coupon/area/details")
    @Produces("application/json")
    public List<CouponAreaData> getNumOfCouponsCreatedByAreaDetails() {
        LogUtil.entryTrace("getNumOfCouponsCreatedByAreaDetails", LOG);

        List<CouponAreaData> couponAreaData = couponWebRequestProcessor.getNumOfCouponsCreatedByAreaDetails();

        LogUtil.exitTrace("getNumOfCouponsCreatedByAreaDetails", LOG);
        return couponAreaData;
    }

    @GET
    @Path(("/coupon/discountRange"))
    @Produces("application/json")
    public List<CouponDiscountsRangeData> getNumOfCouponsGivenRangeDetails() {
        LogUtil.entryTrace("getNumOfCouponsGivenRangeDetails", LOG);

        List<CouponDiscountsRangeData> couponsWithinDiscountRange = couponWebRequestProcessor.getNumOfCouponsGivenRangeDetails();

        LogUtil.entryTrace("getNumOfCouponsGivenRangeDetails", LOG);

        return couponsWithinDiscountRange;
    }

    @GET
    @Path("/coupon/area/discountDetails")
    @Produces("application/json")
    public List<CouponDiscountAreaData> getCouponDiscountsGivenByArea() {
        LogUtil.entryTrace("getCouponDiscountsGivenByArea", LOG);

        List<CouponDiscountAreaData> couponAreaData = couponWebRequestProcessor.getCouponDiscountsGivenByArea();

        LogUtil.exitTrace("getCouponDiscountsGivenByArea", LOG);
        return couponAreaData;
    }

    @GET
    @Path("/coupon/brand/discountDetails")
    @Produces("application/json")
    public List<CouponDiscountBrandData> getCouponDiscountsGivenByBrand() {
        LogUtil.entryTrace("getCouponDiscountsGivenByBrand", LOG);

        List<CouponDiscountBrandData> couponBrandData = couponWebRequestProcessor.getCouponDiscountsGivenByBrand();

        LogUtil.exitTrace("getCouponDiscountsGivenByBrand", LOG);
        return couponBrandData;
    }

    @GET
    @Path("/couponDiscount/area/details")
    @Produces("application/json")
    public List<CouponAreaData> getNumOfCouponDiscountsGivenByArea() {
        LogUtil.entryTrace("getNumOfCouponDiscountsGivenByArea", LOG);

        List<CouponAreaData> couponAreaData = couponWebRequestProcessor.getNumOfCouponDiscountsGivenByArea();

        LogUtil.exitTrace("getNumOfCouponDiscountsGivenByArea", LOG);
        return couponAreaData;
    }

    @GET
    @Path("/couponDiscount/brand/details")
    @Produces("application/json")
    public List<CouponBrandData> getNumOfCouponDiscountsGivenByBrand() {
        LogUtil.entryTrace("getNumOfCouponDiscountsGivenByBrand", LOG);

        List<CouponBrandData> couponBrandData = couponWebRequestProcessor.getNumOfCouponDiscountsGivenByBrand();

        LogUtil.exitTrace("getNumOfCouponDiscountsGivenByBrand", LOG);
        return couponBrandData;
    }

    @GET
    @Path("/couponDiscount/referrerType/details")
    @Produces("application/json")
    public List<CouponReferrerData> getNumOfCouponDiscountsGivenByReferrerType() {
        LogUtil.entryTrace("getNumOfCouponDiscountsGivenByReferrerType", LOG);

        List<CouponReferrerData> couponReferrerData = couponWebRequestProcessor.getNumOfCouponDiscountsGivenByReferrerType();

        LogUtil.exitTrace("getNumOfCouponDiscountsGivenByReferrerType", LOG);
        return couponReferrerData;
    }

    @GET
    @Path("/couponDiscount/status/details")
    @Produces("application/json")
    public CouponDiscountStatusWeeklyData getCouponDiscReqStatusWeeklyDetails() {
        LogUtil.entryTrace("getCouponDiscReqStatusWeeklyDetails", LOG);

        CouponDiscountStatusWeeklyData couponDiscountStatusData = couponWebRequestProcessor.getCouponDiscReqStatusWeeklyDetails();

        LogUtil.exitTrace("getCouponDiscReqStatusWeeklyDetails", LOG);
        return couponDiscountStatusData;
    }

    @GET
    @Path("/couponDiscount/details")
    @Produces("application/json")
    public CouponDiscountWeeklyData getCouponDiscountWeeklyDetails() {
        LogUtil.entryTrace("getCouponDiscountWeeklyDetails", LOG);

        CouponDiscountWeeklyData couponDiscountData = couponWebRequestProcessor.getCouponDiscountWeeklyDetails();

        LogUtil.exitTrace("getCouponDiscountWeeklyDetails", LOG);
        return couponDiscountData;
    }

    @GET
    @Path("/couponExpiry/details")
    @Produces("application/json")
    public CouponExpiryWeeklyData getCouponExpiryDetails() {
        LogUtil.entryTrace("getCouponExpiryDetails", LOG);

        CouponExpiryWeeklyData couponExpiryWeeklyData = couponWebRequestProcessor.getCouponExpiryDetails();

        LogUtil.exitTrace("getCouponExpiryDetails", LOG);
        return couponExpiryWeeklyData;
    }

}
