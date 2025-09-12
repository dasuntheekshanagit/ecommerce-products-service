package com.ecommerce.repository.users;

import com.ecommerce.entity.users.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Find all addresses for a user
    List<Address> findByUserId(Long userId);

    // Find address by ID and user ID
    Optional<Address> findByIdAndUserId(Long id, Long userId);

    // Delete all addresses for a user
    void deleteByUserId(Long userId);

    // Count addresses for a user
    long countByUserId(Long userId);
}

