package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pizzas;
import com.example.DonnaPizza.Model.PizzasFamiliares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzasFamiliaresRepository extends JpaRepository<PizzasFamiliares, Long> {

    // Buscar Segun Nombre
    Optional<PizzasFamiliares> findPizzaFamiliarByNombre(String nombre);

    // Verificar existencia
    boolean existsById_pizza_familiar(Long id_pizza_familiar);
    // Eliminar
    void deleteById_pizza_familiar(Long id_pizza_familiar);
}
