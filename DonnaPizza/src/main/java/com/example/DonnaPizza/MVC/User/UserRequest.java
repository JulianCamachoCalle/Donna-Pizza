package com.example.DonnaPizza.MVC.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    String nombre;
    String apellido;
    String username;
    String telefono;
    String direccion;
    String password;
    LocalDate fecha_registro;
}
