package practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.exception.ProductNotFoundException;
import practice.model.product.Product;
import practice.model.product.ProductMapper;
import practice.model.product.ProductRequest;
import practice.model.product.ProductResponse;
import practice.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("No product"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public void save(ProductRequest product) {
        productRepository.save(productMapper.toProductEntity(product));
    }

    @Override
    public void update(Long id, ProductRequest product) {
        if (productRepository.existsById(id)) {
            Product product1 = productRepository.findById(id).orElseThrow(
                    () -> new ProductNotFoundException("No product"));

            productRepository.save(updateProduct(product1, product));
        }
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    private Product updateProduct(Product product1, ProductRequest product) {
        return product1;
    }
}
