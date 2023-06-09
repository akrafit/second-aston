package servlet;

import com.google.gson.Gson;
import customAarrayList.CustomArrayList;
import db.JDBCPostgresSQLService;
import dto.IdDto;
import entity.Customer;
import entity.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {
    Gson gson = new Gson();
    private static JDBCPostgresSQLService jdbc;


    public void init() {
        jdbc = new JDBCPostgresSQLService();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if(id == null){
            CustomArrayList<Customer> list = jdbc.getCustomers("all");
            out.print(gson.toJson(list));
        }else{
            CustomArrayList<Customer> list = jdbc.getCustomers(id);
            out.print(gson.toJson(list.get(0)));
        }
        out.flush();
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
        if(idDto != null && idDto.getType().equals("products")){
            CustomArrayList<Product> list = jdbc.getProductsByCustomerId(String.valueOf(idDto.getId()));
            out.print(gson.toJson(list));
        }else if (idDto != null && idDto.getType().equals("customer")){
            CustomArrayList<Customer> list = jdbc.getCustomers(String.valueOf(idDto.getId()));
            out.print(gson.toJson(list.get(0)));
        }else {
            CustomArrayList<Customer> list = jdbc.getCustomers("all");
            out.print(gson.toJson(list));
        }
        out.close();
    }


}
