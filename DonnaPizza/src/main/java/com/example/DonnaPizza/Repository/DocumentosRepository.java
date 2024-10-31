package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {

    List<Documentos> findByTipoDocumentos(String tipoDocumento);

    Optional<Documentos> findByIdDocumentos(Long idDocumento);
}
