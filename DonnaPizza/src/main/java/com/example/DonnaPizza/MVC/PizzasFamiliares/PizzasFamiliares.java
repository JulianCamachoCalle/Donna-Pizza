package com.example.DonnaPizza.MVC.PizzasFamiliares;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "pizzas_familiares")
public class PizzasFamiliares {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pizza_familiar;

    @Column(unique = true)
    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer disponible;
}
