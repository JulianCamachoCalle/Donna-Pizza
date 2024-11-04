package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.MetodosPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetodosPagoRepository extends JpaRepository<MetodosPago, Long> {

    // Buscar Segun Nombre
    Optional<MetodosPago> findByNombre(String nombre);
}
