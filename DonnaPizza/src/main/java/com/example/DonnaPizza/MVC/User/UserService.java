package com.example.DonnaPizza.MVC.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Actualizar
    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .nombre(userRequest.getNombre())
                .apellido(userRequest.getApellido())
                .telefono(userRequest.getTelefono())
                .direccion(userRequest.getDireccion())
                .fecha_registro(userRequest.getFecha_registro())
                .rol(Role.USER)
                .build();

        userRepository.updateUser(user.id_usuario, user.username, user.password, user.nombre, user.apellido, user.telefono, user.direccion, user.fecha_registro);

        return new UserResponse("El usuario se registro exitosamente!");
    }

    // Obtener Segun el ID
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            UserDTO userDTO = UserDTO.builder()
                    .id_usuario(user.id_usuario)
                    .username(user.username)
                    .password(user.password)
                    .nombre(user.nombre)
                    .apellido(user.apellido)
                    .telefono(user.telefono)
                    .direccion(user.direccion)
                    .fecha_registro(user.fecha_registro)
                    .build();
            return userDTO;
        }
        return null;
    }

    // Obtener todos
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserDTO.builder()
                        .id_usuario(user.getId_usuario())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .nombre(user.getNombre())
                        .apellido(user.getApellido())
                        .telefono(user.getTelefono())
                        .direccion(user.getDireccion())
                        .fecha_registro(user.getFecha_registro())
                        .build())
                .collect(Collectors.toList());
    }

    // Eliminar
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    // Registrar Nuevo
    public UserResponse registerUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .nombre(userRequest.getNombre())
                .apellido(userRequest.getApellido())
                .telefono(userRequest.getTelefono())
                .direccion(userRequest.getDireccion())
                .fecha_registro(userRequest.getFecha_registro())
                .rol(Role.USER)
                .build();

        userRepository.save(user);
        return new UserResponse("El usuario se registro exitosamente!");
    }
}
