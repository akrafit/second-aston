package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order2Products {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;

}
