package com.example.DonnaPizza.MVC.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/clientes")
public class ClienteControlador {

    // Link al Servicio
    private final ServicioCliente servicioCliente;

    @Autowired
    public ClienteControlador(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    // Obtener Todos
    @GetMapping
    public List<Cliente> getClientes() {
        return this.servicioCliente.getClientes();
    }

    // Obtener por Id
    @GetMapping("{clienteId}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("clienteId") Long id) {
        Optional<Cliente> cliente = this.servicioCliente.getClienteById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarCliente(@RequestBody Cliente cliente) {
        return this.servicioCliente.newCliente(cliente);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return this.servicioCliente.updateCliente(id, cliente);
    }

    // Eliminar
    @DeleteMapping(path = "{clienteId}")
    public ResponseEntity<Object> eliminarCliente(@PathVariable("clienteId") Long id) {
        return this.servicioCliente.deleteCliente(id);
    }
}