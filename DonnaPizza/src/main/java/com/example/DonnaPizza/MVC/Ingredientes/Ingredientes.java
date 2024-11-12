package com.example.DonnaPizza.MVC.Ingredientes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "ingredientes")
    public class Ingredientes {

        // Atributos
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_ingrediente;

        @Column(unique = true)
        private String nombre;

        private Double cantidad_disponible;
    }
