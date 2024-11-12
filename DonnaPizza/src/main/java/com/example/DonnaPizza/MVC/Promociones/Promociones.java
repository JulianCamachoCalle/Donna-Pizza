package com.example.DonnaPizza.MVC.Promociones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//Desarrollo de anthony
// Tabla
@Table(name = "promociones")
public class Promociones {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_promocion;

    private String nombre;

    private String descripcion;

    private Double descuento;

    private String requisitos;

    private Boolean activo;
}
