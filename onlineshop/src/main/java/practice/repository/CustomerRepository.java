package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.model.customer.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
