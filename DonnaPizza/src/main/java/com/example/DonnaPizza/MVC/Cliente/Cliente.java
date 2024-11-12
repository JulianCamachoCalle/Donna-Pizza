package com.example.DonnaPizza.MVC.Cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "clientes")
public class Cliente {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefono;

    private String direccion;

}
