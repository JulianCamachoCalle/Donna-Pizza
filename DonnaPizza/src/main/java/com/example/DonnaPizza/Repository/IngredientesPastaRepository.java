package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.IngredientesPasta;
import com.example.DonnaPizza.Model.IngredientesPastaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientesPastaRepository extends JpaRepository<IngredientesPasta, Long> {

    // Buscar por id_pasta
    List<IngredientesPasta> findById_pasta(Long id_pasta);

    // Buscar por id_ingrediente
    List<IngredientesPasta> findById_ingrediente(Long id_ingrediente);

    // Verificar existencia por id_pedido
    boolean existsById_pasta(Long id_pasta);
    // Eliminar por id_pedido
    void deleteById_pasta(Long id_pasta);
}
