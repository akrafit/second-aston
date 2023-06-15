package servlet;

import service.SQLService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {
    private static SQLService service;
    public void init() {
        service = new SQLService();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.doGetCostumers(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.doPostCostumers(request, response);
    }
}
