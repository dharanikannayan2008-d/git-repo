package com.foodsharing.repository;

import com.foodsharing.entity.User;
import com.foodsharing.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * User Repository - Data Access Layer for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    List<User> findByRole(UserRole role);
    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findByIsActive(Boolean isActive);
}
