package com.example.DonnaPizza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "usuarios")
public class Usuarios {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefono;

    private String direccion;

    private String rol;

    private String contraseña;

    private LocalDate fecha_registro;

    @PrePersist
    protected void onCreate() {
        this.fecha_registro = LocalDate.now();
    }
}
