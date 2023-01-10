package com.ws.bookshoprestserver.dao.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IsbnNotFoundExceptionMapper implements ExceptionMapper<IsbnNotFoundException> {

    @Override
    public Response toResponse(IsbnNotFoundException exception) {
        return Response.noContent().build();
    }
}
