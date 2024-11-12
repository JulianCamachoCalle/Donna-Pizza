package com.example.DonnaPizza.MVC.Pizzas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/pizzas")
public class PizzasControlador {

    // Link al Servicio
    private final ServicioPizzas servicioPizzas;

    @Autowired
    public PizzasControlador(ServicioPizzas servicioPizzas) {
        this.servicioPizzas = servicioPizzas;
    }

    // Obtener Todos
    @GetMapping
    public List<Pizzas> getPizzas() {
        return this.servicioPizzas.getPizzas();
    }

    // Obtener por Id
    @GetMapping("{pizzasId}")
    public ResponseEntity<Pizzas> getPizza(@PathVariable("pizzasId") Long id) {
        Optional<Pizzas> pizzas = this.servicioPizzas.getPizzaById(id);
        if (pizzas.isPresent()) {
            return ResponseEntity.ok(pizzas.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarPizza(@RequestBody Pizzas pizza) {
        return this.servicioPizzas.newPizza(pizza);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPizza(@PathVariable Long id, @RequestBody Pizzas pizza) {
        return this.servicioPizzas.updatePizza(id, pizza);
    }

    // Eliminar
    @DeleteMapping(path = "{pizzasId}")
    public ResponseEntity<Object> eliminarPizza(@PathVariable("pizzasId") Long id) {
        return this.servicioPizzas.deletePizza(id);
    }
}
