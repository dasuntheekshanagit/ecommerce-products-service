package com.ecommerce.repository.users;

import com.ecommerce.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user with addresses
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.id = :userId")
    Optional<User> findByIdWithAddresses(@Param("userId") Long userId);

    // Check if user exists by email
    boolean existsByEmail(String email);

    // Check if user exists by email excluding current user
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :userId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("userId") Long userId);
}

