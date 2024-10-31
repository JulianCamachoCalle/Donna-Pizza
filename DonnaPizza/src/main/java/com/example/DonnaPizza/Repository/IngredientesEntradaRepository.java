package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.IngredientesEntrada;
import com.example.DonnaPizza.Model.IngredientesEntradaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientesEntradaRepository extends JpaRepository<IngredientesEntrada,Long> {

    // Buscar por id_entrada
    List<IngredientesEntrada> findById_entrada(Long id_entrada);

    // Verificar si existe por id_ingrediente
    boolean existsById_ingrediente(Long idIngrediente);

    // Buscar por id_ingrediente
    List<IngredientesEntrada> findById_ingrediente(Long id_ingrediente);

    // Eliminar un ingrediente de entrada por su id_ingrediente
    void deleteById_ingrediente(Long id_ingrediente);
}