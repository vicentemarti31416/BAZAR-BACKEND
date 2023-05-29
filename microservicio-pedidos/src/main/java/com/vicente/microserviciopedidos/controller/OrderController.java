package com.vicente.microserviciopedidos.controller;

import com.vicente.microserviciopedidos.model.Order;
import com.vicente.microserviciopedidos.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if (optionalOrder.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalOrder.get());
    }

    @GetMapping("/clientId/{clientId}")
    public ResponseEntity<?> getAllByClientId(@PathVariable Long clientId) {
        HashSet<Order> orders = orderService.findAllByClientId(clientId);
        if (orders == null) {
            return ResponseEntity.notFound().build();
        } else if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Validated @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Validated @RequestBody Order order, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        Optional<Order> optionalOrder = orderService.findById(id);
        if (optionalOrder.isEmpty()) return ResponseEntity.notFound().build();
        Order orderDB = optionalOrder.get();
        orderDB.getItems().clear();
        orderDB.getItems().addAll(order.getItems());
        orderDB.setClientId(order.getClientId());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if (optionalOrder.isEmpty()) return ResponseEntity.notFound().build();
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }
}
