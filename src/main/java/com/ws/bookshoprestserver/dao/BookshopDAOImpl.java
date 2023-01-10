package com.ws.bookshoprestserver.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Author;
import domain.Book;
import domain.BookBuilder;
import domain.BookCategory;
import jakarta.ejb.Stateless;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Stateless
public class BookshopDAOImpl implements BookshopDAO {
    private static final String HOST = "jdbc:mysql://localhost:3306/bookshop";
    private static final String USER = "amir";
    private static final String PASS = "@wsrmp1378";
    @Override
    public List<Book> getAll() {
        List<Book> books = new LinkedList<>();
        try(Connection connection = DriverManager.getConnection(HOST, USER, PASS)){
            PreparedStatement statement = connection.prepareStatement("select * from bookshop.books");
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
        TypeToken<List<Author>> typeToken = new TypeToken<>() {};
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
        try(Connection connection = DriverManager.getConnection(HOST, USER, PASS)){
            PreparedStatement statement = connection.prepareStatement("select * from bookshop.books where id=?");
            statement.setString(1,id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
              book= getBookObject(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void update(Book book) {
        try (Connection connection = DriverManager.getConnection(HOST, USER, PASS)) {
            String sql = "update books set title=?,description=?,price=?,link=?,category=?,authors=?,image_path=? where id=?";
            PreparedStatement insert = connection.prepareStatement(sql);
            insert.setString(1, book.getTitle());
            insert.setString(2, book.getDescription());
            insert.setFloat(3, book.getPrice());
            insert.setString(4, book.getLink());
            insert.setString(5, new Gson().toJson(book.getCategory()));
            insert.setString(6, new Gson().toJson(book.getAuthors()));
            insert.setString(7, book.getImagePath());
            insert.setString(8, book.getId());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(Book book) {
        try (Connection connection = DriverManager.getConnection(HOST, USER, PASS)) {
            String sql = "insert into books (id, title, description, price, link, category, authors,image_path) values (?,?,?,?,?,?,?,?)";
            PreparedStatement insert = connection.prepareStatement(sql);
            insert.setString(1, book.getId());
            insert.setString(2, book.getTitle());
            insert.setString(3, book.getDescription());
            insert.setFloat(4, book.getPrice());
            insert.setString(5, book.getLink());
            insert.setString(6, new Gson().toJson(book.getCategory()));
            insert.setString(7, new Gson().toJson(book.getAuthors()));
            insert.setString(8, book.getImagePath());
            insert.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addBooks(List<Book> books) {

    }

    @Override
    public Integer deleteBook(String id) {
        Integer result = null;
        try(Connection connection = DriverManager.getConnection(HOST, USER, PASS)){
            PreparedStatement statement = connection.prepareStatement("delete from bookshop.books where id=?");
            statement.setString(1,id);
           result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
