package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Pagos;
import com.example.DonnaPizza.Services.ServicioPagos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/pagos")
public class ControladorPagos {

    // Link al Servicio
    private final ServicioPagos servicioPagos;

    @Autowired
    public ControladorPagos(ServicioPagos servicioPagos) {
        this.servicioPagos = servicioPagos;
    }

    //Obtener Todos
    @GetMapping
    public List<Pagos> getPagos() {
        return servicioPagos.getPagos();
    }

    // Obtener por Id
    @GetMapping("{pagoId}")
    public ResponseEntity<Pagos> getPago(@PathVariable("pagoId") Long id) {
        Optional<Pagos> pagos = this.servicioPagos.getPagosByIdPedido(id);
        if (pagos.isPresent()) {
            return ResponseEntity.ok(pagos.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarPedido(@RequestBody Pagos pagos) {
        return this.servicioPagos.newPago(pagos);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPedido(@PathVariable Long id, @RequestBody Pagos pagos) {
        return this.servicioPagos.updatePago(id, pagos);
    }

    // Eliminar
    @DeleteMapping(path = "{pagoId}")
    public ResponseEntity<Object> eliminarPago(@PathVariable("pagoId") Long id) {
        return this.servicioPagos.deletePago(id);
    }
}
