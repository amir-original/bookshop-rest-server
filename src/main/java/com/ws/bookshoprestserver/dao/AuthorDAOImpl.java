package com.ws.bookshoprestserver.dao;

import com.ws.bookshoprestserver.domain.Author;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Stateless
public class AuthorDAOImpl implements AuthorDAO {
    private static final int FAIL = 0;

    private Connection connection;

    public AuthorDAOImpl() {
        init();
    }

    @PostConstruct
    private void init() {
        MySQLDbConnection mySQLDbConnection = new MySQLDbConnection();
        connection = mySQLDbConnection.getConnection();
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new LinkedList<>();
        try {
            PreparedStatement statement = sql("select * from bookshop.authors");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                authors.add(getAuthor(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public Optional<Author> getById(int id) {
        Author author = null;
        try {
            PreparedStatement statement = sql("select * from bookshop.authors where id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                author = getAuthor(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Integer addAuthor(Author author) {
        Integer lastId = null;
        try {
            PreparedStatement statement = sql("insert into authors (first_name, last_name) VALUES (?,?)");
            fillAuthorInfo(author, statement);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                lastId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    @Override
    public int deleteAuthor(int id) {
        int result = FAIL;
        try {
            PreparedStatement statement = sql("delete from bookshop.authors where id=?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Author update(Author author) {
        Author updatedAuthor = null;
        try {
            PreparedStatement statement = sql("update authors set first_name=?,last_name=? where id=?");
            fillAuthorInfo(author, statement);
            statement.setInt(3, author.getId());
            int res = statement.executeUpdate();
            if (res == 1) updatedAuthor = author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedAuthor;
    }

    @Override
    public void addAuthors(List<Author> authors) {
        authors.forEach(this::addAuthor);
    }

    public Optional<Author> getLastAuthor() {
        Author author = null;
        try {
            PreparedStatement statement = sql("select * from bookshop.authors order by id desc limit 1");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                author = getAuthor(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(author);
    }

    private PreparedStatement sql(String sqlStatement) throws SQLException {
        return connection.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS);
    }

    private static void fillAuthorInfo(Author author, PreparedStatement statement) throws SQLException {
        statement.setString(1, author.getFirstName());
        statement.setString(2, author.getLastName());
    }

    private static Author getAuthor(ResultSet result) throws SQLException {
        return new Author(result.getInt("id"),
                result.getString("first_name"),
                result.getString("last_name"));
    }
}
