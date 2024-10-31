package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.IngredientesEntrada;
import com.example.DonnaPizza.Model.IngredientesEntradaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientesEntradaRepository extends JpaRepository<IngredientesEntrada,Long> {

}