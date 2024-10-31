package com.example.DonnaPizza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "ingredientes_pizza_familiar")
@IdClass(IngredientesPizzaFamiliarId.class)
public class IngredientesPizzaFamiliar {

    // Atributos
    @Id
    private Long id_pizza_familiar;

    @Id
    private Long id_ingrediente;

    private Double cantidad_necesaria;
}
