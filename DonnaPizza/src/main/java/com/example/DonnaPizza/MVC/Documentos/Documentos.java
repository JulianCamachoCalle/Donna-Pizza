package com.example.DonnaPizza.MVC.Documentos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "documentos")
public class Documentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    @Column(name = "tipo_documento")
    private String tipoDocumento;
}
