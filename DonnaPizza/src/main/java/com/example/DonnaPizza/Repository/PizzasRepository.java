package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pizzas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzasRepository extends JpaRepository<Pizzas, Long> {

    // Buscar Segun Nombre
    Optional<Pizzas> findPizzaByNombre(String nombre);
}
