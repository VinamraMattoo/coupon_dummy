package com.portea.cpnen.rapi.service;

import com.portea.cpnen.rapi.annotation.PATCH;
import com.portea.cpnen.rapi.domain.ApplicableDiscountResp;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestCreateReq;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestUpdateReq;
import com.portea.cpnen.rapi.exception.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.MessageFormat;
import java.util.Date;

/**
 * This is a REST service which is a copy of its rel counterpart. This service
 * may be used to test availability of the service in the most basic form. This
 * service does not attempt to execute any business processes. Following kinds of testing
 * may be achieved
 * <li>Whether service is reachable</li>
 * <li>Whether request in correct form is reaching the server</li>
 * <li>Simulated throwing of exceptions</li>
 */
@Path("testrapi/")
public class CouponEngineRestTestService {

    public CouponEngineRestTestService() {
        // No-arg Constructor
    }

    private boolean toEcho(String echoParam) {
        if (echoParam != null && (
                echoParam.equalsIgnoreCase("y")
                || echoParam.equalsIgnoreCase("yes")
                || echoParam.equalsIgnoreCase("true")
                || echoParam.equalsIgnoreCase("1")
        )) {
            return true;
        }
        else {
            return false;
        }
    }

    private void throwRequestedException(String exParam) {

        ExceptionalCondition.Error errorEnum = null;

        try {
            errorEnum = ExceptionalCondition.Error.codeToEnum(exParam);
        }
        catch(IllegalArgumentException iae) {
            throw new BadRequestException(iae.getMessage());
        }

        CouponApplicationException exception = null;

        switch(errorEnum) {
            case RESERVED_FOR_ANOTHER_USER:
                exception = new ReservedForAnotherUserException("EGCODE1");
                break;
            case COUPON_VALIDITY_EXPIRED:
                exception = new CouponValidityExpiredException(
                        "EGCODE1", new Date(), new Date());
                break;
            case MULTIPLE_EXCLUSIVE_COUPONS:
                exception = new MultipleExclusiveCouponsException("CFLT1", "PREV1");
                break;
            case TRANSACTION_VALUE_OUT_OF_RANGE:
                exception = new TransactionValueOutOfRangeException("EGCODE1", 100, 300);
                break;
            case COUPON_CODE_TIMEOUT:
                exception = new CouponCodeTimeoutException("EGCODE1");
                break;
            case COUPON_INACTIVE:
                exception = new InactiveCouponException("EGCODE1");
                break;
            case COUPON_EXHAUSTED:
                exception = new ExhaustedCouponException("EGCODE1");
                break;
            case COUPON_INVALID:
                exception = new InvalidCouponException("EGCODE1");
                break;
            case REQUEST_ALREADY_COMPLETED:
                exception = new RequestAlreadyCompletedException("sample/testuri");
                break;
            case PRODUCT_INVALID:
                exception = new InvalidProductException("EDPROD1");
                break;
            case CONSUMER_INVALID:
                exception = new InvalidConsumerException("EGUSER1");
                break;
            default:
                throw new BadRequestException(exParam + "Error code not recognized");
        }

        throw exception;
    }

    @POST
    @Path("/cdr")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCouponDiscountRequestJSON(
            @Context UriInfo uriInfo,
            @QueryParam("echo") String echo,
            CouponDiscountRequestCreateReq cdrCreateReq) throws Exception {

        System.out.println(MessageFormat.format(
                "Received request [{0}] when called at {1} with HTTP method {2}",
                cdrCreateReq, uriInfo.getMatchedURIs(), "POST"
        ));

        Response response = null;

        if (toEcho(echo)) {
            response = Response.status(200).entity(cdrCreateReq).build();
        }
        else {
            response = Response.status(200).build();
        }

        return response;
    }

    @GET
    @Path("/cdr/{id}/discountAmt")
    @Produces("application/json")
    public ApplicableDiscountResp getApplicableDiscountAmount(
            @Context UriInfo uriInfo,
            @PathParam("id") int cdrId) {

        System.out.println(MessageFormat.format(
                "Received request at {0} with HTTP method {1}",
                uriInfo.getMatchedURIs(), "GET"
        ));

        ApplicableDiscountResp response = new ApplicableDiscountResp();
        response.setDiscountAmount(3003);

        return response;
    }

    @PATCH
    @Path("/cdr/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCouponDiscountRequestJSON(
            @Context UriInfo uriInfo,
            @QueryParam("echo") String echo,
            @PathParam("id") int cdrId,
            CouponDiscountRequestUpdateReq cdrUpdateReq) throws Exception {

        System.out.println(MessageFormat.format(
                "Received request [{0}] when called at {1} with HTTP method {2}",
                cdrUpdateReq, uriInfo.getMatchedURIs(), "PATCH"
        ));

        Response response = null;

        if (toEcho(echo)) {
            response = Response.status(200).entity(cdrUpdateReq).build();
        }
        else {
            response = Response.status(200).build();
        }

        return response;
    }

    @DELETE
    @Path("/cdr/{id}")
    public Response deleteCouponDiscountRequestJSON(
            @Context UriInfo uriInfo,
            @PathParam("id") int cdrId) {

        System.out.println(MessageFormat.format(
                "Received request at {0} with HTTP method {1}",
                uriInfo.getMatchedURIs(), "DELETE"
        ));

        return Response.status(200).build();
    }

    @GET
    @Path("/exception")
    @Produces("application/json")
    public void getApplicableDiscountAmount(
            @Context UriInfo uriInfo,
            @QueryParam("exception") String exceptionParam) {

        System.out.println(MessageFormat.format(
                "Received request at {0} with HTTP method {1}",
                uriInfo.getMatchedURIs(), "GET"
        ));

        if (exceptionParam == null) {
            throw new BadRequestException("Must specify 'exception' query parameter");
        }

        throwRequestedException(exceptionParam);
    }
}