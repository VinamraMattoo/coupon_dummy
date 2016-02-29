package com.portea.commp.service.rapi;

import com.portea.commp.service.domain.SubmittedSmsVo;
import com.portea.commp.service.ejb.CommpTestRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * The REST service that implements the interface to the clients.
 */
@Path("testrapi/")
public class CommpRestTestService {

    private static final Logger LOG = LoggerFactory.getLogger(CommpRestTestService.class);

    @EJB
    private CommpTestRequestProcessor testRequestProcessor;

    public CommpRestTestService() {
        // No-arg Constructor
    }

    @GET
    @Path("/createSmsBatch")
    public Response createTestSmsBatch(
            @Context UriInfo uriInfo,
            @QueryParam("count") int count,
            @QueryParam("groupName") String groupName,
            @QueryParam("phoneNumber") String phoneNumber,
            @QueryParam("brandName") String brandName,
            @QueryParam("userId") Integer userId,
            @QueryParam("templateName") String templateName,
            @QueryParam("receiverType") String receiverType,
            @QueryParam("message") String message,
            @QueryParam("appendTimeStampToMessage") Boolean appendTimeStampToMessage,
            @QueryParam("login") String login) {

        LOG.trace(MessageFormat.format(
                "Received request at {0} with HTTP method {1}, with count as {2}, groupName as {3} & phoneNumber as {4}",
                uriInfo.getMatchedURIs(), "GET", count, groupName, phoneNumber
        ));

        if (count <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).header(
                    "Warning", "Count is 0. No sms to create").build();
        }

        if (groupName == null || groupName.length() == 0 ) {
            return Response.status(Response.Status.BAD_REQUEST).header(
                    "Warning", "SMS group name is missing").build();
        }

        if (phoneNumber == null || phoneNumber.length() != 10) {
            return Response.status(Response.Status.BAD_REQUEST).header(
                    "Warning", "Phone number is missing or invalid").build();
        }

        if (userId == null) {
            userId = 1;
        }

        if (receiverType == null) {
            receiverType = "SampleReceiverType";
        }

        if (login == null) {
            login = "tester";
        }

        Response info = testRequestProcessor.createSmsBatch(count, groupName, phoneNumber, userId,
                templateName, receiverType, message, brandName, appendTimeStampToMessage, login);

        return info;
    }

    @GET
    @Path("/sms/submitted")
    @Produces("application/json")
    public SubmittedSmsVo getSubmittedSms(@QueryParam("fromDate") Long fromDate,
                                          @QueryParam("tillDate") Long tillDate,
                                          @QueryParam("detailed") Boolean detailed) {

        SubmittedSmsVo submitted = testRequestProcessor.getSubmittedSms(fromDate, tillDate, detailed);
        return submitted;
    }

    @GET
    @Path("/smsGroup")
    @Produces("application/json")
    public List<String> getSmsGroupForNameMatch(@QueryParam("name") String name, @QueryParam("limit") Integer limit) {

        return testRequestProcessor.getSmsGroupForNameMatch(name, limit);
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public List<String> getUserForLoginNameMatch(@QueryParam("login") String login, @QueryParam("limit") Integer limit) {

        return testRequestProcessor.getUserForLoginNameMatch(login, limit);
    }

    @GET
    @Path("/errorCode/{code}")
    public Response echoErrorCode(@PathParam("code") Integer code) {

        return Response.status(code).build();
    }

    @GET
    @Path("/targetConfig/{tname}/configParam/{cname}/targetId/{id}/")
    public String getTargetConfigValue(@PathParam("tname") String targetTypeName,
                                        @PathParam("cname") String configParam, @PathParam("id") Integer targetId){

        return testRequestProcessor.getTargetConfigValue(targetTypeName, configParam, targetId);

    }

    @GET
    @Produces("text/plain")
    @Path("/pMockSms/submit")
    public String submitPMockSms(@QueryParam("count") Integer count, @QueryParam("error") Boolean error) {

        return testRequestProcessor.submitPMockSms(count, error);
    }

    @GET
    @Produces("text/plain")
    @Path("/pMockSms/status")
    public String getPMockSmsStatus(@QueryParam("status") String status, @QueryParam("error") Boolean error) {

        return testRequestProcessor.getPMockSmsStatus(status, error);
    }
}

