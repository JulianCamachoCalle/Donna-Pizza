package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Ingredientes;
import com.example.DonnaPizza.Repository.IngredientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioIngredientes {

    // Link al Repository
    private final IngredientesRepository ingredientesRepository;

    HashMap<String, Object> datosIngredientes;

    @Autowired
    public ServicioIngredientes(IngredientesRepository ingredientesRepository) {
        this.ingredientesRepository = ingredientesRepository;
    }

    // Obtener Todos
    public List<Ingredientes> getIngredientes() {
        return ingredientesRepository.findAll();
    }

    // Obtener por ID
    public Optional<Ingredientes> getIngredienteById(Long id) {
        return ingredientesRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newIngrediente(Ingredientes ingredientes) {
        datosIngredientes = new HashMap<>();

        // Verificar Nombre Existente
        Optional<Ingredientes> resNom = ingredientesRepository.findIngredientesByNombre(ingredientes.getNombre());

        // Mnesaje de error Nombre
        if (resNom.isPresent()) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ya existe un ingrediente con ese nombre");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosIngredientes.put("mensaje", "Se ha registrado el Ingrediente");
        ingredientesRepository.save(ingredientes);
        datosIngredientes.put("data", ingredientes);
        return new ResponseEntity<>(
                datosIngredientes,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateIngrediente(Long id, Ingredientes ingredientes) {
        datosIngredientes = new HashMap<>();

        // Buscar Ingrediente por ID
        Optional<Ingredientes> ingredienteExistente = ingredientesRepository.findById(id);
        if (ingredienteExistente.isEmpty()) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ingrediente no encontrado");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya está usado
        Optional<Ingredientes> resNom = ingredientesRepository.findIngredientesByNombre(ingredientes.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_ingrediente().equals(id)) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ya existe un ingrediente con ese nombre");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Ingrediente
        Ingredientes ingredienteActualizar = ingredienteExistente.get();
        ingredienteActualizar.setNombre(ingredientes.getNombre());
        ingredienteActualizar.setCantidad_disponible(ingredientes.getCantidad_disponible());

        ingredientesRepository.save(ingredienteActualizar);
        datosIngredientes.put("mensaje", "Se actualizó el Ingrediente");
        datosIngredientes.put("data", ingredienteActualizar);

        return new ResponseEntity<>(
                datosIngredientes,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deleteIngrediente(Long id) {
        datosIngredientes = new HashMap<>();
        boolean existeIngrediente = ingredientesRepository.existsById(id);
        if (!existeIngrediente) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "No existe un ingrediente con ese id");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        }
        ingredientesRepository.deleteById(id);
        datosIngredientes.put("mensaje", "Ingrediente eliminado");
        return new ResponseEntity<>(
                datosIngredientes,
                HttpStatus.ACCEPTED
        );
    }
}
