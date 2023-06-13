package db;

import com.google.gson.Gson;
import connectionPool.BasicConnectionPool;
import connectionPool.ConnectionPool;
import customAarrayList.CustomArrayList;
import dto.IdDto;
import entity.Customer;
import entity.Order;
import entity.Product;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@NoArgsConstructor
public class JDBCPostgresSQLService {
    static ConnectionPool connectionPool;

    Gson gson = new Gson();
    private String url = "jdbc:postgresql://localhost:5432/aston-test";

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

    public void doGetCostumers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if(id == null){
            CustomArrayList<Customer> list = getCustomers("all");
            out.print(gson.toJson(list));
        }else{
            CustomArrayList<Customer> list = getCustomers(id);
            out.print(gson.toJson(list.get(0)));
        }
        out.flush();
    }

    public void doPostCostumers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        StringBuilder jb = new StringBuilder();
        String line;
        IdDto idDto = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            idDto = gson.fromJson(String.valueOf(jb),IdDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(idDto != null && idDto.getType().equals("products")){
            CustomArrayList<Product> list = getProductsByCustomerId(String.valueOf(idDto.getId()));
            out.print(gson.toJson(list));
        }else if (idDto != null && idDto.getType().equals("customer")){
            CustomArrayList<Customer> list = getCustomers(String.valueOf(idDto.getId()));
            out.print(gson.toJson(list.get(0)));
        }else {
            CustomArrayList<Customer> list = getCustomers("all");
            out.print(gson.toJson(list));
        }
        out.close();
    }

    public void dopPostOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        StringBuilder jb = new StringBuilder();
        String line;
        IdDto idDto = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            idDto = gson.fromJson(String.valueOf(jb),IdDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(idDto == null){
            CustomArrayList<Order> list = getOrders();
            out.print(gson.toJson(list));
        }else{
            CustomArrayList<Order> list = getOrderById(String.valueOf(idDto.getId()));
            out.print(gson.toJson(list.get(0)));
        }
        out.close();
    }
}
