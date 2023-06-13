package servlet;

import db.JDBCPostgresSQLService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer")
public class CustomersServlet extends HttpServlet {

    private static JDBCPostgresSQLService service;
    public void init() {
        service = new JDBCPostgresSQLService();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.doGetCostumers(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.doPostCostumers(request, response);
    }
}
