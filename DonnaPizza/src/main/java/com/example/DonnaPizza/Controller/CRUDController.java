package com.example.DonnaPizza.Controller;

import com.example.DonnaPizza.MVC.Cliente.ServicioCliente;
import com.example.DonnaPizza.MVC.Documentos.ServicioDocumentos;
import com.example.DonnaPizza.MVC.Ingredientes.ServicioIngredientes;
import com.example.DonnaPizza.MVC.Pizzas.ServicioPizzas;
import com.example.DonnaPizza.MVC.PizzasFamiliares.ServicioPizzasFamiliares;
import com.example.DonnaPizza.MVC.Promociones.ServicioPromociones;
import com.example.DonnaPizza.MVC.PromocionesUsuarios.ServicioPromocionesUsuarios;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/crud")
public class CRUDController {

    // CRUD Clientes
    private final ServicioCliente servicioCliente;
    private final ServicioIngredientes servicioIngredientes;
    private final ServicioDocumentos servicioDocumentos;
    private final ServicioPromociones servicioPromociones;
    private final ServicioPromocionesUsuarios servicioPromocionesUsuarios;
    private final ServicioPizzas servicioPizzas;
    private final ServicioPizzasFamiliares servicioPizzasFamiliares;

    public CRUDController(
            ServicioCliente servicioCliente,
            ServicioIngredientes servicioIngredientes,
            ServicioDocumentos servicioDocumentos,
            ServicioPromociones servicioPromociones,
            ServicioPromocionesUsuarios servicioPromocionesUsuarios,
            ServicioPizzas servicioPizzas,
            ServicioPizzasFamiliares servicioPizzasFamiliares) {
        this.servicioCliente = servicioCliente;
        this.servicioIngredientes = servicioIngredientes;
        this.servicioDocumentos = servicioDocumentos;
        this.servicioPromociones = servicioPromociones;
        this.servicioPromocionesUsuarios = servicioPromocionesUsuarios;
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

    @GetMapping("/documentos")
    public String listaDocumentos(Model model){
        model.addAttribute("documentos",servicioDocumentos.getDocumentos());
        return "CRUDDocumentos";
    }
    //Desarrollo de anthony

    @GetMapping("/promociones")
    public String listaPromociones(Model model){
        model.addAttribute("promociones",servicioPromociones.getPromociones());
        return  "CRUDPromociones";
    }
    //Desarrollo de anthony
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
}
