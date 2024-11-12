package com.example.DonnaPizza.MVC.DetallesPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioDetallesPedido {

    // Inyección del repositorio
    private final DetallesPedidoRepository detallesPedidoRepository;
    private HashMap<String, Object> datosDetallesPedido;

    @Autowired
    public ServicioDetallesPedido(DetallesPedidoRepository detallesPedidoRepository) {
        this.detallesPedidoRepository = detallesPedidoRepository;
    }

    // **Obtener todos los Detalles de Pedidos**
    public List<DetallesPedido> getDetallesPedido() {
        return detallesPedidoRepository.findAll();
    }

    // **Obtener Detalle de Pedido por ID**
    public ResponseEntity<Object> getDetallesPedidoById_pedido(Long id_pedido) {
        datosDetallesPedido = new HashMap<>();
        Optional<DetallesPedido> detalle = detallesPedidoRepository.findById(id_pedido);

        if (detalle.isEmpty()) {
            datosDetallesPedido.put("error", true);
            datosDetallesPedido.put("mensaje", "Detalle de pedido no encontrado");
            return new ResponseEntity<>(datosDetallesPedido, HttpStatus.NOT_FOUND);
        }

        datosDetallesPedido.put("data", detalle.get());
        return new ResponseEntity<>(datosDetallesPedido, HttpStatus.OK);
    }

    // **Crear un nuevo Detalle de Pedido**
    public ResponseEntity<Object> newDetallesPedido(DetallesPedido detallesPedido) {
        datosDetallesPedido = new HashMap<>();

        // Verificar si hay un subtotal negativo
        if (detallesPedido.getSubtotal() < 0) {
            datosDetallesPedido.put("error", true);
            datosDetallesPedido.put("mensaje", "El subtotal no puede ser negativo");
            return new ResponseEntity<>(datosDetallesPedido, HttpStatus.BAD_REQUEST);
        }

        // Guardar el detalle del pedido
        detallesPedidoRepository.save(detallesPedido);
        datosDetallesPedido.put("mensaje", "Detalle de pedido registrado con éxito");
        datosDetallesPedido.put("data", detallesPedido);
        return new ResponseEntity<>(datosDetallesPedido, HttpStatus.CREATED);
    }

    // **Actualizar un Detalle de Pedido**
    public ResponseEntity<Object> updateDetallesPedido(Long id, DetallesPedido detallesActualizados) {
        datosDetallesPedido = new HashMap<>();

        // Buscar el detalle por ID
        Optional<DetallesPedido> detalleExistente = detallesPedidoRepository.findById(id);

        if (detalleExistente.isEmpty()) {
            datosDetallesPedido.put("error", true);
            datosDetallesPedido.put("mensaje", "Detalle de pedido no encontrado");
            return new ResponseEntity<>(datosDetallesPedido, HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del detalle existente
        DetallesPedido detalle = detalleExistente.get();
        detalle.setId_pedido(detallesActualizados.getId_pedido());
        detalle.setId_pizza(detallesActualizados.getId_pizza());
        detalle.setId_pizza_familiar(detallesActualizados.getId_pizza_familiar());
        detalle.setId_pasta(detallesActualizados.getId_pasta());
        detalle.setId_entrada(detallesActualizados.getId_entrada());
        detalle.setCantidad(detallesActualizados.getCantidad());
        detalle.setPrecio_unitario(detallesActualizados.getPrecio_unitario());
        detalle.setSubtotal(detallesActualizados.getSubtotal());

        detallesPedidoRepository.save(detalle);
        datosDetallesPedido.put("mensaje", "Detalle de pedido actualizado con éxito");
        datosDetallesPedido.put("data", detalle);

        return new ResponseEntity<>(datosDetallesPedido, HttpStatus.OK);
    }

    // **Eliminar un Detalle de Pedido**
    public ResponseEntity<Object> deleteDetallesPedido(Long id) {
        datosDetallesPedido = new HashMap<>();

        // Verificar si existe el detalle por ID
        boolean existe = detallesPedidoRepository.existsById(id);
        if (!existe) {
            datosDetallesPedido.put("error", true);
            datosDetallesPedido.put("mensaje", "No existe un detalle de pedido con ese ID");
            return new ResponseEntity<>(datosDetallesPedido, HttpStatus.NOT_FOUND);
        }

        // Eliminar el detalle
        detallesPedidoRepository.deleteById(id);
        datosDetallesPedido.put("mensaje", "Detalle de pedido eliminado con éxito");

        return new ResponseEntity<>(datosDetallesPedido, HttpStatus.ACCEPTED);
    }
}
