package rest;

import com.ws.bookshoprestserver.helper.HttpRequestHandler;
import com.ws.bookshoprestserver.domain.Author;
import com.ws.bookshoprestserver.domain.Book;
import com.ws.bookshoprestserver.domain.BookBuilder;
import com.ws.bookshoprestserver.domain.BookCategory;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class BookshopResourceShould {

    private static final String BASE_TARGET = "http://localhost:8080/bookshop-rest-server-1.0-SNAPSHOT/";
    private static final String END_POINT = BASE_TARGET + "api/books";
    private HttpRequestHandler httpHandler;

    @BeforeEach
    void setUp() {
        httpHandler = new HttpRequestHandler();
    }

    @Test
    void get_all_books() {
        HttpResponse<String> response = httpHandler
                .target(END_POINT)
                .mediaType(MediaType.APPLICATION_JSON)
                .GET().build();

        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
        assertThat(response.body()).isNotEmpty();
    }

    @Test
    void get_book_by_id() {
        String id = "12345678";
        HttpResponse<String> response = httpHandler
                .target(END_POINT)
                .path("/" + id)
                .mediaType(MediaType.APPLICATION_JSON)
                .GET().build();

        Book book = httpHandler.getResponse(response, Book.class);
        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
        assertThat(book.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void delete_book_by_id() {
        String id = generateRandomId();
        addFakeDataToDatabase(id);
        HttpResponse<String> response = httpHandler.target(END_POINT).path("/" + id)
                .mediaType(MediaType.APPLICATION_JSON).DELETE().build();

        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
    }

    @Test
    void add_book() {
        Book book = getBook(generateRandomId());
        HttpResponse<String> response = httpHandler.target(END_POINT).mediaType(MediaType.APPLICATION_JSON)
                .POST(book).build();
        assertThat(response.statusCode()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(response.body()).isNotEmpty();
    }

    @Test
    void update_book() {
        String id = generateRandomId();
        Book book = addFakeDataToDatabase(id);
        Book updateBook = new BookBuilder().setTitle(book.getTitle() +": updated")
                .setAuthors(book.getAuthors()).setDescription("Updated Description")
                .setId(id).setImagePath(book.getImagePath()).setCategory(book.getCategory()).createBook();

        HttpResponse<String> response = httpHandler.target(END_POINT).mediaType(MediaType.APPLICATION_JSON)
                .PUT(updateBook).build();
        Book responseBook = httpHandler.getResponse(response, Book.class);

        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
        assertThat(responseBook.getTitle()).isEqualTo(updateBook.getTitle());
    }

    private Book addFakeDataToDatabase(String id) {
        Book book = getBook(id);
         httpHandler.target(END_POINT).mediaType(MediaType.APPLICATION_JSON).POST(book).build();
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
        return bookBuilder.setId(id).setTitle("Refactoring "+ id.substring(1))
                .setDescription("As the application of object the Java programming" +
                        " language--has become commonplace, a new problem has emerged to confront the software development community.")
                .setPrice(49.47F)
                .setCategory(category)
                .setLink("https://www.amazon.com/Refactoring-Improving-Design-Existing-Code/dp/0201485672")
                .setImagePath("https://m.media-amazon.com/images/I/41xShlnTZTL._SX218_BO1,204,203,200_QL40_FMwebp_.jpg")
                .setAuthors(authors)
                .createBook();
    }

    public String generateRandomId() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }
}
