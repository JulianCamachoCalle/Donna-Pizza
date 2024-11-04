package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {

    // Buscar Segun fecha
    Optional<Pagos> findByNombre(String fecha);
}
