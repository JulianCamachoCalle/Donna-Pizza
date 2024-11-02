package com.example.DonnaPizza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "promociones_usuarios")
public class PromocionesUsuarios {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPromocionUsuario;

    private Long idUsuario;

    private Long idPromocion;

    private LocalDateTime fechaAplicacion;

    private String estado;
}

