package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Entradas;
import com.example.DonnaPizza.Repository.EntradasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioEntradas {

    // Inyección del repositorio
    private final EntradasRepository entradasRepository;
    private HashMap<String, Object> datosEntrada;

    @Autowired
    public ServicioEntradas(EntradasRepository entradasRepository) {
        this.entradasRepository = entradasRepository;
    }

    // **Obtener todas las Entradas**
    public List<Entradas> getEntradas() {
        return entradasRepository.findAll();
    }

    // **Obtener Entrada por ID**
    public ResponseEntity<Object> getEntradaById(Long id) {
        datosEntrada = new HashMap<>();
        Optional<Entradas> entrada = entradasRepository.findById(id);

        if (entrada.isEmpty()) {
            datosEntrada.put("error", true);
            datosEntrada.put("mensaje", "Entrada no encontrada");
            return new ResponseEntity<>(datosEntrada, HttpStatus.NOT_FOUND);
        }

        datosEntrada.put("data", entrada.get());
        return new ResponseEntity<>(datosEntrada, HttpStatus.OK);
    }

    // **Crear una nueva Entrada**
    public ResponseEntity<Object> newEntrada(Entradas entrada) {
        datosEntrada = new HashMap<>();

        // Validar que el nombre no esté vacío
        if (entrada.getNombre() == null || entrada.getNombre().isEmpty()) {
            datosEntrada.put("error", true);
            datosEntrada.put("mensaje", "El nombre de la entrada es obligatorio");
            return new ResponseEntity<>(datosEntrada, HttpStatus.BAD_REQUEST);
        }

        // Guardar la entrada
        entradasRepository.save(entrada);
        datosEntrada.put("mensaje", "Entrada registrada con éxito");
        datosEntrada.put("data", entrada);
        return new ResponseEntity<>(datosEntrada, HttpStatus.CREATED);
    }

    // **Actualizar una Entrada**
    public ResponseEntity<Object> updateEntrada(Long id, Entradas entradaActualizada) {
        datosEntrada = new HashMap<>();

        // Buscar la entrada por ID
        Optional<Entradas> entradaExistente = entradasRepository.findById(id);

        if (entradaExistente.isEmpty()) {
            datosEntrada.put("error", true);
            datosEntrada.put("mensaje", "Entrada no encontrada");
            return new ResponseEntity<>(datosEntrada, HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos de la entrada
        Entradas entrada = entradaExistente.get();
        entrada.setNombre(entradaActualizada.getNombre());
        entrada.setDescripcion(entradaActualizada.getDescripcion());
        entrada.setPrecio(entradaActualizada.getPrecio());
        entrada.setDisponible(entradaActualizada.getDisponible());

        entradasRepository.save(entrada);
        datosEntrada.put("mensaje", "Entrada actualizada con éxito");
        datosEntrada.put("data", entrada);

        return new ResponseEntity<>(datosEntrada, HttpStatus.OK);
    }

    // **Eliminar una Entrada**
    public ResponseEntity<Object> deleteEntrada(Long id) {
        datosEntrada = new HashMap<>();

        // Verificar si la entrada existe
        boolean existe = entradasRepository.existsById(id);
        if (!existe) {
            datosEntrada.put("error", true);
            datosEntrada.put("mensaje", "No existe una entrada con ese ID");
            return new ResponseEntity<>(datosEntrada, HttpStatus.NOT_FOUND);
        }

        // Eliminar la entrada
        entradasRepository.deleteById(id);
        datosEntrada.put("mensaje", "Entrada eliminada con éxito");

        return new ResponseEntity<>(datosEntrada, HttpStatus.ACCEPTED);
    }
}
