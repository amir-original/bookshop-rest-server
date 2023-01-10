package com.ws.bookshoprestserver.rest;

import com.ws.bookshoprestserver.dao.exceptions.AuthorIdNotFoundException;
import com.ws.bookshoprestserver.domain.Author;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.ws.bookshoprestserver.service.AuthorService;

import java.util.List;

@Path("authors")
@Stateless
public class AuthorResource {

    @EJB
    private AuthorService authorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Author> authors = authorService.getAll();
        return Response.ok(authors).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(final @PathParam("id") int id) throws AuthorIdNotFoundException {
        Author author = authorService.getById(id).orElseThrow(AuthorIdNotFoundException::new);
        return Response.ok(author).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAuthor(final @PathParam("id") int id) throws AuthorIdNotFoundException {
        int isDeleted = authorService.deleteAuthor(id);
        if (isDeleted == 1)
            return Response.noContent().build();

        throw new AuthorIdNotFoundException();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(@Valid final Author author) {
        authorService.addAuthor(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@Valid final Author author) throws AuthorIdNotFoundException {
        Author updatedAuthor = authorService.update(author).orElseThrow(AuthorIdNotFoundException::new);
        return Response.ok(updatedAuthor).build();
    }
}
