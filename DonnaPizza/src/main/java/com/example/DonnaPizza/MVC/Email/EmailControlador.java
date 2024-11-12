package com.example.DonnaPizza.MVC.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enviarEmail")
public class EmailControlador {

    @Autowired
    private EmailSenderUtil emailSenderUtil;

    @GetMapping
    public String openEmailServicePage(Model model) {
        model.addAttribute("emailDto", new EmailDto());
        return "EnviarEmail";
    }

    @PostMapping
    public String sendEmailMessage(@ModelAttribute EmailDto emailDto, RedirectAttributes redirectAttributes) {
        String message = emailSenderUtil.sendEmail(emailDto);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/enviarEmail";
    }
}
