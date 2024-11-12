package com.example.DonnaPizza.MVC.PizzasFamiliares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/pizzasfamiliares")
public class PizzasFamiliaresControlador {

    // Link al Servicio
    private final ServicioPizzasFamiliares servicioPizzasFamiliares;

    @Autowired
    public PizzasFamiliaresControlador(ServicioPizzasFamiliares servicioPizzasFamiliares) {
        this.servicioPizzasFamiliares = servicioPizzasFamiliares;
    }

    // Obtener Todos
    @GetMapping
    public List<PizzasFamiliares> getPizzasFamiliares() {
        return this.servicioPizzasFamiliares.getPizzasFamiliares();
    }

    // Obtener por Id
    @GetMapping("{pizzasfamiliaresId}")
    public ResponseEntity<PizzasFamiliares> getPizzaFamiliar(@PathVariable("pizzasfamiliaresId") Long id) {
        Optional<PizzasFamiliares> pizzasfamiliares = this.servicioPizzasFamiliares.getPizzaFamiliarById(id);
        if (pizzasfamiliares.isPresent()) {
            return ResponseEntity.ok(pizzasfamiliares.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarPizzaFamiliar(@RequestBody PizzasFamiliares pizzafamiliar) {
        return this.servicioPizzasFamiliares.newPizzaFamiliar(pizzafamiliar);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPizzaFamiliares(@PathVariable Long id, @RequestBody PizzasFamiliares pizzafamiliar) {
        return this.servicioPizzasFamiliares.updatePizzaFamiliar(id, pizzafamiliar);
    }

    // Eliminar
    @DeleteMapping(path = "{pizzasfamiliaresId}")
    public ResponseEntity<Object> eliminarPizzaFamiliar(@PathVariable("pizzasfamiliaresId") Long id) {
        return this.servicioPizzasFamiliares.deletePizzaFamiliar(id);
    }
}
