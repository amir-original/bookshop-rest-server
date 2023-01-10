package com.ws.bookshoprestserver.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.bookshoprestserver.domain.Author;
import com.ws.bookshoprestserver.domain.Book;
import com.ws.bookshoprestserver.domain.BookBuilder;
import com.ws.bookshoprestserver.domain.BookCategory;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class BookshopDAOImpl implements BookshopDAO {

    private final Gson gson = new Gson();

    private AuthorDAO authorDAO;
    private Connection connection;

    public BookshopDAOImpl() {
        init();
    }

    @PostConstruct
    private void init() {
        MySQLDbConnection mySQLDbConnection = new MySQLDbConnection();
        connection = mySQLDbConnection.getConnection();
        authorDAO = new AuthorDAOImpl();
    }


    @Override
    public List<Book> getAll() {
        List<Book> books = new LinkedList<>();
        try {
            PreparedStatement statement = sql("select * from bookshop.books");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                books.add(getBookObject(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Book getBookObject(ResultSet result) throws SQLException {
        BookBuilder builder = new BookBuilder();
        Gson gson = new Gson();
        TypeToken<List<Author>> typeToken = new TypeToken<>() {
        };
        List<Author> authors = gson.fromJson(result.getString("authors"), typeToken.getType());
        BookCategory category = gson.fromJson(result.getString("category"), BookCategory.class);
        return builder.setId(result.getString("id"))
                .setTitle(result.getString("title"))
                .setDescription(result.getString("description"))
                .setLink(result.getString("link"))
                .setImagePath(result.getString("image_path")).setPrice(result.getFloat("price"))
                .setAuthors(authors)
                .setCategory(category).createBook();
    }

    @Override
    public Book getById(String id) {
        Book book = null;
        try {
            PreparedStatement statement = sql("select * from bookshop.books where id=?");
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                book = getBookObject(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void update(Book book) {
        try {
            PreparedStatement insert =
                    sql("update books set title=?,description=?,price=?," +
                            "link=?,category=?,authors=?,image_path=? where id=?");

            insert.setString(1, book.getTitle());
            insert.setString(2, book.getDescription());
            insert.setFloat(3, book.getPrice());
            insert.setString(4, book.getLink());
            insert.setString(5, gson.toJson(book.getCategory()));
            insert.setString(6, gson.toJson(book.getAuthors()));
            insert.setString(7, book.getImagePath());
            insert.setString(8, book.getId());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(Book book) {
        try {
            String sql = "insert into books values (?,?,?,?,?,?,?,?)";
            PreparedStatement insert = sql(sql);
            insert.setString(1, book.getId());
            insert.setString(2, book.getTitle());
            insert.setString(3, book.getDescription());
            insert.setFloat(4, book.getPrice());
            insert.setString(5, book.getLink());
            insert.setString(6, gson.toJson(book.getCategory()));
            insert.setString(7, gson.toJson(book.getAuthors()));
            insert.setString(8, book.getImagePath());
            authorDAO.addAuthors(book.getAuthors());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBooks(List<Book> books) {
        books.forEach(this::addBook);
    }

    @Override
    public Integer deleteBook(String id) {
        Integer result = null;
        try {
            PreparedStatement statement = sql("delete from bookshop.books where id=?");
            statement.setString(1, id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private PreparedStatement sql(String sql) throws SQLException {
        return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
    }
}
