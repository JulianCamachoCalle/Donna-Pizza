package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.IngredientesPizzaFamiliar;
import com.example.DonnaPizza.Model.IngredientesPizzaFamiliarId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientesPizzaFamiliarRepository extends JpaRepository<IngredientesPizzaFamiliar, Long> {

    // Buscar por id_pizza_familiar
    List<IngredientesPizzaFamiliar> findById_pizza_familiar(Long id_pizza_familiar);

    // Buscar por id_ingrediente
    List<IngredientesPizzaFamiliar> findById_ingrediente(Long id_ingrediente);

    // Verificar existencia por id_pedido
    boolean existsById_pizza_familiar(Long id_pizza_familiar);
    // Eliminar por id_pedido
    void deleteById_pizza_familiar(Long id_pizza_familiar);
}
    