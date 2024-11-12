package com.example.DonnaPizza.MVC.PromocionesUsuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//Desarrollo de anthony
@Repository
public interface PromocionesUsuariosRepository extends JpaRepository<PromocionesUsuarios, Long> {

    // Buscar según id_promocion_usuario
    Optional<PromocionesUsuarios> findPromocionesUsuariosByIdPromocionUsuario(Long idPromocionUsuario);

}
