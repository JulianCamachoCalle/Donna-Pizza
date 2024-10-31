package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Cliente;
import com.example.DonnaPizza.Model.DetallesPedido;
import com.example.DonnaPizza.Repository.DetallesPedidoRepository;
import com.example.DonnaPizza.Services.ServicioDetallesPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/detalles-pedido")
public class DetallesPedidoControlador {

    // Link al Servicio
    @Autowired
    private final ServicioDetallesPedido servicioDetallesPedido;

    @Autowired
    public DetallesPedidoControlador(ServicioDetallesPedido servicioDetallesPedido) {
        this.servicioDetallesPedido = servicioDetallesPedido;
    }

    // Obtener Todos
    @GetMapping
    public List<DetallesPedido> getDetallesPedido() {
        return this.servicioDetallesPedido.getDetallesPedido();
    }

    // Obtener por Id
    @GetMapping("{detalleId}")
    public ResponseEntity<Object> getDetallesPedido(@PathVariable("detalleId") Long id) {
        ResponseEntity<Object> detallesPedido = servicioDetallesPedido.getDetallesPedidoById_pedido(id);
        if (!detallesPedido.getStatusCode().isError()) {
            return (ResponseEntity<Object>) ResponseEntity.ok( detallesPedido.getBody());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarDetallesPedido(@RequestBody DetallesPedido detallesPedido) {
        return this.servicioDetallesPedido.newDetallesPedido(detallesPedido);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarDetallesPedido(@PathVariable Long id, @RequestBody DetallesPedido detallesPedido) {
        return this.servicioDetallesPedido.updateDetallesPedido(id, detallesPedido);
    }

    // Eliminar
    @DeleteMapping(path = "{detalleId}")
    public ResponseEntity<Object> eliminarDetallesPedido(@PathVariable("detalleId") Long id) {
        return this.servicioDetallesPedido.deleteDetallesPedido(id);
    }
}
