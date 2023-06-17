package connectionPool;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

@NoArgsConstructor
public class BasicConnectionPool implements ConnectionPool {
    private static final int MAX_TIMEOUT = 10000;
    private static final int INITIAL_POOL_SIZE = 10;
    public String url;
    public String user;
    public String password;
    private Queue<Connection> connectionPool;

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool == null) {
            if (url == null) {
                getAppProperties();
            }
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
                e.printStackTrace();
            }
            Queue<Connection> pool = new LinkedList<>();
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                pool.offer(createConnection(url, user, password));
            }
            connectionPool = pool;
        }
        Connection connection = connectionPool.remove();

        if (!connection.isValid(MAX_TIMEOUT)) {
            connection = createConnection(url, user, password);
        }

        connectionPool.offer(connection);
        return connection;
    }

    private void getAppProperties() {
        Properties properties = new Properties();
        if (System.getProperty("url") == null) {
            try {
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
        } else {
            this.url = System.getProperty("url");
            this.user = System.getProperty("user");
            this.password = System.getProperty("password");
        }
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size();
    }

    public void shutdown() throws SQLException {
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }
}
