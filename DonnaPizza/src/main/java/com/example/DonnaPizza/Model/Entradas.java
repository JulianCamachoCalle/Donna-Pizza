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
@Table(name = "entradas")
public class Entradas {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_entrada;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Boolean disponible;
}
