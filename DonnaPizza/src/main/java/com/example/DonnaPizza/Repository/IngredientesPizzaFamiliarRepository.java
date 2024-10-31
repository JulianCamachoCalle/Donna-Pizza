package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.IngredientesPizzaFamiliar;
import com.example.DonnaPizza.Model.IngredientesPizzaFamiliarId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientesPizzaFamiliarRepository extends JpaRepository<IngredientesPizzaFamiliar, Long> {

}
    