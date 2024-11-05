package com.example.DonnaPizza.controladores;


import com.example.DonnaPizza.Services.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

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

    @GetMapping("/primerlocal")
    public String primerlocal(Model model) {
        return "primerlocal";
    }

    @GetMapping("/segundolocal")
    public String segundolocal(Model model) {
        return "segundolocal";
    }

    @GetMapping("/inicioSesion")
    public String login(Model model) {
        return "inicioSesion";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        return "access-denied";
    }

    @GetMapping("/menuUsuario")
    public String menuUsuario(Model model) {
        return "menuUsuario";
    }

    // CRUD Clientes
    private final ServicioCliente servicioCliente;
    private final ServicioIngredientes servicioIngredientes;
    private final ServicioDocumentos servicioDocumentos;
    private final ServicioPromociones servicioPromociones;
    private final ServicioPromocionesUsuarios servicioPromocionesUsuarios;
    private final ServicioPizzas servicioPizzas;
    private final ServicioPizzasFamiliares servicioPizzasFamiliares;
    private final ServicioUsuarios servicioUsuarios;
    private final ServicioEntrada servicioEntrada;
    private final ServicioPasta servicioPasta;

    public ControladorPrincipal(
            ServicioCliente servicioCliente,
            ServicioIngredientes servicioIngredientes,
            ServicioDocumentos servicioDocumentos,
            ServicioPromociones servicioPromociones,
            ServicioPromocionesUsuarios servicioPromocionesUsuarios,
            ServicioPizzas servicioPizzas,
            ServicioPizzasFamiliares servicioPizzasFamiliares,
            ServicioUsuarios servicioUsuarios,
            ServicioEntrada servicioEntrada,
            ServicioPasta servicioPasta) {
        this.servicioCliente = servicioCliente;
        this.servicioIngredientes = servicioIngredientes;
        this.servicioDocumentos = servicioDocumentos;
        this.servicioPromociones = servicioPromociones;
        this.servicioPromocionesUsuarios = servicioPromocionesUsuarios;
        this.servicioPizzas = servicioPizzas;
        this.servicioPizzasFamiliares = servicioPizzasFamiliares;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioEntrada = servicioEntrada;
        this.servicioPasta = servicioPasta;
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

    @GetMapping("/documentos")
    public String listaDocumentos(Model model){
        model.addAttribute("documentos",servicioDocumentos.getDocumentos());
        return "CRUDDocumentos";
    }

    @GetMapping("/promociones")
    public String listaPromociones(Model model){
        model.addAttribute("promociones",servicioPromociones.getPromociones());
        return  "CRUDPromociones";
    }

    @GetMapping("/promocionesusuarios")
    public String listaPromcoionesUsuarios(Model model){
        model.addAttribute("promocionesusuarios",servicioPromocionesUsuarios.getPromocionesUsuarios());
        return "CRUDPromocionesUsuarios";
    }

    @GetMapping("/pizzas")
    public String listarPizzas(Model model) {
        model.addAttribute("pizzas", servicioPizzas.getPizzas());
        return "CRUDPizzas";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", servicioUsuarios.getUsuarios());
        return "CRUDUsuarios";
    }

    @GetMapping("/entradas")
    public String listarEntrada(Model model){
        model.addAttribute("entradas",servicioEntrada.obtenerTodasLasEntradas());
        return "CRUDEntrada";
    }

    @GetMapping("/pasta")
    public String listarPasta(Model model){
        model.addAttribute("pasta", servicioPasta.obtenerTodasLasPastas());
        return "CRUDPasta";
    }

    @GetMapping("/fromclient")
    public String fromclient(Model model) {
        return "fromclient";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        request.getSession().setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", new Locale(lang));
        return "redirect:" + request.getHeader("Referer");
    }


}
