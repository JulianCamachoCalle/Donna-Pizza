package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.IngredientesEntrada;
import com.example.DonnaPizza.Model.IngredientesEntradaId;
import com.example.DonnaPizza.Repository.IngredientesEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioIngredientesEntrada {

    private final IngredientesEntradaRepository ingredientesEntradaRepository;
    private HashMap<String, Object> datosIngrediente;

    @Autowired
    public ServicioIngredientesEntrada(IngredientesEntradaRepository ingredientesEntradaRepository) {
        this.ingredientesEntradaRepository = ingredientesEntradaRepository;
    }

    // Obtener todos los Ingredientes de Entradas
    public List<IngredientesEntrada> getIngredientesEntrada() {
        return ingredientesEntradaRepository.findAll();
    }

    // Obtener Ingrediente de Entrada por ID compuesto
    public ResponseEntity<Object> getIngredienteEntradaById(Long idEntrada, Long idIngrediente) {
        datosIngrediente = new HashMap<>();
        Optional<IngredientesEntrada> ingrediente = ingredientesEntradaRepository.findById(idEntrada);

        if (ingrediente.isEmpty()) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "Ingrediente de entrada no encontrado");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.NOT_FOUND);
        }

        datosIngrediente.put("data", ingrediente.get());
        return new ResponseEntity<>(datosIngrediente, HttpStatus.OK);
    }

    // Crear un nuevo Ingrediente de Entrada
    public ResponseEntity<Object> newIngredienteEntrada(IngredientesEntrada ingredienteEntrada) {
        datosIngrediente = new HashMap<>();

        if (ingredienteEntrada.getId_entrada() == null || ingredienteEntrada.getId_ingrediente() == null) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "Los IDs de entrada e ingrediente son obligatorios");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.BAD_REQUEST);
        }

        ingredientesEntradaRepository.save(ingredienteEntrada);
        datosIngrediente.put("mensaje", "Ingrediente de entrada registrado con éxito");
        datosIngrediente.put("data", ingredienteEntrada);
        return new ResponseEntity<>(datosIngrediente, HttpStatus.CREATED);
    }

    // Actualizar un Ingrediente de Entrada
    public ResponseEntity<Object> updateIngredienteEntrada(Long idEntrada, Long idIngrediente, IngredientesEntrada ingredienteEntradaActualizado) {
        datosIngrediente = new HashMap<>();

        Optional<IngredientesEntrada> ingredienteExistente = ingredientesEntradaRepository.findById(idEntrada);

        if (ingredienteExistente.isEmpty()) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "Ingrediente de entrada no encontrado");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.NOT_FOUND);
        }

        IngredientesEntrada ingredienteEntrada = ingredienteExistente.get();
        ingredienteEntrada.setCantidad_necesaria(ingredienteEntradaActualizado.getCantidad_necesaria());

        ingredientesEntradaRepository.save(ingredienteEntrada);
        datosIngrediente.put("mensaje", "Ingrediente de entrada actualizado con éxito");
        datosIngrediente.put("data", ingredienteEntrada);

        return new ResponseEntity<>(datosIngrediente, HttpStatus.OK);
    }

    // Eliminar un Ingrediente de Entrada
    public ResponseEntity<Object> deleteIngredienteEntrada(Long idEntrada, Long idIngrediente) {
        datosIngrediente = new HashMap<>();

        IngredientesEntradaId ingredientesEntradaId = new IngredientesEntradaId(idEntrada, idIngrediente);
        boolean existe = ingredientesEntradaRepository.existsById(idEntrada);
        if (!existe) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "No existe un ingrediente de entrada con esos IDs");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.NOT_FOUND);
        }

        ingredientesEntradaRepository.deleteById(idEntrada);
        datosIngrediente.put("mensaje", "Ingrediente de entrada eliminado con éxito");

        return new ResponseEntity<>(datosIngrediente, HttpStatus.ACCEPTED);
    }
}
