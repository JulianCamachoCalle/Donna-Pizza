package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.IngredientesPasta;
import com.example.DonnaPizza.Model.IngredientesPastaId;
import com.example.DonnaPizza.Repository.IngredientesPastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioIngredientesPasta {

    // Inyección del repositorio
    private final IngredientesPastaRepository ingredientesPastaRepository;
    private HashMap<String, Object> datosIngrediente;

    @Autowired
    public ServicioIngredientesPasta(IngredientesPastaRepository ingredientesPastaRepository) {
        this.ingredientesPastaRepository = ingredientesPastaRepository;
    }

    // **Obtener todos los Ingredientes de Pasta**
    public List<IngredientesPasta> getIngredientesPasta() {
        return ingredientesPastaRepository.findAll();
    }

    // **Obtener Ingrediente de Pasta por ID**
    public ResponseEntity<Object> getIngredientePastaById(Long idPasta, Long idIngrediente) {
        datosIngrediente = new HashMap<>();
        Optional<IngredientesPasta> ingrediente = ingredientesPastaRepository.findById(idPasta);

        if (ingrediente.isEmpty()) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "Ingrediente de pasta no encontrado");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.NOT_FOUND);
        }

        datosIngrediente.put("data", ingrediente.get());
        return new ResponseEntity<>(datosIngrediente, HttpStatus.OK);
    }

    // **Crear un nuevo Ingrediente de Pasta**
    public ResponseEntity<Object> newIngredientePasta(IngredientesPasta ingredientePasta) {
        datosIngrediente = new HashMap<>();

        // Validar que los IDs no sean nulos
        if (ingredientePasta.getId_pasta() == null || ingredientePasta.getId_ingrediente() == null) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "Los IDs de pasta e ingrediente son obligatorios");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.BAD_REQUEST);
        }

        // Guardar el ingrediente de pasta
        ingredientesPastaRepository.save(ingredientePasta);
        datosIngrediente.put("mensaje", "Ingrediente de pasta registrado con éxito");
        datosIngrediente.put("data", ingredientePasta);
        return new ResponseEntity<>(datosIngrediente, HttpStatus.CREATED);
    }

    // **Actualizar un Ingrediente de Pasta**
    public ResponseEntity<Object> updateIngredientePasta(Long idPasta, Long idIngrediente, IngredientesPasta ingredientePastaActualizado) {
        datosIngrediente = new HashMap<>();

        Optional<IngredientesPasta> ingredienteExistente = ingredientesPastaRepository.findById(idPasta);

        if (ingredienteExistente.isEmpty()) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "Ingrediente de pasta no encontrado");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del ingrediente de pasta
        IngredientesPasta ingredientePasta = ingredienteExistente.get();
        ingredientePasta.setCantidad_necesaria(ingredientePastaActualizado.getCantidad_necesaria());

        ingredientesPastaRepository.save(ingredientePasta);
        datosIngrediente.put("mensaje", "Ingrediente de pasta actualizado con éxito");
        datosIngrediente.put("data", ingredientePasta);

        return new ResponseEntity<>(datosIngrediente, HttpStatus.OK);
    }

    // **Eliminar un Ingrediente de Pasta**
    public ResponseEntity<Object> deleteIngredientePasta(Long idPasta, Long idIngrediente) {
        datosIngrediente = new HashMap<>();

        IngredientesPastaId ingredientesPastaId = new IngredientesPastaId(idPasta,idIngrediente);
        boolean existe = ingredientesPastaRepository.existsById_pasta(idPasta);
        if (!existe) {
            datosIngrediente.put("error", true);
            datosIngrediente.put("mensaje", "No existe un ingrediente de pasta con esos IDs");
            return new ResponseEntity<>(datosIngrediente, HttpStatus.NOT_FOUND);
        }

        ingredientesPastaRepository.deleteById_pasta(idPasta);
        datosIngrediente.put("mensaje", "Ingrediente de pasta eliminado con éxito");

        return new ResponseEntity<>(datosIngrediente, HttpStatus.ACCEPTED);
    }
}
