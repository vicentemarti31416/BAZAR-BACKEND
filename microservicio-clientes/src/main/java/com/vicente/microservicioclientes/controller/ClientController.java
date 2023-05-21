package com.vicente.microservicioclientes.controller;

import com.vicente.microservicioclientes.model.Client;
import com.vicente.microservicioclientes.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClienteService clienteService;

    public ClientController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Client> optionalClient = clienteService.findById(id);
        if (optionalClient.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalClient.get());
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Validated @RequestBody Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Validated @RequestBody Client client, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        Optional<Client> optionalClient = clienteService.findById(id);
        if (optionalClient.isEmpty()) return ResponseEntity.notFound().build();
        Client clientDB = optionalClient.get();
        clientDB.setName(client.getName());
        clientDB.setPhone(client.getPhone());
        clientDB.setEmail(client.getEmail());
        clientDB.setCountry(client.getCountry());
        clientDB.setCity(client.getCity());
        clientDB.setPostalCode(client.getPostalCode());
        clientDB.setStreet(client.getStreet());
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clientDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Client> optionalClient = clienteService.findById(id);
        if (optionalClient.isEmpty()) return ResponseEntity.notFound().build();
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }
}
