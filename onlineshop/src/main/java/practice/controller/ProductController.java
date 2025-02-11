package practice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.model.product.ProductRequest;
import practice.model.product.ProductResponse;
import practice.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<String>> getAllProducts() {
        List<ProductResponse> responses = productService.findAll();
        List<String> result = responses.stream()
                .map(value -> {
                    try {
                        return objectMapper.writeValueAsString(value);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) throws JsonProcessingException {
        ProductResponse response = productService.findById(id);
        String jsonResponse = objectMapper.writeValueAsString(response);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody String product) throws JsonProcessingException {
        productService.save(objectMapper.readValue(product, ProductRequest.class));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest product) {
        productService.update(id, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
