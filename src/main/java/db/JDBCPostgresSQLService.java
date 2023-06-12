package db;

import connectionPool.BasicConnectionPool;
import connectionPool.ConnectionPool;
import customAarrayList.CustomArrayList;
import entity.Customer;
import entity.Order;
import entity.Product;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@NoArgsConstructor
public class JDBCPostgresSQLService {
    static ConnectionPool connectionPool;
    private String url = "jdbc:postgresql://45.8.98.142:5432/aston";

    {
        try {
            connectionPool = BasicConnectionPool
                    .create(url, "postgres", "postgres");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JDBCPostgresSQLService(String url) {
        this.url = url;
    }

    public CustomArrayList<Customer> getCustomers(String id) {
        StringBuilder query = new StringBuilder("SELECT * FROM customers");
        if (!id.equals("all")) {
            query.append(" WHERE id = ").append(id);
        }
        try (ResultSet rs = connectionPool.getConnection().createStatement().executeQuery(query.toString())) {
            CustomArrayList<Customer> customers = new CustomArrayList<>();
            while (rs.next()) {
                customers.add(new Customer(Integer.parseInt(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")));
            }
            return customers;
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }

    public CustomArrayList<Order> getOrders() {
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM orders o JOIN customers c ON c.id = o.customer_id");
            return getOrders(rs);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }

    public CustomArrayList<Order> getOrderById(String id) {
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM orders o JOIN customers c ON c.id = o.customer_id WHERE o.id = " + id);
            return getOrders(rs);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }

    private CustomArrayList<Order> getOrders(ResultSet rs) throws SQLException {
        CustomArrayList<Order> orders = new CustomArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer(Integer.parseInt(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"));
            Order order = new Order(Integer.parseInt(rs.getString("id")), customer,
                    rs.getString("order_date"),
                    rs.getString("total_price"));
            orders.add(order);
        }
        return orders;
    }

    public CustomArrayList<Product> getProductsByCustomerId(String customerId) {
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("SELECT p.id, p.name, p.price FROM public.customers c \n" +
                            "JOIN orders o ON o.customer_id = c.id\n" +
                            "JOIN order_items oi ON oi.order_id = o.id\n" +
                            "JOIN products p ON p.id = oi.product_id\n" +
                            "WHERE c.id = " + customerId);
            return getProducts(rs);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }

    private CustomArrayList<Product> getProducts(ResultSet rs) throws SQLException {
        CustomArrayList<Product> orders = new CustomArrayList<>();
        while (rs.next()) {
            Product product = new Product(Integer.parseInt(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("price"));
            orders.add(product);
        }
        return orders;
    }
}
