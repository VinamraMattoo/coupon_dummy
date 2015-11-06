package com.portea.cpnen.rapi.service;

import com.portea.cpnen.rapi.exception.CouponApplicationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouponApplicationExceptionMapper
        implements ExceptionMapper<CouponApplicationException> {

    @Override
    public Response toResponse(CouponApplicationException exception) {
        Response response = Response.status(exception.getStatus())
                .header("X-Cpnen-Error", exception.getErrorRepresentation())
                .header("X-Cpnen-Error-Detail", exception.getExplanatoryMessage()).build();

        return response;
    }
}
