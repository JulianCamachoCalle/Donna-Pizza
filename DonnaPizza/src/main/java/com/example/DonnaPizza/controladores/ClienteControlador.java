package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Cliente;
import com.example.DonnaPizza.Services.ServicioCliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteControlador {

    @Autowired
    ServicioCliente servicioCliente;

    @GetMapping("/CRUDClientes")
    public String CRUDClientes(Model model) {
        List<Cliente> lista = servicioCliente.getList();
        model.addAttribute("lista", lista);

        return "CRUDClientes";
    }

    @GetMapping("/formClientes")
    public String formClientes(Model model) {
        model.addAttribute("clientes", new Cliente());
        return "formClientes";
    }

    @PostMapping("/registrarClientes")
    public String grabarClientes(@ModelAttribute Cliente clientes, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clientes);
            return "formClientes";
        }
        try {
            servicioCliente.save(clientes);
            return "redirect:/CRUDClientes";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Error al guardar el cliente: " + e.getRootCause().getMessage());
            model.addAttribute("clientes", clientes);
            return "formClientes";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ocurrió un error inesperado: " + e.getMessage());
            model.addAttribute("clientes", clientes);
            return "formClientes";
        }
    }

    @GetMapping("/getEditCliente/{id}")
    public String editFormClientes(Model model, @PathVariable("id") Long id) {
        Cliente clientes = servicioCliente.get(id);
        model.addAttribute("clientes", clientes);
        return "formClientes";
    }

    @GetMapping("/deleteCliente/{id}")
    public String deleteFormClientes(@PathVariable("id") Long id) {
        servicioCliente.delete(id);
        return "redirect:/CRUDClientes";
    }
}
