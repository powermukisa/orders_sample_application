package com.canals.orders.infrastructure.adapter.persistence.jpa;

import com.canals.orders.infrastructure.adapter.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for Customer
 */
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}

