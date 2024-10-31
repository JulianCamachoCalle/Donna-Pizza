package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

    // Buscar Segun Id_usuario
    Optional<Pedidos> findByIdUsuario(Long id_usario);

    // Buscar Segun Id_cliente
    Optional<Pedidos> findByIdCliente(Long id_cliente);
}
