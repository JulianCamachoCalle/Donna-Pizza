package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.EmailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailDtoRepository extends JpaRepository<EmailDto, Long> {

    // Buscar correos electrónicos por receptor
    List<EmailDto> findByReceptor(String receptor);

    // Buscar correos electrónicos que contengan cierto texto en el mensaje
    List<EmailDto> findByMensaje(String mensaje);
}