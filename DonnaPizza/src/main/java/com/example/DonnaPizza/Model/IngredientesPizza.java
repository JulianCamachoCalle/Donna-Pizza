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
@Table(name = "ingredientes_pizza")
@IdClass(IngredientesPizzaId.class)
public class IngredientesPizza {

    // Atributos
    @Id
    private Long id_pizza;

    @Id
    private Long id_ingrediente;

    private Double cantidad_necesaria;
}
