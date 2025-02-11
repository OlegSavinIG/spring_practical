package practice.service;

import practice.model.order.OrderRequest;
import practice.model.order.OrderResponse;

public interface OrderService {
    OrderResponse getOrderById(Long id);

    void createOrder(OrderRequest order);
}
