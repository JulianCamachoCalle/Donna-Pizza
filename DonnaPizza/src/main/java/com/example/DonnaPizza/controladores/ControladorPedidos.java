package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Pedidos;
import com.example.DonnaPizza.Services.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/pedidos")
public class ControladorPedidos {

    // Link al Servicio
    private final ServicioPedidos servicioPedidos;

    @Autowired
    public ControladorPedidos(ServicioPedidos servicioPedidos) {
        this.servicioPedidos = servicioPedidos;
    }

    //Obtener Todos
    @GetMapping
    public List<Pedidos> getPedidos() {
        return servicioPedidos.getPedidos();
    }

    // Obtener por Id
    @GetMapping("{pedidoId}")
    public ResponseEntity<Pedidos> getPedido(@PathVariable("pedidoId") Long id) {
        Optional<Pedidos> pedidos = this.servicioPedidos.getPedidoById(id);
        if (pedidos.isPresent()) {
            return ResponseEntity.ok(pedidos.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarPedido(@RequestBody Pedidos pedidos) {
        return this.servicioPedidos.newPedido(pedidos);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPedido(@PathVariable Long id, @RequestBody Pedidos pedidos) {
        return this.servicioPedidos.updatePedido(id, pedidos);
    }

    // Eliminar
    @DeleteMapping(path = "{pedidoId}")
    public ResponseEntity<Object> eliminarPedido(@PathVariable("pedidoId") Long id) {
        return this.servicioPedidos.deletePedido(id);
    }
}
