package com.example.DonnaPizza.MVC.Pagos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "pagos")
public class Pagos {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pago;

    @Column(unique = true)
    private long id_pedido;

    @Column(unique = true)
    private long id_metodo_pago;

    private Double monto;

    private String fecha;
}
