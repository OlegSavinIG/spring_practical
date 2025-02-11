package practice.model.order;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import practice.model.customer.CustomerMapper;
import practice.model.product.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;
    private final CustomerMapper customerMapper;

    public OrderMapper(ModelMapper modelMapper, ProductMapper productMapper, CustomerMapper customerMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
        this.customerMapper = customerMapper;
    }

    public OrderResponse toOrderResponse(Order order) {
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setCustomer(customerMapper.toCustomerResponse(order.getCustomer()));
        orderResponse.setProducts(order.getProducts().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList()));
        return orderResponse;
    }

    public Order toOrderEntity(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        return order;
    }

    public OrderRequest toOrderRequest(Order order) {
        return modelMapper.map(order, OrderRequest.class);
    }
}

