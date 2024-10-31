package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pastas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastasRepository extends JpaRepository<Pastas, Long> {

    List<Pastas> finByIdPasta(Long id_pasta);
    // Método para buscar todas las pastas disponibles
    List<Pastas> findByDisponible(Boolean disponible);

    // Método para buscar una pasta por su nombre
    Optional<Pastas> findByNombre(String nombre);

    // Método para buscar todas las pastas con un precio menor que un valor específico
    List<Pastas> findByPrecioLessThan(Double precio);
}
