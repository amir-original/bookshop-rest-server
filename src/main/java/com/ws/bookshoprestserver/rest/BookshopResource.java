package com.ws.bookshoprestserver.rest;

import com.ws.bookshoprestserver.dao.exceptions.IsbnNotFoundException;
import com.ws.bookshoprestserver.domain.Book;
import com.ws.bookshoprestserver.domain.LinkResource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import com.ws.bookshoprestserver.service.BookService;

import java.util.List;
import java.util.Optional;

@Path("books")
@Stateless
public class BookshopResource {

    @EJB
    private BookService bookService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<Book> books = bookService.getAll();
       books.forEach(book -> {
           Link delete = prepareLink("deleteBook", book, "delete", "DELETE");
           Link self = prepareLink("getBookByIsbn", book, "self", "GET");
           addLinksToBook(book, delete, self);
       });
        GenericEntity<List<Book>> bookWrapper = new GenericEntity<>(books) {};
        return Response.ok(bookWrapper).build();
    }

    @GET
    @Path("{isbn:\\d{8}}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByIsbn(final @PathParam("isbn") String id) throws IsbnNotFoundException {
        Optional<Book> book = bookService.getById(id);
        if (book.isPresent()) {
            Link delete = prepareLink("deleteBook", book.get(), "delete", "DELETE");
            Link self = prepareLink("getBookByIsbn", book.get(), "self", "GET");
            addLinksToBook(book.get(), delete, self);
            return Response.ok(book.get()).links(self,delete).build();
        }
        throw new IsbnNotFoundException();
    }

    @DELETE
    @Path("{isbn:\\d{8}}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(final @PathParam("isbn") String isbn) throws IsbnNotFoundException {
        Integer integer = bookService.deleteBook(isbn).orElseThrow(IsbnNotFoundException::new);
        return Response.ok(integer).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(@Valid final Book book) {
        bookService.addBook(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@Valid final Book book) {
        bookService.update(book);
        return Response.ok(book).build();
    }


    private static void addLinksToBook(Book book, Link delete, Link self) {
        LinkResource deleteLink = new LinkResource(delete);
        LinkResource selfLink = new LinkResource(self);
        book.addLink(deleteLink);
        book.addLink(selfLink);
    }

    private Link prepareLink(String methodName, Book book, String rel, String type) {
        return Link.fromUri(uriInfo.getBaseUriBuilder()
                        .path(getClass())
                        .path(getClass(), methodName)
                        .build(book.getId()))
                .rel(rel)
                .type(type).build();
    }
}
