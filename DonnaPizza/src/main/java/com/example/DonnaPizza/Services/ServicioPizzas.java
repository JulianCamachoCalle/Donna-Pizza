package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Pizzas;
import com.example.DonnaPizza.Repository.PizzasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPizzas {

    // Link al Respository
    private final PizzasRepository pizzasRepository;

    HashMap<String, Object> datosPizzas;

    @Autowired
    public ServicioPizzas(PizzasRepository pizzasRepository) {
        this.pizzasRepository = pizzasRepository;
    }

    // Obtener Todos
    public List<Pizzas> getPizzas() {
        return this.pizzasRepository.findAll();
    }

    // Obtener por ID
    public Optional<Pizzas> getPizzaById(Long id) {
        return pizzasRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPizza(Pizzas pizza) {
        datosPizzas = new HashMap<>();

        // Verificar Nombre Existente
        Optional<Pizzas> resNom = pizzasRepository.findPizzaByNombre(pizza.getNombre());

        // Mensaje de error Nombre
        if (resNom.isPresent()) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosPizzas.put("mensaje", "Se ha registrado el Producto");
        pizzasRepository.save(pizza);
        datosPizzas.put("data", pizza);
        return new ResponseEntity<>(
                datosPizzas,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePizza(Long id, Pizzas pizza) {
        datosPizzas = new HashMap<>();

        // Buscar la pizza por ID
        Optional<Pizzas> pizzasExistente = pizzasRepository.findById(id);
        if (pizzasExistente.isEmpty()) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya esta en uso
        Optional<Pizzas> resNom = pizzasRepository.findPizzaByNombre(pizza.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_pizza().equals(id)) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Pizza
        Pizzas pizzasActualizar = pizzasExistente.get();
        pizzasActualizar.setNombre(pizza.getNombre());
        pizzasActualizar.setDescripcion(pizza.getDescripcion());
        pizzasActualizar.setPrecio(pizza.getPrecio());
        pizzasActualizar.setDisponible(pizza.getDisponible());

        pizzasRepository.save(pizzasActualizar);
        datosPizzas.put("mensaje", "Se actualizó el Producto");
        datosPizzas.put("data", pizza);

        return new ResponseEntity<>(
                datosPizzas,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePizza(Long id) {
        datosPizzas = new HashMap<>();
        boolean existePizza = this.pizzasRepository.existsById(id);
        if (!existePizza) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "No existe un Producto con ese id");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.CONFLICT
            );
        }
        pizzasRepository.deleteById(id);
        datosPizzas.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(
                datosPizzas,
                HttpStatus.OK
        );
    }
}
