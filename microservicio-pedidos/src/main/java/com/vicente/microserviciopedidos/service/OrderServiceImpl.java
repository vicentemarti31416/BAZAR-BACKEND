package com.vicente.microserviciopedidos.service;

import com.vicente.microserviciopedidos.model.Order;
import com.vicente.microserviciopedidos.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order Order) {
        return orderRepository.save(Order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public HashSet<Order> findAllByClientId(Long clientId) {
        return orderRepository.findAllByClientId(clientId);
    }
}
