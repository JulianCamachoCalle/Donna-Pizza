package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.DetallesPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Long> {

    // Buscar por id_pedido
    List<DetallesPedido> findById_pedido(Long id_pedido);

    // Buscar por id_pizza
    List<DetallesPedido> findById_pizza(Long id_pizza);

    // Buscar por id_pizza_familiar
    List<DetallesPedido> findById_pizza_familiar(Long id_pizza_familiar);

    // Buscar por id_pasta
    List<DetallesPedido> findById_asta(Long id_pasta);

    // Buscar por id_entrada
    List<DetallesPedido> findById_entrada(Long id_entrada);

    // Verificar existencia por id_pedido
    boolean existsById_pedido(Long id_pedido);

    // Eliminar por id_pedido
    void deleteById_pedido(Long id_pedido);
}

