package com.ws.bookshoprestserver.dao;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();
}
