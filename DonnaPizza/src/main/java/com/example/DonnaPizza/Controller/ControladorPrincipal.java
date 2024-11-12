package com.example.DonnaPizza.Controller;

import com.example.DonnaPizza.MVC.User.User;
import com.example.DonnaPizza.MVC.User.UserDTO;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        return "access-denied";
    }

    @GetMapping("/menuUsuario")
    public String menuUsuario(Model model) {
        return "menuUsuario";
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

    @GetMapping("/inicioSesion")
    public ModelAndView login() {
        // Obtén la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica si el usuario ya está autenticado
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            // Si está autenticado, redirige al dashboard
            return new ModelAndView("redirect:/dashboard");
        }

        // Si no está autenticado, muestra la página de inicio de sesión
        return new ModelAndView("inicioSesion");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getUserDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User user = (User) principal;

            UserDTO userDTO = new UserDTO();
            userDTO.setNombre(user.getNombre());
            userDTO.setApellido(user.getApellido());
            userDTO.setUsername(user.getUsername());
            userDTO.setDireccion(user.getDireccion());
            userDTO.setTelefono(user.getTelefono());

            return ResponseEntity.ok(userDTO);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
    }

}
