package practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import practice.model.order.OrderRequest;
import practice.model.order.OrderResponse;
import practice.service.OrderService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetOrderById() throws Exception {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(1L);
        orderResponse.setTotalPrice(200.0);

        when(orderService.getOrderById(1L)).thenReturn(orderResponse);

        String jsonResponse = objectMapper.writeValueAsString(orderResponse);

        mockMvc.perform(get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }
}
