package com.example.DonnaPizza.MVC.DetallesPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Long> {
    //buscar segun id pedido
    //Optional<DetallesPedido> findByIdPedido(Long id_pedido);
}

