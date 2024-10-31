package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pastas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastasRepository extends JpaRepository<Pastas, Long> {

    // Metodo para buscar una pasta por su nombre
    Optional<Pastas> findByNombre(String nombre);

}
