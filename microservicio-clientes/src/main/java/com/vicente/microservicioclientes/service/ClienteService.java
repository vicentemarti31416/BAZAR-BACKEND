package com.vicente.microservicioclientes.service;

import com.vicente.microservicioclientes.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
