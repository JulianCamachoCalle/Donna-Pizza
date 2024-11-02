package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Promociones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocionesRepository extends JpaRepository<Promociones, Long>{

    // Buscar Segun Email
    Optional<Promociones> findPromocionesByNombre(String nombre);

}
