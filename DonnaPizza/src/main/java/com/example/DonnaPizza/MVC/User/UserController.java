package com.example.DonnaPizza.MVC.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Obtener Segun el ID
    @GetMapping(value = "{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Registrar Nuevo
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

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
