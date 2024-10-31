package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Ingredientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientesRepository extends JpaRepository<Ingredientes, Long> {

    // Buscar Segun Nombre
    Optional<Ingredientes> findIngredientesByNombre(String nombre);

}
