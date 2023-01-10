package com.ws.bookshoprestserver.dao;

import com.ws.bookshoprestserver.helper.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDbConnection implements DbConnection {

    private String host;
    private String username;
    private String password;

    public MySQLDbConnection() {
        init();
    }

    private void init() {
        PropertiesReader propertiesReader = new PropertiesReader("db-config");
        host = propertiesReader.getProperty("host");
        username = propertiesReader.getProperty("username");
        password = propertiesReader.getProperty("password");
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(host, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
