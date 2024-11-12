package com.example.DonnaPizza.MVC.Promociones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//Desarrollo de anthony
@Repository
public interface PromocionesRepository extends JpaRepository<Promociones, Long>{

    // Buscar Segun Email
    Optional<Promociones> findPromocionesByNombre(String nombre);

}
