package com.example.DonnaPizza.MVC.Pagos;

import jakarta.persistence.*;
import java.util.Date;
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

    private Long id_pedido;

    private Long id_metodo_pago;

    private Double monto;

    private Date fecha;
}
