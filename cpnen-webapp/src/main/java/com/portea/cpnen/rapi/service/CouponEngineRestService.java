package com.portea.cpnen.rapi.service;

import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.rapi.annotation.PATCH;
import com.portea.cpnen.rapi.domain.ApplicableDiscountResp;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestCreateReq;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestUpdateReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * The REST service that implements the interface to the clients.
 */
@Path("rapi/")
public class CouponEngineRestService {

    private static final Logger LOG = LoggerFactory.getLogger(CouponEngineRestService.class);

    @EJB
    CouponEngineRequestProcessor requestProcessor;

    public CouponEngineRestService() {
        // No-arg Constructor
    }

    @POST
    @Path("/cdr")
    @Consumes("application/json")
    public Response createCouponDiscountRequestJSON(@Context UriInfo uriInfo, CouponDiscountRequestCreateReq cdrCreateReq) {

        LOG.info("Received a request for creation of a coupon discount");

        CouponDiscountRequest newRequest = requestProcessor.createCouponDiscountRequest(cdrCreateReq);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(newRequest.getId()));

        LOG.info("Returning status {}. Id of newly created request is {}",
                200, newRequest.getId());

        return Response.created(builder.build()).status(200).build();
    }

    @GET
    @Path("/cdr/{id}/discountAmt")
    @Produces("application/json")
    public ApplicableDiscountResp getApplicableDiscountAmount(@PathParam("id") int cdrId) {
        return requestProcessor.getCurrentApplicableDiscount(cdrId);
    }

    @PATCH
    @Path("/cdr/{id}")
    @Consumes("application/json")
    public Response updateCouponDiscountRequestJSON(@PathParam("id") int cdrID,
                                                CouponDiscountRequestUpdateReq cdrUpdateReq) {
        // TODO Complete Implementation
        requestProcessor.updateCouponDiscountRequest(cdrUpdateReq);
        return Response.status(500).build();
    }

    @DELETE
    @Path("/cdr/{id}")
    public Response deleteCouponDiscountRequestJSON(@PathParam("id") int cdrId) {
        // TODO Complete Implementation
        requestProcessor.deleteCouponDiscountRequest(cdrId);
        return Response.status(500).build();
    }
}