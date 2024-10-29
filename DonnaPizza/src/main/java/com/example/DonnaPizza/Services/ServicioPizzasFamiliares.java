package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Pizzas;
import com.example.DonnaPizza.Model.PizzasFamiliares;
import com.example.DonnaPizza.Repository.PizzasFamiliaresRepository;
import com.example.DonnaPizza.Repository.PizzasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPizzasFamiliares {

    // Link al Respository
    private final PizzasFamiliaresRepository pizzasfamiliaresRepository;

    HashMap<String, Object> datosPizzasFamiliares;

    @Autowired
    public ServicioPizzasFamiliares(PizzasFamiliaresRepository pizzasfamiliaresRepository) {
        this.pizzasfamiliaresRepository = pizzasfamiliaresRepository;
    }

    // Obtener Todos
    public List<PizzasFamiliares> getPizzasFamiliares() {
        return this.pizzasfamiliaresRepository.findAll();
    }

    // Obtener por ID
    public Optional<PizzasFamiliares> getPizzaFamiliarById(Long id) {
        return pizzasfamiliaresRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPizzaFamiliar(PizzasFamiliares pizzafamiliar) {
        datosPizzasFamiliares = new HashMap<>();

        // Verificar Nombre Existente
        Optional<PizzasFamiliares> resNom = pizzasfamiliaresRepository.findPizzaFamiliarByNombre(pizzafamiliar.getNombre());

        // Mensaje de error Nombre
        if (resNom.isPresent()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosPizzasFamiliares.put("mensaje", "Se ha registrado el Producto");
        pizzasfamiliaresRepository.save(pizzafamiliar);
        datosPizzasFamiliares.put("data", pizzafamiliar);
        return new ResponseEntity<>(
                datosPizzasFamiliares,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePizzaFamiliar(Long id, PizzasFamiliares pizzafamiliar) {
        datosPizzasFamiliares = new HashMap<>();

        // Buscar la pizza por ID
        Optional<PizzasFamiliares> pizzasfamiliaresExistente = pizzasfamiliaresRepository.findById(id);
        if (pizzasfamiliaresExistente.isEmpty()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya esta en uso
        Optional<PizzasFamiliares> resNom = pizzasfamiliaresRepository.findPizzaFamiliarByNombre(pizzafamiliar.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_pizza_familiar().equals(id)) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Pizza
        PizzasFamiliares pizzasfamiliaresActualizar = pizzasfamiliaresExistente.get();
        pizzasfamiliaresActualizar.setNombre(pizzafamiliar.getNombre());
        pizzasfamiliaresActualizar.setDescripcion(pizzafamiliar.getDescripcion());
        pizzasfamiliaresActualizar.setPrecio(pizzafamiliar.getPrecio());
        pizzasfamiliaresActualizar.setDisponible(pizzafamiliar.getDisponible());

        pizzasfamiliaresRepository.save(pizzasfamiliaresActualizar);
        datosPizzasFamiliares.put("mensaje", "Se actualizó el Producto");
        datosPizzasFamiliares.put("data", pizzafamiliar);

        return new ResponseEntity<>(
                datosPizzasFamiliares,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePizzaFamiliar(Long id) {
        datosPizzasFamiliares = new HashMap<>();
        boolean existePizzaFamiliar = this.pizzasfamiliaresRepository.existsById(id);
        if (!existePizzaFamiliar) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "No existe un Producto con ese id");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.CONFLICT
            );
        }
        pizzasfamiliaresRepository.deleteById(id);
        datosPizzasFamiliares.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(
                datosPizzasFamiliares,
                HttpStatus.OK
        );
    }
}
