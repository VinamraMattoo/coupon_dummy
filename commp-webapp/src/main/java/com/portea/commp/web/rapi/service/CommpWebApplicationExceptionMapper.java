package com.portea.commp.web.rapi.service;

import com.portea.commp.web.rapi.exception.CommpWebApplicationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CommpWebApplicationExceptionMapper
        implements ExceptionMapper<CommpWebApplicationException> {

    @Override
    public Response toResponse(CommpWebApplicationException exception) {
        Response response = Response.status(exception.getStatus())
                .header("X-Commp-web-Error", exception.getErrorRepresentation())
                .header("X-Commp-web-Error-Detail", exception.getExplanatoryMessage()).build();

        return response;
    }
}
