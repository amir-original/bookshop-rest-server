package service;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Optional<Book> getById(String id);

    void update(Book book);

    void addBook(Book book);

    void addBooks(List<Book> books);

    Optional<Integer> deleteBook(String id);

}
