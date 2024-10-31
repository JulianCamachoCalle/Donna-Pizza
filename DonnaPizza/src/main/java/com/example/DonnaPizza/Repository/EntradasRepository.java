package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Entradas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntradasRepository extends JpaRepository<Entradas, Long>{

    // Buscar entradas disponibles (disponible = true)
    List<Entradas> findByDisponibleTrue();

    // Buscar entradas por nombre
    List<Entradas> findByNombre(String nombre);
}
