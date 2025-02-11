package practice.service;

import practice.model.product.ProductRequest;
import practice.model.product.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    void save(ProductRequest product);

    void update(Long id, ProductRequest product);

    void deleteById(Long id);
}
