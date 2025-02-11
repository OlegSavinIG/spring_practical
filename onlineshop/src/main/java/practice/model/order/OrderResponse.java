package practice.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.model.customer.CustomerResponse;
import practice.model.product.ProductResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private CustomerResponse customer;
    private List<ProductResponse> products;
    private String orderDate;
    private String shippingAddress;
    private double totalPrice;
    private String orderStatus;
}

