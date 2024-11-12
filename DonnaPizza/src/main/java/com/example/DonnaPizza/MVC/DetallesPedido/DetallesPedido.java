package com.example.DonnaPizza.MVC.DetallesPedido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "detalles_pedido")
public class DetallesPedido {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detalle;

    private Long id_pedido;

    private Long id_pizza;

    private Long id_pizza_familiar;

    private Long id_pasta;

    private Long id_entrada;

    private Integer cantidad;

    private Double precio_unitario;

    private Double subtotal;
}
