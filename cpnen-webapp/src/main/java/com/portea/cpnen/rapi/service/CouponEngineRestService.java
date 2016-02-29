package com.portea.cpnen.rapi.service;

import com.portea.cpnen.rapi.annotation.PATCH;
import com.portea.cpnen.rapi.domain.*;
import com.portea.cpnen.vo.ProductVo;
import com.portea.cpnen.web.rapi.domain.CouponCodeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

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
    @Produces("application/json")
    public CouponDiscountRequestResponse createCouponDiscountRequestJSON(@Context UriInfo uriInfo, CouponDiscountRequestCreateReq cdrCreateReq) {

        LOG.trace("Received a request for creation of a coupon discount");

        CouponDiscountRequestResponse discountResponse = requestProcessor.createCouponDiscountRequest(cdrCreateReq);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(discountResponse.getCdrId()));

        LOG.trace("Returning status {}. Id of newly created request is {}",
                200, discountResponse.getCdrId());

        return discountResponse;
    }

    @GET
    @Path("/cdr/{id}/discountAmt")
    @Produces("application/json")
    public ApplicableDiscountResp getApplicableDiscountAmount(@PathParam("id") int cdrId) {

        LOG.trace("Received a request for getting the applicable discount of a coupon discount request");

        ApplicableDiscountResp applicableDiscountResp = requestProcessor.getCurrentApplicableDiscount(cdrId);

        LOG.trace("Returning status {}. ", 200);

        return applicableDiscountResp;
    }

    @PUT
    @Path("/cdr/{id}")
    @Consumes("application/json")
    public Response updateCouponDiscountRequestJSON(@PathParam("id") int cdrID,
                                                    CouponDiscountRequestUpdateReq cdrUpdateReq) {

        LOG.trace("Received a request for updating a coupon discount request");

        requestProcessor.updateCouponDiscountRequest(cdrID, cdrUpdateReq);

        LOG.trace("Returning status {}. Id of updated request is {}",
                200, cdrID);

        return Response.status(200).build();
    }

    @PUT @PATCH
    @Path("/cdr/{id}/cancel")
    public Response cancelCouponDiscountRequest(@PathParam("id") int cdrID) {

        LOG.trace("Received a request for canceling a coupon discount request");

        requestProcessor.cancelCouponDiscountRequest(cdrID);

        LOG.trace("Returning status {}. Id of canceled request is {}",
                200, cdrID);

        return Response.status(200).build();
    }

    @PUT
    @Path("/cdr/{id}/code/{code}")
    public Response addCoupon(@PathParam("id") int cdrId, @PathParam("code") String code) {

        LOG.trace("Received a request for adding a coupon code from a coupon discount request");

        requestProcessor.addCouponCodeToRequest(cdrId, code);

        LOG.trace("Returning status {}. ", 200);

        return Response.status(200).build();
    }

    @DELETE
    @Path("/cdr/{id}/code/{code}")
    public Response deleteCoupon(@PathParam("id") int cdrId, @PathParam("code") String code) {

        LOG.trace("Received a request for deleting a coupon code from a coupon discount request");

        requestProcessor.deleteCouponCodeFromRequest(cdrId, code);

        LOG.trace("Returning status {}. ", 200);

        return Response.status(200).build();
    }

    @PUT
    @Path("/cdr/{id}/product")
    @Consumes("application/json")
    public Response addProduct(@PathParam("id") int cdrId, ProductUpdateReq productUpdateReq) {

        LOG.trace("Received a request for adding a product from a coupon discount request");

        requestProcessor.addProductToRequest(cdrId, productUpdateReq);

        LOG.trace("Returning status {}. ", 200);

        return Response.status(200).build();
    }

    @DELETE
    @Path("/cdr/{id}/product/{productId}/{productType}")
    @Consumes("application/json")
    public Response deleteProduct(@PathParam("id") int cdrId, @PathParam("productId") int productId, @PathParam("productType") String productType, CostUpdateReq costUpdateReq) {

        LOG.trace("Received a request for deleting a product from a coupon discount request");

        requestProcessor.deleteProductFromRequest(cdrId, productId, productType, costUpdateReq);

        LOG.trace("Returning status {}. ", 200);

        return Response.status(200).build();
    }

    @GET
    @Path("/cdr/{id}/codes")
    @Produces("application/json")
    public List<CouponCodeVO> listCouponCodes(@PathParam("id") int cdrId){

        LOG.trace("Received a request for getting the list of coupon codes of a coupon discount request");

        List<CouponCodeVO> couponCodeVos = requestProcessor.getCouponCodes(cdrId);

        LOG.trace("Returning status {}. ", 200);

        return couponCodeVos;
    }

    @GET
    @Path("/cdr/{id}/products")
    @Produces("application/json")
    public List<ProductVo> listProducts(@PathParam("id") int cdrId){

        LOG.trace("Received a request for getting the list of products of a coupon discount request");

        List<ProductVo> productVos = requestProcessor.getProducts(cdrId);

        LOG.trace("Returning status {}. ", 200);

        return productVos;
    }

    @PUT @PATCH
    @Path("/cdr/{id}/commit")
    public Response commitCDR(@PathParam("id") int cdrId, @QueryParam("clientContextId") String clientContextId) {

        LOG.trace("Received a request for committing of a coupon discount request");

        requestProcessor.commitCDR(cdrId, clientContextId);

        LOG.trace("Returning status {}. ", 200);

        return Response.status(200).build();
    }

    @PUT @PATCH
    @Path("/cdr/{cdrId}/apply")
    public Response applyCDR(@PathParam("cdrId") Integer cdrId, @QueryParam("clientContextId") String clientContextId) {

        LOG.trace("Received a request for applying a coupon discount request");

        requestProcessor.applyCDR(cdrId, clientContextId);

        LOG.trace("Returning status {}. ", 200);

        return Response.status(200).build();
    }


    @GET
    @Path("/cdr/{id}/status")
    @Produces("application/json")
    public CouponDiscountRequestStatusResp getCDRStatus(@PathParam("id") int cdrId) {

        LOG.trace("Received a request for getting status of a coupon discount request");

        CouponDiscountRequestStatusResp couponDiscountRequestStatusResp = requestProcessor.getCDRStatus(cdrId);

        LOG.trace("Returning status {}. ", 200);

        return couponDiscountRequestStatusResp;

    }

    @GET
    @Path("/coupon")
    @Produces("application/json")
    public CouponInfoResponse getCoupon(@QueryParam("code") String code) {

        LOG.trace("Received a request for getting status of a coupon discount request");

        CouponInfoResponse couponInfoResponse = requestProcessor.getCoupon(code);

        LOG.trace("Returning status {}. ", 200);

        return couponInfoResponse;
    }

    @GET
    @Path("/healthCheck")
    public Response healthCheck() {
        return Response.status(200).build();
    }
}