package service;

import com.google.gson.Gson;
import customAarrayList.CustomArrayList;
import dto.Dto;
import dto.IdDto;
import entity.Customer;
import entity.Order;
import repo.OrderRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderService implements Dto {
    Gson gson = new Gson();
    static OrderRepository repo = new OrderRepository();

    public CustomArrayList<Order> getOrderById(Integer id) throws SQLException {
        return getOrdersList(repo.getOrderById(id));
    }

    public CustomArrayList<Order> getOrders() throws SQLException {
        return getOrdersList(repo.getOrders());
    }

    private CustomArrayList<Order> getOrdersList(ResultSet rs) throws SQLException {
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

    public void dopPostOrders(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            IdDto idDto = getIdDto(request);
            CustomArrayList<Order> list;
            if (idDto == null) {
                list = getOrders();
                out.print(gson.toJson(list));
            } else {
                list = getOrderById(idDto.getId());
                out.print(gson.toJson(list.get(0)));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
