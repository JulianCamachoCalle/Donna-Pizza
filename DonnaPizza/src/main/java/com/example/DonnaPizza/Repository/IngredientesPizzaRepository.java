package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.IngredientesPizza;
import com.example.DonnaPizza.Model.IngredientesPizzaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientesPizzaRepository extends JpaRepository<IngredientesPizza, Long> {

    // Buscar por id_pizza
    List<IngredientesPizza> findById_pizza(Long id_pizza);

    // Buscar por id_ingrediente
    List<IngredientesPizza> findById_ingrediente(Long id_ingrediente);
}
