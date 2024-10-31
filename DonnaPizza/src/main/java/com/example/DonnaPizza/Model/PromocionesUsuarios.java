package com.example.DonnaPizza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "promociones_usuarios")
public class PromocionesUsuarios {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_promocion_usuario;

    private Long id_usuario;

    private Long id_promocion;

    private LocalDateTime fecha_aplicacion;

    private String estado;
}

