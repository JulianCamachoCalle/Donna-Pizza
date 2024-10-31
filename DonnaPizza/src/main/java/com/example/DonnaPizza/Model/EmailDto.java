package com.example.DonnaPizza.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String enviando;

    private String mensaje;

    private String receptor;
}
