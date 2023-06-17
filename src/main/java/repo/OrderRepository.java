package repo;

import data.PostgresDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepository implements PostgresDB {

    @Override
    public Connection getConnection() {
        return PostgresDB.super.getConnection();
    }

    public ResultSet getOrderById(Integer id) {
        Connection connection = getConnection();
        try {
            PreparedStatement myStmt = connection.prepareStatement("SELECT * FROM orders o JOIN customers c ON c.id = o.customer_id WHERE o.id = ?");
            myStmt.setInt(1, id);
            return myStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }


    public ResultSet getOrders() {
        Connection connection = getConnection();
        try {
            PreparedStatement myStmt = connection.prepareStatement("SELECT * FROM orders o JOIN customers c ON c.id = o.customer_id");
            return myStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return null;
    }
}
