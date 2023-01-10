package com.ws.bookshoprestserver.service;

import com.ws.bookshoprestserver.dao.BookshopDAO;
import com.ws.bookshoprestserver.domain.Book;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Optional;

@Stateless
public class BookServiceImpl implements BookService {
    @EJB
    private BookshopDAO dao;

    @Override
    public List<Book> getAll() {
        return dao.getAll();
    }

    @Override
    public Optional<Book> getById(String id) {
        return Optional.of(dao.getById(id));
    }

    @Override
    public void update(Book book) {
        dao.update(book);
    }

    @Override
    public void addBook(Book book) {
        dao.addBook(book);
    }

    @Override
    public void addBooks(List<Book> books) {

    }

    @Override
    public Optional<Integer> deleteBook(String id) {
        return Optional.of(dao.deleteBook(id));
    }
}
