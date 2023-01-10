package com.ws.bookshoprestserver.service;

import com.ws.bookshoprestserver.dao.AuthorDAO;
import com.ws.bookshoprestserver.domain.Author;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Optional;
@Stateless
public class AuthorServiceImpl implements AuthorService {

    @EJB
    private AuthorDAO dao;

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Override
    public Optional<Author> getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void addAuthor(Author author) {
        dao.addAuthor(author);
    }

    @Override
    public int deleteAuthor(int id) {
        return dao.deleteAuthor(id);
    }

    @Override
    public Optional<Author> update(Author author) {
        return Optional.ofNullable(dao.update(author));
    }
}
