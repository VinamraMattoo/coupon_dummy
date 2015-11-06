package com.portea.cpnen.web.rapi.service;

import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.rapi.annotation.PATCH;
import com.portea.cpnen.web.rapi.domain.*;
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

    @EJB
    CouponWebRequestProcessor couponWebRequestProcessor;


    @POST
    @Path("/coupon")
    @Consumes("application/json")
    public Integer createCoupon(@Context HttpServletRequest request, @Context UriInfo uriInfo, CouponCreationRequest couponCreationRequest){

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");

        Coupon coupon = couponWebRequestProcessor.createCoupon(1, couponCreationRequest);

        return coupon.getId();
    }

    @GET
    @Path("/coupon/{id}/")
    @Produces("application/json")
    public CouponVO getCoupon(@PathParam("id") int couponId){
        CouponVO coupon = couponWebRequestProcessor.getCoupon(couponId);
        return coupon;
    }

    @PATCH
    @Path("/coupon/{id}")
    @Consumes("application/json")
    public Response updateCoupon(@Context HttpServletRequest request, @PathParam("id") int couponId, CouponUpdateRequest couponUpdateRequest){

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");

        couponWebRequestProcessor.updateCoupon(userId, couponId, couponUpdateRequest);
        return Response.status(200).build();
    }

    @GET
    @Path("/coupon/{id}/publish")
    public Response publishCoupon(@Context HttpServletRequest request, @PathParam("id") int couponId){

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");

        couponWebRequestProcessor.publishCoupon(userId, couponId);

        return Response.status(200).build();
    }

    @GET
    @Path("/coupon/list")
    @Produces("application/json")
    public CouponListResponse listCoupons(@QueryParam("offset") int offset, @QueryParam("limit") int limit){
        CouponListRequest couponListRequest = new CouponListRequest();
        couponListRequest.setStartIndex(offset);
        couponListRequest.setRequestedCount(limit);
        CouponListResponse coupons = couponWebRequestProcessor.getCoupons(couponListRequest);
        return coupons;
    }

}
