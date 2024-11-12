package com.example.DonnaPizza.MVC.MetodosPago;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "metodos_pago")
public class MetodosPago {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_metodo_pago;

    private String nombre;

    private String descripcion;
}
