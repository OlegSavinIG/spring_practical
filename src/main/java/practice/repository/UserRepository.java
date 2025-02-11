package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
