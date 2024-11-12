package com.example.DonnaPizza.MVC.PizzasFamiliares;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzasFamiliaresRepository extends JpaRepository<PizzasFamiliares, Long> {

    // Buscar Segun Nombre
    Optional<PizzasFamiliares> findPizzaFamiliarByNombre(String nombre);

}
