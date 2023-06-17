package service;

import com.google.gson.Gson;
import customAarrayList.CustomArrayList;
import dto.Dto;
import dto.IdDto;
import entity.Customer;
import entity.Product;
import repo.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService implements Dto {
    static CustomerRepository repo = new CustomerRepository();
    Gson gson = new Gson();

    public void doPostCostumers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            IdDto idDto = getIdDto(request);
            if (idDto != null && idDto.getType().equals("products")) {
                CustomArrayList<Product> list = getProducts(repo.getProductsByCustomerId(idDto.getId()));
                out.print(gson.toJson(list));
            } else if (idDto != null && idDto.getType().equals("customer")) {
                CustomArrayList<Customer> list = getCustomerList(repo.getCustomers(String.valueOf(idDto.getId())));
                out.print(gson.toJson(list.get(0)));
            } else {
                CustomArrayList<Customer> list = getCustomerList(repo.getCustomers("all"));
                out.print(gson.toJson(list));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetCostumers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if (id == null) {
            CustomArrayList<Customer> list = getCustomerList((repo.getCustomers("all")));
            out.print(gson.toJson(list));
        } else {
            CustomArrayList<Customer> list = getCustomerList(repo.getCustomers(id));
            out.print(gson.toJson(list.get(0)));
        }
        out.flush();
    }

    public CustomArrayList<Customer> getCustomerList(ResultSet rs) {
        CustomArrayList<Customer> customers = new CustomArrayList<>();
        try {
            while (rs.next()) {
                customers.add(new Customer(Integer.parseInt(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public CustomArrayList<Product> getProducts(ResultSet rs) throws SQLException {
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
