package db;

import customAarrayList.CustomArrayList;
import entity.Customer;
import entity.Order;
import entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JDBCPostgresSQLServiceTest {


    JDBCPostgresSQLService jdbc = new JDBCPostgresSQLService("jdbc:postgresql://localhost:5432/aston-test");

    @Test
    void getCustomers() {
        CustomArrayList<Customer> customers = jdbc.getCustomers("1");
        assertNotNull(customers);
        assertEquals(1,customers.get(0).getId());

    }

    @Test
    void getOrders() {
        CustomArrayList<Order> orders = jdbc.getOrders();
        assertNotNull(orders);
        assertEquals(1,orders.get(0).getCustomer().getId());
    }

    @Test
    void getOrderById() {
        CustomArrayList<Order> orders = jdbc.getOrderById("1");
        assertNotNull(orders);
        assertEquals(1,orders.get(0).getCustomer().getId());
    }

    @Test
    void getProductsByCustomerId() {
        CustomArrayList<Product> products = jdbc.getProductsByCustomerId("1");
        assertNotNull(products);
        assertEquals("Widget",products.get(0).getName());
    }
}