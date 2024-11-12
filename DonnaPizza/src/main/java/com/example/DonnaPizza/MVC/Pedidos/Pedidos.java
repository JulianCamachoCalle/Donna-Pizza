package com.example.DonnaPizza.MVC.Pedidos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "pedidos")
public class Pedidos {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;

    @Column(unique = true)
    private Long id_usuario;

    @Column(unique = true)
    private Long id_cliente;

    private String fecha;

    private Double total;

    private Long id_documento;
}
