package com.example.DonnaPizza.Model;

import com.example.DonnaPizza.User.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
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
public class Usuarios implements UserDetails {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nombre;

    private String apellido;

    @Column(name = "email", unique = true)
    private String username;

    @Column(unique = true)
    private String telefono;

    private String direccion;

    @Enumerated(EnumType.STRING)
    private Role rol;

    @Column(name = "contraseña")
    private String password;

    private LocalDate fecha_registro;

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
