package dao;

import com.ws.bookshoprestserver.dao.BookshopDAO;
import com.ws.bookshoprestserver.dao.BookshopDAOImpl;
import domain.Author;
import domain.Book;
import domain.BookBuilder;
import domain.BookCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class BookshopDAOImplShould {

    private static final String HOST = "jdbc:mysql://localhost:3306/bookshop";
    private static final String USER = "amir";
    private static final String PASS = "@wsrmp1378";
    private static final String CONNECTION_FAILURE_MESSAGE = "we can't connect to database";
    private BookshopDAO dao;

    @BeforeEach
    void setUp() {
        dao = new BookshopDAOImpl();
    }

    @Test
    void connect_to_database() {
        try (Connection connection = DriverManager.getConnection(HOST, USER, PASS)) {
            if (connection == null)
                fail(CONNECTION_FAILURE_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            fail(CONNECTION_FAILURE_MESSAGE);
        }

    }

    @Test
    void get_all_books() {
        List<Book> books = dao.getAll();
        assertThat(books).isNotNull();
        assertThat(books).isNotEmpty();
    }

    @Test
    void get_book_by_id() {
        Book book = dao.getById("12345678");

        Assertions.assertThat(book).isNotNull();
    }

    @Test
    void delete_book() {
        String id = generateRandomId();
        addFakeDataToDatabase(id);
        int book = dao.deleteBook(id);
        assertThat(book).isEqualTo(1);
    }

    @Test
    void add_new_book() {
        String id = "12345678";
        Book book = getBook(id);
        assertThatNoException().isThrownBy(() -> dao.addBook(book));
        assertThat(dao.getById(id).getTitle()).isEqualTo(book.getTitle());
        dao.deleteBook(id);
    }

    @Test
    void update_book() {
        String id = generateRandomId();
        Book book = addFakeDataToDatabase(id);
        BookBuilder bookBuilder = new BookBuilder();
        Book updated = bookBuilder.setTitle(book.getTitle() + " :updated")
                .setDescription("desc updated")
                .setLink(book.getLink())
                .setAuthors(book.getAuthors())
                .setCategory(book.getCategory())
                .setPrice(45.1F)
                .setImagePath(book.getImagePath())
                .setId(id).createBook();

        assertThatNoException().isThrownBy(() -> dao.update(updated));
        assertThat(dao.getById(id).getTitle()).isEqualTo(updated.getTitle());
    }

    Book addFakeDataToDatabase(String id) {
        Book book = getBook(id);
        dao.addBook(book);
        return book;
    }

    private static Book getBook(String id) {
        BookCategory category = new BookCategory(1, "Software", "Software Development and programming");
        BookBuilder bookBuilder = new BookBuilder();
        List<Author> authors = new LinkedList<>();
        Author martinFowler = new Author(2, "Martin", "Fowler");
        Author kentBeck = new Author(3, "Kent ", "Beck");
        authors.add(kentBeck);
        authors.add(martinFowler);
        return bookBuilder.setId(id).setTitle("Refactoring")
                .setDescription("As the application of object the Java programming" +
                        " language--has become commonplace, a new problem has emerged to confront the software development community.")
                .setPrice(49.47F)
                .setCategory(category)
                .setLink("https://www.amazon.com/Refactoring-Improving-Design-Existing-Code/dp/0201485672")
                .setImagePath("https://m.media-amazon.com/images/I/41xShlnTZTL._SX218_BO1,204,203,200_QL40_FMwebp_.jpg")
                .setAuthors(authors)
                .createBook();
    }

    private String generateRandomId() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }
}
