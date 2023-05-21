package com.vicente.microserviciopedidos.repository;

import com.vicente.microserviciopedidos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    HashSet<Order> findAllByClientId(Long clientId);
}
