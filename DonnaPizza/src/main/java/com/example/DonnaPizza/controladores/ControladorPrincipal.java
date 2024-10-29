package com.example.DonnaPizza.controladores;


import com.example.DonnaPizza.Services.ServicioCliente;
import com.example.DonnaPizza.Services.ServicioIngredientes;
import com.example.DonnaPizza.Services.ServicioPizzas;
import com.example.DonnaPizza.Services.ServicioPizzasFamiliares;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorPrincipal {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/carta")
    public String carta(Model model) {
        return "carta";
    }

    @GetMapping("/locales")
    public String locales(Model model) {
        return "locales";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    // CRUD Clientes
    private final ServicioCliente servicioCliente;
    private final ServicioIngredientes servicioIngredientes;
    private final ServicioPizzas servicioPizzas;
    private final ServicioPizzasFamiliares servicioPizzasFamiliares;
    public ControladorPrincipal(
            ServicioCliente servicioCliente,
            ServicioIngredientes servicioIngredientes,
            ServicioPizzas servicioPizzas,
            ServicioPizzasFamiliares servicioPizzasFamiliares) {
        this.servicioCliente = servicioCliente;
        this.servicioIngredientes = servicioIngredientes;
        this.servicioPizzas = servicioPizzas;
        this.servicioPizzasFamiliares = servicioPizzasFamiliares;
    }
    
    @GetMapping("/pizzasfamiliares")
    public String listarPizzasFamiliares(Model model) {
        model.addAttribute("pizzasfamiliares", servicioPizzasFamiliares.getPizzasFamiliares());
        return "CRUDPizzasFamiliares";
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", servicioCliente.getClientes());
        return "CRUDClientes";
    }

    @GetMapping("/ingredientes")
    public String listarIngredientes(Model model) {
        model.addAttribute("ingredientes", servicioIngredientes.getIngredientes());
        return "CRUDIngredientes";
    }

    @GetMapping("/pizzas")
    public String listarPizzas(Model model) {
        model.addAttribute("pizzas", servicioPizzas.getPizzas());
        return "CRUDPizzas";
    }

    @GetMapping("/fromclient")
    public String fromclient(Model model) {
        return "fromclient";
    }

    //clientes
    @RequestMapping("/dataclientfrom")
    public String dataclientfrom(@RequestParam("nombre") String nombre,
                                 @RequestParam("apellidos") String apellidos,
                                 @RequestParam("correo") String correo,
                                 @RequestParam("numero") String numero,
                                 @RequestParam("direccion") String direccion,
                                 Model model
    ) {
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellidos", apellidos);
        model.addAttribute("correo", correo);
        model.addAttribute("numero", numero);
        model.addAttribute("direccion", direccion);
        return "client";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        request.getSession().setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", new Locale(lang));
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/especificaciones")
    public String especificaciones(Model model) {
        return "especificaciones";
    }

}
