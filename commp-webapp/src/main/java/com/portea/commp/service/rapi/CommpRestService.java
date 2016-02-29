package com.portea.commp.service.rapi;

import com.portea.commp.service.domain.*;
import com.portea.commp.service.ejb.CommpRequestProcessor;
import com.portea.commp.service.domain.BatchFailureResponse;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The REST service that implements the interface to the clients.
 */
@Path("smsen/rapi/")
public class CommpRestService {

    @EJB
    CommpRequestProcessor commpRequestProcessor;

    private static final Logger LOG = LoggerFactory.getLogger(CommpRestService.class);


    public CommpRestService() {
        // No-arg Constructor
    }

    /**
     * Receives a direct sms and sends straight to sms processing queues.
     */
    @POST
    @Path("direct/sendSms")
    @Consumes("application/json")
    public Response sendDirectSms(SmsAssemblyVo smsAssembly) {
        commpRequestProcessor.queueDirectSms(smsAssembly);
        return Response.status(200).build();
    }

    @PUT
    @Path("gateway/{name}/submissionCount/reset")
    public Response resetSubmissionCount(@PathParam("name") String gatewayName) {
        commpRequestProcessor.resetSmsSubmissionCount(gatewayName);
        return Response.status(200).build();
    }

    @GET
    @Path("gateway/{name}/submissionCount/")
    @Produces("application/json")
    public Integer getSubmissionCount(@PathParam("name") String gatewayName) {
        Integer submissionCount = commpRequestProcessor.getSmsSubmissionCount(gatewayName);
        return submissionCount;
    }

    @POST
    @Path("sendSms")
    @Consumes("application/json")
    @Produces("application/json")
    public SendSmsResponse sendSms(SendSmsRequest sendSmsRequest) {
        LogUtil.entryTrace("sendSms", LOG);

        SendSmsResponse sendSmsResponse = commpRequestProcessor.queueSms(sendSmsRequest);

        LogUtil.exitTrace("sendSms", LOG);
        return sendSmsResponse;
    }

    @GET
    @Path("/sms/{id}/status")
    @Produces("application/json")
    public SmsStatusResponse getSmsStatus(@PathParam("id") String id) {
        LogUtil.entryTrace("getSmsStatus", LOG);

        SmsStatusResponse smsStatusResponse = commpRequestProcessor.getSmsStatus(id);

        LogUtil.exitTrace("getSmsStatus", LOG);
        return smsStatusResponse;
    }

    @GET
    @Path("/batch/{id}/status")
    @Produces("application/json")
    public BatchStatusResponse getBatchStatus(@PathParam("id") String id) {
        LogUtil.entryTrace("getLotStatus", LOG);

        BatchStatusResponse batchStatusResponse = commpRequestProcessor.getBatchStatus(id);

        LogUtil.exitTrace("getLotStatus", LOG);
        return batchStatusResponse;
    }

    @GET
    @Path("/lot/{id}/status")
    @Produces("application/json")
    public LotStatusResponse getLotStatus(@PathParam("id") String id) {
        LogUtil.entryTrace("getLotStatus", LOG);

        LotStatusResponse lotStatusResponse = commpRequestProcessor.getLotStatus(id);

        LogUtil.exitTrace("getLotStatus", LOG);
        return lotStatusResponse;
    }

    @GET
    @Path("/batch/{id}/failure")
    @Produces("application/json")
    public BatchFailureResponse getBatchFailureStatus(@PathParam("id") String id) {
        LogUtil.entryTrace("getBatchFailureStatus", LOG);

        BatchFailureResponse reasons= commpRequestProcessor.getBatchFailureStatus(id);

        LogUtil.exitTrace("getBatchFailureStatus", LOG);
        return reasons;
    }
}