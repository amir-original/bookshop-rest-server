package com.ws.bookshoprestserver.dao;

import domain.Book;

import java.util.List;

public interface BookshopDAO {

    List<Book> getAll();

    Book getById(String id);

    void update(Book book);

    void addBook(Book book);

    void addBooks(List<Book> books);

    Integer deleteBook(String id);

}
