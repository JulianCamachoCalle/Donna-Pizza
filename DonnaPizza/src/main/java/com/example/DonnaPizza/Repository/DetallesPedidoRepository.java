package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.DetallesPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Long> {
    //buscar segun id pedido
    //Optional<DetallesPedido> findByIdPedido(Long id_pedido);
}

