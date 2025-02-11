package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.model.product.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
