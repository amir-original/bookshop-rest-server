package dao;

import com.ws.bookshoprestserver.dao.MySQLDbConnection;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.fail;

public class MySQLDbConnectionShould {
    private static final String CONNECTION_FAILURE_MESSAGE = "we can't connect to database";

    @Test
    void connect_to_database() {
        MySQLDbConnection mysql = new MySQLDbConnection();
        if (mysql.getConnection() == null)
            fail(CONNECTION_FAILURE_MESSAGE);

    }

}
