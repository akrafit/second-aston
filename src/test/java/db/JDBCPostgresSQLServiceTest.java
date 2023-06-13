package db;

import customAarrayList.CustomArrayList;
import entity.Customer;
import entity.Order;
import entity.Product;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
class JDBCPostgresSQLServiceTest {
    private static final String DATABASE_NAME = "aston-test";
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("data.sql")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);
    JDBCPostgresSQLService jdbc = new JDBCPostgresSQLService("jdbc:postgresql://"+postgreSQLContainer.getHost()+"/"+postgreSQLContainer.getDatabaseName());
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