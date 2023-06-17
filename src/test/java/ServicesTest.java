import customAarrayList.CustomArrayList;
import entity.Customer;
import entity.Order;
import entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repo.CustomerRepository;
import service.CustomerService;
import service.OrderService;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class ServicesTest {
    private static final String DATABASE_NAME = "test";
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("data.sql")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);

    @BeforeAll
    static void start() {
        postgreSQLContainer.start();
        System.setProperty("url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("user", postgreSQLContainer.getUsername());
        System.setProperty("password", postgreSQLContainer.getPassword());
    }
    CustomerRepository customerRepository = new CustomerRepository();
    CustomerService service = new CustomerService();
    OrderService orderService = new OrderService();

    @Test
    void getCustomersTest() {
        ResultSet rs = customerRepository.getCustomers("1");
        CustomArrayList<Customer> customers = service.getCustomerList(rs);
        assertEquals(1, customers.get(0).getId());
    }
    @Test
    void getProductsByCustomerIdTest() throws SQLException {
        ResultSet rs = customerRepository.getProductsByCustomerId(1);
        CustomArrayList<Product> products = service.getProducts(rs);
        assertEquals("Widget", products.get(0).getName());
    }
    @Test
    void getOrderByIdTest() throws SQLException {
        CustomArrayList<Order> orders = orderService.getOrderById(1);
        assertEquals(1,orders.get(0).getCustomer().getId());
    }
    @Test
    void getOrdersTest() throws SQLException {
        CustomArrayList<Order> orders = orderService.getOrders();
        assertEquals(1,orders.get(0).getCustomer().getId());
    }
}