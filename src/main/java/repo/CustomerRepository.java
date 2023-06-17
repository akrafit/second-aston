package repo;

import data.PostgresDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository implements PostgresDB {

    @Override
    public Connection getConnection() {
        return PostgresDB.super.getConnection();
    }

    public ResultSet getCustomers(String id) {
        Connection connection = getConnection();
        try {
            PreparedStatement myStmt;
            if (!id.equals("all")) {
                myStmt = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
                myStmt.setInt(1, Integer.parseInt(id));
            } else {
                myStmt = connection.prepareStatement("SELECT * FROM customers");
            }
            return myStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getProductsByCustomerId(Integer customerId) {
        Connection connection = getConnection();

        try {
            PreparedStatement myStmt = connection.prepareStatement("SELECT p.id, p.name, p.price FROM public.customers c \n" +
                    "JOIN orders o ON o.customer_id = c.id\n" +
                    "JOIN order_items oi ON oi.order_id = o.id\n" +
                    "JOIN products p ON p.id = oi.product_id\n" +
                    "WHERE c.id = ?");
            myStmt.setInt(1, customerId);
            return myStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }
}
