package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Pastas;
import com.example.DonnaPizza.Services.ServicioPastas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/pastas")
public class PastasControlador {

    // Link al Servicio
    private final ServicioPastas servicioPastas;

    @Autowired
    public PastasControlador(ServicioPastas servicioPastas) {
        this.servicioPastas = servicioPastas;
    }

    // Obtener Todos
    @GetMapping
    public List<Pastas> getPastas() {
        return this.servicioPastas.getPastas();
    }

    // Obtener por Id
    @GetMapping("{pastaId}")
    public ResponseEntity<Pastas> getPasta(@PathVariable("pastaId") Long id) {
        Optional<Pastas> pasta = this.servicioPastas.getPastaById(id);
        if (pasta.isPresent()) {
            return ResponseEntity.ok(pasta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarPasta(@RequestBody Pastas pasta) {
        return this.servicioPastas.newPasta(pasta);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPasta(@PathVariable Long id, @RequestBody Pastas pasta) {
        return this.servicioPastas.updatePasta(id, pasta);
    }

    // Eliminar
    @DeleteMapping(path = "{pastaId}")
    public ResponseEntity<Object> eliminarPasta(@PathVariable("pastaId") Long id) {
        return this.servicioPastas.deletePasta(id);
    }
}
