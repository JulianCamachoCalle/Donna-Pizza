package com.example.DonnaPizza.MVC.Pedidos;

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
@Table(name = "pedidos")
public class Pedidos {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;

    private Long id_usuario;

    private Long id_cliente;

    private LocalDateTime fecha;

    private Double total;
}
