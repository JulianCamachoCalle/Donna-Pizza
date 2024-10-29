package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Usuarios;
import com.example.DonnaPizza.Services.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/usuarios")
public class UsuariosControlador {

    // Link al Servicio
    private final ServicioUsuarios servicioUsuarios;

    @Autowired
    public UsuariosControlador(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }

    // Obtener Todos
    @GetMapping
    public List<Usuarios> getUsuarios() {
        return this.servicioUsuarios.getUsuarios();
    }

    // Obtener por Id
    @GetMapping("{usuarioId}")
    public ResponseEntity<Usuarios> getUsuarios(@PathVariable("usuarioId") Long id) {
        Optional<Usuarios> usuarios = this.servicioUsuarios.getUsuarioById(id);
        if (usuarios.isPresent()) {
            return ResponseEntity.ok(usuarios.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarUsuarios(@RequestBody Usuarios usuarios) {
        return this.servicioUsuarios.newUsuario(usuarios);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarUsuarios(@PathVariable Long id, @RequestBody Usuarios usuarios) {
        return this.servicioUsuarios.updateUsuarios(id, usuarios);
    }

    // Eliminar
    @DeleteMapping(path = "{usuarioId}")
    public ResponseEntity<Object> eliminarUsuarios(@PathVariable("usuarioId") Long id) {
        return this.servicioUsuarios.deleteUsuarios(id);
    }
}
