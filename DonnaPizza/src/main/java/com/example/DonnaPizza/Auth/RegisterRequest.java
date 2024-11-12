package com.example.DonnaPizza.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String nombre;
    String apellido;
    String username;
    String telefono;
    String direccion;
    String password;
    LocalDate fecha_registro;
}
