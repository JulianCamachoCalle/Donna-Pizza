package com.example.DonnaPizza.MVC.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    // Buscar Segun Email
    Optional<Cliente> findClienteByEmail(String email);

    // Buscar Segun Telefono
    Optional<Cliente> findClienteByTelefono(String telefono);
}
