package practice.model.product;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductResponse toProductResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    public Product toProductEntity(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }

    public ProductRequest toProductRequest(Product product) {
        return modelMapper.map(product, ProductRequest.class);
    }
}

