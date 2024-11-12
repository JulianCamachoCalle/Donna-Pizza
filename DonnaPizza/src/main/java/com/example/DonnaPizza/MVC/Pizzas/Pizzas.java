package com.example.DonnaPizza.MVC.Pizzas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "pizzas")
public class Pizzas {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pizza;

    @Column(unique = true)
    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer disponible;
}
