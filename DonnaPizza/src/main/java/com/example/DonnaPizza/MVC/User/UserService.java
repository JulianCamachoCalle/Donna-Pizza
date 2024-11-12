package com.example.DonnaPizza.MVC.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
