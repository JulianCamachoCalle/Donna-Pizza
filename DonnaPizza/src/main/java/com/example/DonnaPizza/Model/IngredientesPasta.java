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
@Table(name = "ingredientes_pasta")
@IdClass(IngredientesPastaId.class)
public class IngredientesPasta {

    // Atributos
    @Id
    private Long id_pasta;

    @Id
    private Long id_ingrediente;

    private Double cantidad_necesaria;
}
