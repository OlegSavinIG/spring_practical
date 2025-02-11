package practice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.model.order.OrderRequest;
import practice.model.order.OrderResponse;
import practice.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Long id) throws JsonProcessingException {
        OrderResponse response = orderService.getOrderById(id);
        String jsonResponse = objectMapper.writeValueAsString(response);
        return ResponseEntity.ok(jsonResponse);

    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody String orderJson) throws JsonProcessingException {
        orderService.createOrder(objectMapper.readValue(orderJson, OrderRequest.class));
        return ResponseEntity.ok().build();
    }
}
