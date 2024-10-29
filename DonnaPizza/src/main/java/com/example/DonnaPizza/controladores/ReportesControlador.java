package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Services.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ReportesControlador {

    @Autowired
    private ServicioPizzas servicioPizzas;

    @Autowired
    private ServicioCliente servicioCliente;

    @Autowired
    private ServicioIngredientes servicioIngredientes;

    @Autowired
    private ServicioPizzasFamiliares servicioPizzasFamiliares;

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @GetMapping("/excelpizzas")
    public void generarExcelReportePizzas(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pizzas.xls";

        response.setHeader(headerKey, headerValue);

        servicioPizzas.generarExcelPizzas(response);
    }

    @GetMapping("/excelclientes")
    public void generarExcelReporteClientes(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=clientes.xls";

        response.setHeader(headerKey, headerValue);

        servicioCliente.generarExcelClientes(response);
    }

    @GetMapping("/excelingredientes")
    public void generarExcelReporteIngredientes(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ingredientes.xls";

        response.setHeader(headerKey, headerValue);

        servicioIngredientes.generarExcelIngredientes(response);
    }

    @GetMapping("/excelpizzasfamiliares")
    public void generarExcelReportePizzasFamiliares(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pizzasfamiliares.xls";

        response.setHeader(headerKey, headerValue);

        servicioPizzasFamiliares.generarExcelPizzasFamiliares(response);
    }

    @GetMapping("/excelusuarios")
    public void generarExcelReporteUsuarios(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios.xls";

        response.setHeader(headerKey, headerValue);

        servicioUsuarios.generarExcelUsuarios(response);
    }
}