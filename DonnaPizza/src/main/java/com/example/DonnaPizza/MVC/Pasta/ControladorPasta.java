package com.example.DonnaPizza.MVC.Pasta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pastas")
public class ControladorPasta {
    private final ServicioPasta servicioPasta;

    @Autowired
    public ControladorPasta(ServicioPasta servicioPasta) {
        this.servicioPasta = servicioPasta;
    }

    @PostMapping
    public Pastas crearPasta(@RequestBody Pastas pasta) {
        return servicioPasta.crearPasta(pasta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pastas> obtenerPastaPorId(@PathVariable Long id) {
        return servicioPasta.obtenerPastaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pastas> obtenerTodasLasPastas() {
        return servicioPasta.obtenerTodasLasPastas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pastas> actualizarPasta(@PathVariable Long id, @RequestBody Pastas nuevaPasta) {
        try {
            return ResponseEntity.ok(servicioPasta.actualizarPasta(id, nuevaPasta));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPasta(@PathVariable Long id) {
        try {
            servicioPasta.eliminarPasta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}