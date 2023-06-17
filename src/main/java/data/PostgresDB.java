package data;

import connectionPool.BasicConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public interface PostgresDB {
    BasicConnectionPool connectionPool = new BasicConnectionPool();
    default Connection getConnection(){
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
