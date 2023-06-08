package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private int id;
    private Customer customer;
    private String orderDate;
    private String totalPrice;
    private List<Order2Products> order2Products;

    public Order(int id, Customer customer, String orderDate, String totalPrice) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
