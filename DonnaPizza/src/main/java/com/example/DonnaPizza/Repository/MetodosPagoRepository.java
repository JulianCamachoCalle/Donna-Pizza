package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.MetodosPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetodosPagoRepository extends JpaRepository<MetodosPago, Long> {
    Optional<MetodosPago> findByNombre(String nombre);
}
