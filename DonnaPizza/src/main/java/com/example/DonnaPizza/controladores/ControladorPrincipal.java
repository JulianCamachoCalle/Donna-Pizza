package com.example.DonnaPizza.controladores;



import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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
    

    

    @GetMapping({"/contacto"})
    public String contacto(Model model) {
        return "contacto";
    }
   @RequestMapping("/dataformcontacto")
    public String dataformcontacto(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("telefono") String telefono,
            Model model){
        
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("telefono", telefono);
        return "listacontactos";
    }

    @GetMapping("/carta")
    public String carta(Model model) {
        return "carta";
    }

    @GetMapping("/membresia")
    public String membresia(Model model) {
        return "membresia";
    }

    @GetMapping("/locales")
    public String locales(Model model) {
        return "locales";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    //registro
    @RequestMapping("/dataFormRegistro")
    public String dataFormRegistro(@RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correo") String correo,
            @RequestParam("numero") String numero,
            @RequestParam("cumpleanos") LocalDate cumpleanos,
            @RequestParam("contrasena") String contrasena,
            Model model
    ) {
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellidos", apellidos);
        model.addAttribute("correo", correo);
        model.addAttribute("numero", numero);
        model.addAttribute("cumpleanos", cumpleanos);
        model.addAttribute("contrasena", contrasena);
        return "usuario";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        request.getSession().setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", new Locale(lang));
        return "redirect:" + request.getHeader("Referer");
    }

   

    @GetMapping("/sugerencias")
    public String sugerencias(Model model) {
        return "sugerencias";
    }
    @RequestMapping("/dataformsugerencia")
    public String dataformsugerencia(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("descripcion") String descripcion,
            Model model){
        
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("descripcion", descripcion);
        return "listasugerencias";
    }
    

    @GetMapping("/reclamaciones")
    public String reclamaciones(Model model) {
        return "reclamaciones";
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
    
    @GetMapping("/especificaciones")
    public String especificaciones(Model model) {
        return "especificaciones";
    }
    
}
