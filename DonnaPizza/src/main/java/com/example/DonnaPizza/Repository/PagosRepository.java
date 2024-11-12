package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {

    // Buscar Segun Id-pedido
    Optional<Pagos> findByIdPedido(long id_pedido);

    // Buscar Segun Id-metodo de pago
    Optional<Pagos> findByIdMetodoPago(long id_metodo_pago);
    }
