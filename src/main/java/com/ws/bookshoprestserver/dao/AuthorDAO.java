package com.ws.bookshoprestserver.dao;

import com.ws.bookshoprestserver.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    List<Author> getAll();

    Optional<Author> getById(int id);

    Integer addAuthor(Author author);

    int deleteAuthor(int id);

    Author update(Author author);

    void addAuthors(List<Author> authors);
}
