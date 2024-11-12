package com.example.DonnaPizza.MVC.MetodosPago;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetodosPagoRepository extends JpaRepository<MetodosPago, Long> {
    Optional<MetodosPago> findByNombre(String nombre);
}
