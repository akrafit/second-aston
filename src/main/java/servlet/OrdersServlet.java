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

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    Gson gson = new Gson();
    private static JDBCPostgresSQLService jdbc;


    public void init() {
        jdbc = new JDBCPostgresSQLService();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        StringBuilder jb = new StringBuilder();
        String line;
        IdDto idDto = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            idDto = gson.fromJson(String.valueOf(jb),IdDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(idDto == null){
            CustomArrayList<Order> list = jdbc.getOrders();
            out.print(gson.toJson(list));
        }else{
            CustomArrayList<Order> list = jdbc.getOrderById(String.valueOf(idDto.getId()));
            out.print(gson.toJson(list.get(0)));
        }
        out.close();
    }
}
