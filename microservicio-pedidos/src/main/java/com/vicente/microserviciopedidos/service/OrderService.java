package com.vicente.microserviciopedidos.service;

import com.vicente.microserviciopedidos.model.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order save(Order Order);
    void deleteById(Long id);
    HashSet<Order> findAllByClientId(Long clientId);
}
