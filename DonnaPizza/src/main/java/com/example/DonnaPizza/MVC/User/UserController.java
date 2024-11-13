package com.example.DonnaPizza.MVC.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    private final UserService userService;

    // Obtener por ID
    @GetMapping(value = "{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    // Registrar
    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    // Actualizar
    @PutMapping()
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
}
