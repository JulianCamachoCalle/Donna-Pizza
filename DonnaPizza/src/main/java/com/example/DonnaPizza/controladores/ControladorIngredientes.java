package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Ingredientes;
import com.example.DonnaPizza.Services.ServicioIngredientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/ingredientes")
public class ControladorIngredientes {

    // Link al Servicio
    private final ServicioIngredientes servicioIngredientes;

    @Autowired
    public ControladorIngredientes(ServicioIngredientes servicioIngredientes) {
        this.servicioIngredientes = servicioIngredientes;
    }

    //Obtener Todos
    @GetMapping
    public List<Ingredientes> getIngredientes() {
        return servicioIngredientes.getIngredientes();
    }

    // Obtener por Id
    @GetMapping("{ingredienteId}")
    public ResponseEntity<Ingredientes> getIngrediente(@PathVariable("ingredienteId") Long id) {
        Optional<Ingredientes> ingredientes = this.servicioIngredientes.getIngredienteById(id);
        if (ingredientes.isPresent()) {
            return ResponseEntity.ok(ingredientes.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarIngrediente(@RequestBody Ingredientes ingredientes) {
        return this.servicioIngredientes.newIngrediente(ingredientes);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarIngrediente(@PathVariable Long id, @RequestBody Ingredientes ingredientes) {
        return this.servicioIngredientes.updateIngrediente(id, ingredientes);
    }

    // Eliminar
    @DeleteMapping(path = "{ingredienteId}")
    public ResponseEntity<Object> eliminarIngrediente(@PathVariable("ingredienteId") Long id) {
        return this.servicioIngredientes.deleteIngrediente(id);
    }
}