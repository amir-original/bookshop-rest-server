package dao;

import com.ws.bookshoprestserver.dao.AuthorDAOImpl;
import com.ws.bookshoprestserver.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorDAOShould {

    private AuthorDAOImpl authorDAO;

    @BeforeEach
    void setUp() {
        authorDAO = new AuthorDAOImpl();
    }

    @Test
    void get_all_authors() {
        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        List<Author> authors = authorDAO.getAll();

        assertThat(authors).isNotEmpty();
    }

    @Test
    void get_author_by_id() {
        Author author = addAuthor().get();
        int id = author.getId();
        Optional<Author> res = authorDAO.getById(id);
        assertThat(res.isEmpty()).isFalse();
        authorDAO.deleteAuthor(id);
    }

    @Test
    void delete_author() {
        Optional<Author> author = addAuthor();
        int isDeletedUser = authorDAO.deleteAuthor(author.get().getId());
        assertThat(isDeletedUser).isEqualTo(1);
    }

    @Test
    void add_new_author() {
        Author author = addAuthor().get();
        Assertions.assertThat(author.getFirstName()).isNotEmpty();
        authorDAO.deleteAuthor(author.getId());
    }

    @Test
    void update_author() {
        Author author = addAuthor().get();
        String updatedFirstName = author.getFirstName() + ": updated";
        Author update = authorDAO.update(new Author(author.getId(), updatedFirstName, author.getLastName()));

        Assertions.assertThat(update.getFirstName()).isEqualTo(updatedFirstName);

        authorDAO.deleteAuthor(author.getId());
    }

    private Optional<Author> addAuthor() {
        Random random = new Random();
        int i = random.nextInt(100);
        Author author = new Author("name "+i, "lastname "+i);
        Integer lastId = authorDAO.addAuthor(author);
        return authorDAO.getById(lastId);
    }


}
