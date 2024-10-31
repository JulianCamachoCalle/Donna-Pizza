package com.example.DonnaPizza.Auth;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    String nombre;
    String apellido;
    String username;
    String telefono;
    String direccion;
    String password;
    LocalDate fecha_registro;

    @PrePersist
    protected void onCreate() {
        this.fecha_registro = LocalDate.now();
    }

    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.startsWith("+51 ")) {
            this.telefono = "+51 " + telefono;
        } else {
            this.telefono = telefono;
        }
    }
}
