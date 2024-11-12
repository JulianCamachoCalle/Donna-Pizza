package com.example.DonnaPizza.MVC.Reportes;

import com.example.DonnaPizza.MVC.Cliente.ServicioCliente;
import com.example.DonnaPizza.MVC.Documentos.ServicioDocumentos;
import com.example.DonnaPizza.MVC.Ingredientes.ServicioIngredientes;
import com.example.DonnaPizza.MVC.Pizzas.ServicioPizzas;
import com.example.DonnaPizza.MVC.PizzasFamiliares.ServicioPizzasFamiliares;
import com.example.DonnaPizza.MVC.Promociones.ServicioPromociones;
import com.example.DonnaPizza.MVC.PromocionesUsuarios.ServicioPromocionesUsuarios;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
//Desarrollo de todos
@RestController
public class ReportesControlador {

    @Autowired
    private ServicioPizzas servicioPizzas;

    @Autowired
    private ServicioCliente servicioCliente;

    @Autowired
    private ServicioDocumentos servicioDocumentos;

    @Autowired
    private ServicioPromociones servicioPromociones;

    @Autowired
    ServicioPromocionesUsuarios servicioPromocionesUsuarios;

    @Autowired
    private ServicioIngredientes servicioIngredientes;

    @Autowired
    private ServicioPizzasFamiliares servicioPizzasFamiliares;

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

    @GetMapping("/exceldocumentos")
    public void generarExcelReportesDocumentos(HttpServletResponse response) throws IOException{

        response.setContentType("application/octet-stream");

        String headerKey="Content-Disposition";
        String headerValue="attachment; filename=documentos.xls";

        response.setHeader(headerKey,headerValue);

        servicioDocumentos.generarExcelDocumento(response);
    }

    @GetMapping("/excelpromociones")
    public void generarExcelReportesPromociones(HttpServletResponse response) throws IOException{

        response.setContentType("application/octet-stream");

        String headerKey= "Content-Disposition";
        String headerValue="attachment; filename=promociones.xls";

        response.setHeader(headerKey,headerValue);

        servicioPromociones.generarExcelPromociones(response);
    }

    @GetMapping("/excelpromocionesusuarios")
    public void generarExcelReportesPromocionesUsuarios(HttpServletResponse response) throws IOException{

        response.setContentType("application/octet-stream");

        String headerKey= "Content-Disposition";
        String headerValue="attachment; filename=promocionesUsuarios.xls";

        response.setHeader(headerKey,headerValue);

        servicioPromocionesUsuarios.generarExcelPromocionesUsuarios(response);
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
}
