package practice.controller.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import practice.model.product.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testJsonToProduct() throws Exception {
        String json = "{ \"name\": \"Product\", \"description\": \"Product description\", \"price\": 100.0, \"quantityInStock\": 50 }";

        Product product = objectMapper.readValue(json, Product.class);

        assertNotNull(product);
        assertEquals("Product", product.getName());
        assertEquals(100.0, product.getPrice());
    }

    @Test
    public void testProductToJson() throws Exception {
        Product product = new Product();
        product.setName("New Product");
        product.setDescription("This is a new product");
        product.setPrice(200.0);
        product.setQuantityInStock(40);

        String json = objectMapper.writeValueAsString(product);

        assertTrue(json.contains("\"name\":\"New Product\""));
        assertTrue(json.contains("\"price\":200.0"));
    }
}

