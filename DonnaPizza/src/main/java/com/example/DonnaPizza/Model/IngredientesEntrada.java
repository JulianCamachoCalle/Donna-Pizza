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
@Table(name = "ingredientes_entrada")
@IdClass(IngredientesEntradaId.class)
public class  IngredientesEntrada {

    // Atributos
    @Id
    private Long id_entrada;

    @Id
    private Long id_ingrediente;

    private Double cantidad_necesaria;
}
