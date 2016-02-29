package com.portea.cpnen.web.rapi.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouponWebApplicationExceptionMapper
        implements ExceptionMapper<CouponWebApplicationException> {

    @Override
    public Response toResponse(CouponWebApplicationException exception) {
        Response response = Response.status(exception.getStatus())
                .header("X-Cpnen-web-Error", exception.getErrorRepresentation())
                .header("X-Cpnen-web-Error-Detail", exception.getExplanatoryMessage()).build();

        return response;
    }
}
