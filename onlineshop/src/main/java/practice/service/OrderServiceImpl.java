package practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.exception.OrderNotFoundException;
import practice.model.order.Order;
import practice.model.order.OrderMapper;
import practice.model.order.OrderRequest;
import practice.model.order.OrderResponse;
import practice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("No order"));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void createOrder(OrderRequest order) {
        orderRepository.save(orderMapper.toOrderEntity(order));
    }
}
