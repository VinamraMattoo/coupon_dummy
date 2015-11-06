package com.portea.commp.service.rapi;

import com.portea.commp.service.ejb.CommpTestRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.MessageFormat;

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
            @QueryParam("count") int count) {

        LOG.trace(MessageFormat.format(
                "Received request at {0} with HTTP method {1}, with count as {2}",
                uriInfo.getMatchedURIs(), "GET", count
        ));

        testRequestProcessor.createSmsBatch(count);

        return Response.status(200).build();
    }


}