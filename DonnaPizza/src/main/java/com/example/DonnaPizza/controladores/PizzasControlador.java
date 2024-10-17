package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Pizzas;
import com.example.DonnaPizza.Services.ServicioPizzas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PizzasControlador {

    @Autowired
    ServicioPizzas servicioPizzas;

    @GetMapping("/CRUDPizzas")
    public String CRUDPizzas(Model model) {
        List<Pizzas> lista = servicioPizzas.getList();
        model.addAttribute("lista", lista);

        return "CRUDPizzas";
    }

    @GetMapping("/formPizzas")
    public String formPizzas(Model model) {
        model.addAttribute("pizzas", new Pizzas());
        return "formPizzas";
    }

    @PostMapping("/registrarPizzas")
    public String grabarPizzas(@ModelAttribute Pizzas pizzas, Model model) {
        try {
            servicioPizzas.save(pizzas);
            return "redirect:/CRUDPizzas";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage().toString());
            model.addAttribute("pizzas", pizzas);
            return "formPizzas";
        }
    }

    @GetMapping("/getEdit/{codigopizzas}")
    public String editFormPizzas(Model model, @PathVariable("codigopizzas") Long id) {
        Pizzas pizzas = servicioPizzas.get(id);
        model.addAttribute("pizzas", pizzas);
        return "formPizzas";
    }

    public String deleteFormPizzas(Model model, @RequestParam("id") Long id) {
        servicioPizzas.delete(id);
        return "redirect:/CRUDPizzas";
    }
}
