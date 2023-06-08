package connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws SQLException;
    String getUrl();
    String getUser();
    String getPassword();
}
