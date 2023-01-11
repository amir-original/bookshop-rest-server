package com.ws.bookshoprestserver.service;

import com.ws.bookshoprestserver.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> getAll();

    Optional<Author> getById(int id);

    Optional<Integer> addAuthor(Author author);

    int deleteAuthor(int id);

    Optional<Author> update(Author author);
}
