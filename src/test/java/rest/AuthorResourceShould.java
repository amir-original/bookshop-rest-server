package rest;

import com.ws.bookshoprestserver.dao.AuthorDAO;
import com.ws.bookshoprestserver.dao.AuthorDAOImpl;
import com.ws.bookshoprestserver.helper.HttpRequestHandler;
import com.ws.bookshoprestserver.domain.Author;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorResourceShould {

    private static final String BASE_TARGET = "http://localhost:8080/bookshop-rest-server-1.0-SNAPSHOT/";
    private static final String END_POINT = BASE_TARGET + "api/authors";
    private HttpRequestHandler handler;

    @BeforeEach
    void setUp() {
        handler = new HttpRequestHandler();
    }

    @Test
    void get_all_authors() {
        HttpRequestHandler handler = new HttpRequestHandler();
        HttpResponse<String> response = handler.target(END_POINT).mediaType(MediaType.APPLICATION_JSON).GET().build();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.body()).isNotEmpty();
    }

    @Test
    void get_author_by_id() {
        int id = 1;
        HttpResponse<String> response = handler.target(END_POINT)
                .path("/"+id)
                .mediaType(MediaType.APPLICATION_JSON).GET().build();
        //Author author = handler.getResponse(response, Author.class);
        //TODO: create author here then check values too
        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.body()).isNotEmpty();
    }

    @Test
    void delete_author() {
        int id = 8;
        HttpResponse<String> respone = addAuthor(new Author("name1", "name2"));
        HttpResponse<String> response = handler.target(END_POINT)
                .path("/"+id)
                .mediaType(MediaType.APPLICATION_JSON).DELETE().build();

        assertThat(response.statusCode()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    void add_new_author() {
        Author author = new Author("Robbert","C Martin");
        HttpResponse<String> response = addAuthor(author);
        Author authorResponse = handler.getResponse(response, Author.class);

        assertThat(response.statusCode()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(authorResponse.getFirstName()).isEqualTo(author.getFirstName());
    }

    @Test
    void update_author() {
        Author author = new Author(9,"Updated: Robbert","C Martin");
        HttpResponse<String> response = handler
                .target(END_POINT)
                .mediaType(MediaType.APPLICATION_JSON)
                .PUT(author).build();
        Author authorResponse = handler.getResponse(response, Author.class);

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(authorResponse.getFirstName()).isEqualTo(author.getFirstName());
    }

    private HttpResponse<String> addAuthor(Author author) {
        return handler
                .target(END_POINT)
                .mediaType(MediaType.APPLICATION_JSON)
                .POST(author)
                .build();
    }

    private Optional<Author> addAuthor() {
        Random random = new Random();
        int i = random.nextInt(100);
        Author author = new Author("name "+i, "lastname "+i);
        AuthorDAO authorDAO = new AuthorDAOImpl();
        Integer lastId = authorDAO.addAuthor(author);
        return authorDAO.getById(lastId);
    }
}
