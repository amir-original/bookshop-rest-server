package com.ws.bookshoprestserver.dao.exceptions;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthorIdNotFoundExceptionMapper implements ExceptionMapper<AuthorIdNotFoundException> {

    @Override
    public Response toResponse(AuthorIdNotFoundException exception) {
        return Response.status(Response.Status.FOUND).build();
    }
}
