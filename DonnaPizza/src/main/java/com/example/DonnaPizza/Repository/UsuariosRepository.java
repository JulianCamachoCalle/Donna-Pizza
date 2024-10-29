package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    // Buscar Segun Email
    Optional<Usuarios> findUsuariosByEmail(String email);

    // Buscar Segun Telefono
    Optional<Usuarios> findUsuariosByTelefono(String telefono);
}
