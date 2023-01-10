package rest;

import com.ws.bookshoprestserver.dao.BookshopDAO;
import com.ws.bookshoprestserver.dao.BookshopDAOImpl;
import com.ws.bookshoprestserver.domain.Author;
import com.ws.bookshoprestserver.domain.Book;
import com.ws.bookshoprestserver.domain.BookBuilder;
import com.ws.bookshoprestserver.domain.BookCategory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FakerBooks {

    private static final BookshopDAO dao = new BookshopDAOImpl();

    static Book addFakeDataToDatabase(String id) {
        Book book = getBook(id);
        dao.addBook(book);
        return book;
    }

    public static Book getBook(String id) {
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

    public static String generateRandomId() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }

    public static Book addFakeBookToDb(){
        return addFakeDataToDatabase(generateRandomId());
    }
}
