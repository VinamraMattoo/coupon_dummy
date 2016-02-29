package com.portea.commp.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CommpApplicationExceptionMapper implements ExceptionMapper<CommpApplicationException>{
    @Override
    public Response toResponse(CommpApplicationException exception) {
        Response response = Response.status(exception.getStatus())
                .header("X-Commp-Error", exception.getErrorRepresentation())
                .header("X-Commp-Error-Detail", exception.getExplanatoryMessage()).build();

        return response;
    }
}
