package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.EmailDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enviarEmail")
public class EmailControlador {

    @GetMapping
    public String openEmailServicePage(Model model) {
        model.addAttribute("email", new EmailDto());
        return "EnviarEmail";
    }

    @PostMapping
    public String sendEmailMessage(@ModelAttribute EmailDto emailDto) {
        System.out.println(emailDto.toString());
        return "redirect:/enviarEmail";
    }
}
