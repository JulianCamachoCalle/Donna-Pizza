package com.example.DonnaPizza.MVC.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByTelefono(String telefono);

    @Modifying()
    @Query("update User u set u.username=:username, u.password=:password, u.nombre=:nombre, u.apellido=:apellido, u.telefono=:telefono, u.direccion=:direccion, u.fecha_registro=:fecha_registro where u.id_usuario=:id_usuario")
    void updateUser(@Param(value = "id_usuario") Long id_usuario,
                    @Param(value = "username") String username,
                    @Param(value = "password") String password,
                    @Param(value = "nombre") String nombre,
                    @Param(value = "apellido") String apellido,
                    @Param(value = "telefono") String telefono,
                    @Param(value = "direccion") String direccion,
                    @Param(value = "fecha_registro") LocalDate fecha_registro);
}
