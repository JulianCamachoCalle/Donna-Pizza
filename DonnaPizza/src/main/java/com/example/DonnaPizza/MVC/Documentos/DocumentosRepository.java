package com.example.DonnaPizza.MVC.Documentos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {

    // Buscar Segun tipo de documento
    Optional<Documentos> findDocumentosByTipoDocumento(String tipoDocumento);

}
