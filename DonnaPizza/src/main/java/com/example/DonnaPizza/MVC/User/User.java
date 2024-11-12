package com.example.DonnaPizza.MVC.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Tabla
@Table(name = "usuarios")
public class User implements UserDetails {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_usuario;

    String nombre;

    String apellido;

    @Column(name = "email", unique = true, nullable = false)
    String username;

    @Column(unique = true)
    String telefono;

    String direccion;

    @Enumerated(EnumType.STRING)
    Role rol;

    @Column(name = "contraseña")
    String password;

    LocalDate fecha_registro;

    @PrePersist
    protected void onCreate() {
        this.fecha_registro = LocalDate.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
