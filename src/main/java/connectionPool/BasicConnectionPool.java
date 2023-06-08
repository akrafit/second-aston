package connectionPool;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

@Getter
public class BasicConnectionPool implements ConnectionPool {
    private static final int MAX_TIMEOUT = 10000;
    private static final int INITIAL_POOL_SIZE = 10;
    private final String url;
    private final String user;
    private final String password;
    private final Queue<Connection> connectionPool;
    public BasicConnectionPool(String url, String user, String password, Queue<Connection> pool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = pool;
    }

    public static BasicConnectionPool create(
            String url, String user,
            String password) throws SQLException {

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
        return new BasicConnectionPool(url, user, password, pool);
    }
     @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            connectionPool.add(createConnection(url, user, password));
        }
        Connection connection = connectionPool.remove();

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, user, password);
        }

         connectionPool.offer(connection);
        return connection;
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
