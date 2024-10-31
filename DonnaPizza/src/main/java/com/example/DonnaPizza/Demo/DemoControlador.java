package com.example.DonnaPizza.Demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class DemoControlador {

    @PostMapping(value = "demo")
    public String welcome() {
        return "Welcome to Donna Pizza";
    }
}
