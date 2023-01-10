package rest;

import com.google.gson.Gson;
import com.ws.bookshoprestserver.helper.HttpRequestHandler;
import com.ws.bookshoprestserver.domain.Book;
import com.ws.bookshoprestserver.domain.BookBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

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
        Book book = FakerBooks.addFakeBookToDb();
        HttpResponse<String> response = httpHandler
                .target(END_POINT)
                .path("/" + book.getId())
                .mediaType(MediaType.APPLICATION_JSON)
                .GET().build();

        Book resBook = httpHandler.getResponse(response, Book.class);
        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
        assertThat(book.getTitle()).isEqualTo(resBook.getTitle());
    }

    @Test
    void delete_book_by_id() {
        String id = FakerBooks.generateRandomId();
        FakerBooks.addFakeDataToDatabase(id);
        HttpResponse<String> response = httpHandler.target(END_POINT).path("/" + id)
                .mediaType(MediaType.APPLICATION_JSON).DELETE().build();

        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
    }

    @Test
    void add_book() {
        Book book = FakerBooks.getBook(FakerBooks.generateRandomId());
        Gson gson = new Gson();
        String json = gson.toJson(book);
        System.out.println(json);

        HttpResponse<String> response = httpHandler.target(END_POINT).mediaType(MediaType.APPLICATION_JSON)
                .POST(book).build();
        assertThat(response.statusCode()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(response.body()).isNotEmpty();
    }

    @Test
    void update_book() {
        String id = FakerBooks.generateRandomId();
        Book book = FakerBooks.addFakeDataToDatabase(id);
        Book updateBook = new BookBuilder().setTitle(book.getTitle() +": updated")
                .setAuthors(book.getAuthors()).setDescription("Updated Description")
                .setId(id).setImagePath(book.getImagePath()).setCategory(book.getCategory()).createBook();

        HttpResponse<String> response = httpHandler.target(END_POINT).mediaType(MediaType.APPLICATION_JSON)
                .PUT(updateBook).build();
        Book responseBook = httpHandler.getResponse(response, Book.class);

        assertThat(response.statusCode()).isEqualTo(Status.OK.getStatusCode());
        assertThat(responseBook.getTitle()).isEqualTo(updateBook.getTitle());
    }

}
