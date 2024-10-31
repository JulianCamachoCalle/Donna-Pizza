package com.example.DonnaPizza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "email_dto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enviando;
    private String mensaje;
    private String receptor;
}