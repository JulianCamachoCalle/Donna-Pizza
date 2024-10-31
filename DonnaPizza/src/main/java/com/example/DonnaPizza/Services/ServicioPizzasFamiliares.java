package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.PizzasFamiliares;
import com.example.DonnaPizza.Repository.PizzasFamiliaresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPizzasFamiliares {

    // Link al Repository
    private final PizzasFamiliaresRepository pizzasFamiliaresRepository;

    // Datos para respuestas
    private final HashMap<String, Object> datosPizzasFamiliares = new HashMap<>();

    @Autowired
    public ServicioPizzasFamiliares(PizzasFamiliaresRepository pizzasFamiliaresRepository) {
        this.pizzasFamiliaresRepository = pizzasFamiliaresRepository;
    }

    // Obtener todas las pizzas familiares
    public List<PizzasFamiliares> getPizzasFamiliares() {
        return this.pizzasFamiliaresRepository.findAll();
    }

    // Obtener pizza familiar por ID
    public ResponseEntity<Object> getPizzaFamiliarById(Long id) {
        Optional<PizzasFamiliares> pizzaFamiliar = pizzasFamiliaresRepository.findById(id);
        if (pizzaFamiliar.isPresent()) {
            datosPizzasFamiliares.put("data", pizzaFamiliar.get());
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.OK);
        } else {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.NOT_FOUND);
        }
    }

    // Crear nuevo
    public ResponseEntity<Object> newPizzaFamiliar(PizzasFamiliares pizzaFamiliar) {
        // Verificar nombre existente
        Optional<PizzasFamiliares> resNom = pizzasFamiliaresRepository.findPizzaFamiliarByNombre(pizzaFamiliar.getNombre());

        if (resNom.isPresent()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un producto con ese nombre");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.CONFLICT);
        }

        // Guardar con éxito
        pizzasFamiliaresRepository.save(pizzaFamiliar);
        datosPizzasFamiliares.put("mensaje", "Se ha registrado el producto");
        datosPizzasFamiliares.put("data", pizzaFamiliar);
        return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.CREATED);
    }

    // Actualizar
    public ResponseEntity<Object> updatePizzaFamiliar(Long id, PizzasFamiliares pizzaFamiliar) {
        // Buscar la pizza por ID
        Optional<PizzasFamiliares> pizzaFamiliarExistente = pizzasFamiliaresRepository.findById(id);
        if (pizzaFamiliarExistente.isEmpty()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.NOT_FOUND);
        }

        // Verificar si el nombre ya está en uso
        Optional<PizzasFamiliares> resNom = pizzasFamiliaresRepository.findPizzaFamiliarByNombre(pizzaFamiliar.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_pizza_familiar().equals(id)) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un producto con ese nombre");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.CONFLICT);
        }

        // Actualizar pizza
        PizzasFamiliares pizzaFamiliaresActualizar = pizzaFamiliarExistente.get();
        pizzaFamiliaresActualizar.setNombre(pizzaFamiliar.getNombre());
        pizzaFamiliaresActualizar.setDescripcion(pizzaFamiliar.getDescripcion());
        pizzaFamiliaresActualizar.setPrecio(pizzaFamiliar.getPrecio());
        pizzaFamiliaresActualizar.setDisponible(pizzaFamiliar.getDisponible());

        pizzasFamiliaresRepository.save(pizzaFamiliaresActualizar);
        datosPizzasFamiliares.put("mensaje", "Se actualizó el producto");
        datosPizzasFamiliares.put("data", pizzaFamiliaresActualizar);

        return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.OK);
    }

    // Eliminar
    public ResponseEntity<Object> deletePizzaFamiliar(Long id) {
        if (!pizzasFamiliaresRepository.existsById(id)) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "No existe un producto con ese id");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.NOT_FOUND);
        }

        pizzasFamiliaresRepository.deleteById(id);
        datosPizzasFamiliares.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.OK);
    }
}
