package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Cliente;
import com.example.DonnaPizza.Services.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarCliente(@RequestBody Cliente cliente) {
        return this.servicioCliente.newCliente(cliente);
    }

    // Actualizar
    @PutMapping
    public ResponseEntity<Object> actualizarCliente(@RequestBody Cliente cliente) {
        return this.servicioCliente.newCliente(cliente);
    }

    // Eliminar
    @DeleteMapping(path = "{clienteId}")
    public ResponseEntity<Object> eliminarCliente(@PathVariable("clienteId") Long id) {
        return this.servicioCliente.deleteCliente(id);
    }
}
