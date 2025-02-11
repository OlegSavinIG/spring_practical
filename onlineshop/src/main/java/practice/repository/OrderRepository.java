package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.model.order.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
