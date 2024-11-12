package com.example.DonnaPizza.Controller;

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
