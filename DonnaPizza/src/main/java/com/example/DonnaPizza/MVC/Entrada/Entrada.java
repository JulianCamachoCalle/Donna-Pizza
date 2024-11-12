package com.example.DonnaPizza.MVC.Entrada;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name =  "entradas")
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id_entrada;

    public String nombre;
    public String descripcion;
    public Double precio;
    public Boolean disponible;
}
