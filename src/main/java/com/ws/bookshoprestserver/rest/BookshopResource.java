package com.ws.bookshoprestserver.rest;

import com.ws.bookshoprestserver.dao.exceptions.IsbnNotFoundException;
import domain.Book;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bytebuddy.implementation.bytecode.Throw;
import service.BookService;
import service.BookServiceImpl;

import java.util.List;
import java.util.Optional;

@Path("books")
@Stateless
public class BookshopResource {

    @EJB
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<Book> books = bookService.getAll();
        return Response.ok(books).build();
    }

    @GET
    @Path("{isbn:\\d{8}}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByIsbn(final @PathParam("isbn") String id) throws IsbnNotFoundException {
        Optional<Book> book = bookService.getById(id);
        return Response.ok(book.orElseThrow(IsbnNotFoundException::new)).build();
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
    public Response addBook(final Book book){
        System.out.println(book);
        bookService.addBook(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(final Book book){
        bookService.update(book);
        return Response.ok(book).build();
    }
}
