package servlet;

import com.google.gson.Gson;
import customAarrayList.CustomArrayList;
import db.JDBCPostgresSQLService;
import dto.IdDto;
import entity.Order;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private static JDBCPostgresSQLService service;
    public void init() {
        service = new JDBCPostgresSQLService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        service.dopPostOrders(req,resp);
    }
}
