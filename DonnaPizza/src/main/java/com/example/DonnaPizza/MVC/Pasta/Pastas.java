package com.example.DonnaPizza.MVC.Pasta;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pastas")
public class Pastas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long  id_pasta;
    public String descripcion;
    public Double precio;
    public Boolean disponible;
}
