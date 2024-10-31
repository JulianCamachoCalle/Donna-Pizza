package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Pastas;
import com.example.DonnaPizza.Repository.PastasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPastas {

    // Link al Repository
    private final PastasRepository pastasRepository;

    HashMap<String, Object> datosPasta;

    @Autowired
    public ServicioPastas(PastasRepository pastasRepository) {
        this.pastasRepository = pastasRepository;
    }

    // Obtener Todos
    public List<Pastas> getPastas() {
        return this.pastasRepository.findAll();
    }

    // Obtener por ID
    public Optional<Pastas> getPastaById(Long id) {
        return pastasRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPasta(Pastas pasta) {
        datosPasta = new HashMap<>();

        // Verificar nombre existente
        Optional<Pastas> resNombre = pastasRepository.findByNombre(pasta.getNombre());
        if (resNombre.isPresent()) {
            datosPasta.put("error", true);
            datosPasta.put("mensaje", "Ya existe una pasta con ese nombre");
            return new ResponseEntity<>(datosPasta, HttpStatus.CONFLICT);
        }

        // Guardar Con Éxito
        datosPasta.put("mensaje", "Se ha registrado la Pasta");
        pastasRepository.save(pasta);
        datosPasta.put("data", pasta);
        return new ResponseEntity<>(datosPasta, HttpStatus.CREATED);
    }

    // Actualizar
    public ResponseEntity<Object> updatePasta(Long id, Pastas pasta) {
        datosPasta = new HashMap<>();

        // Buscar la pasta por ID
        Optional<Pastas> pastaExistente = pastasRepository.findById(id);
        if (pastaExistente.isEmpty()) {
            datosPasta.put("error", true);
            datosPasta.put("mensaje", "Pasta no encontrada");
            return new ResponseEntity<>(datosPasta, HttpStatus.NOT_FOUND);
        }

        // Verificar si el nombre ya está en uso por otra pasta
        Optional<Pastas> resNombre = pastasRepository.findByNombre(pasta.getNombre());
        if (resNombre.isPresent() && !resNombre.get().getId_pasta().equals(id)) {
            datosPasta.put("error", true);
            datosPasta.put("mensaje", "Ya existe una pasta con ese nombre");
            return new ResponseEntity<>(datosPasta, HttpStatus.CONFLICT);
        }

        // Actualizar la pasta
        Pastas pastaActualizar = pastaExistente.get();
        pastaActualizar.setNombre(pasta.getNombre());
        pastaActualizar.setDescripcion(pasta.getDescripcion());
        pastaActualizar.setPrecio(pasta.getPrecio());
        pastaActualizar.setDisponible(pasta.getDisponible());

        pastasRepository.save(pastaActualizar);
        datosPasta.put("mensaje", "Se actualizó la Pasta");
        datosPasta.put("data", pastaActualizar);

        return new ResponseEntity<>(datosPasta, HttpStatus.OK);
    }

    // Eliminar
    public ResponseEntity<Object> deletePasta(Long id) {
        datosPasta = new HashMap<>();
        boolean existePasta = this.pastasRepository.existsById(id);
        if (!existePasta) {
            datosPasta.put("error", true);
            datosPasta.put("mensaje", "No existe una pasta con ese id");
            return new ResponseEntity<>(datosPasta, HttpStatus.CONFLICT);
        }
        pastasRepository.deleteById(id);
        datosPasta.put("mensaje", "Pasta eliminada");
        return new ResponseEntity<>(datosPasta, HttpStatus.ACCEPTED);
    }
}
