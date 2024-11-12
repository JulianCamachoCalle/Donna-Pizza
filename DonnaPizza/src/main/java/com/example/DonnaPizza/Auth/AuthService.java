package com.example.DonnaPizza.Auth;

import com.example.DonnaPizza.Jwt.JwtService;
import com.example.DonnaPizza.MVC.User.Role;
import com.example.DonnaPizza.MVC.User.User;
import com.example.DonnaPizza.MVC.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Expresión regular para validar solo letras (sin símbolos ni números)
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    // Expresión regular para validar el formato del correo electrónico
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    // Expresión regular para validar el teléfono (9 dígitos, solo números, y empieza con 9)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^9\\d{8}$");

    // Expresión regular para validar la contraseña (mínimo 8 caracteres, una mayúscula y un carácter especial)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$");

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Validación de campos vacíos
        if (!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getPassword()) ||
                !StringUtils.hasText(request.getNombre()) || !StringUtils.hasText(request.getApellido()) ||
                !StringUtils.hasText(request.getTelefono()) || !StringUtils.hasText(request.getDireccion())) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        // Validación de nombre (solo letras)
        if (!NAME_PATTERN.matcher(request.getNombre()).matches()) {
            throw new IllegalArgumentException("El nombre solo puede contener letras");
        }

        // Validación de apellido (solo letras)
        if (!NAME_PATTERN.matcher(request.getApellido()).matches()) {
            throw new IllegalArgumentException("El apellido solo puede contener letras");
        }

        // Validación de formato de correo electrónico o username
        if (!EMAIL_PATTERN.matcher(request.getUsername()).matches()) {
            throw new IllegalArgumentException("El correo electrónico o username no es válido");
        }

        // Validación de que el nombre de usuario no está en uso
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico o username ya está en uso");
        }

        // Validación de teléfono (debe ser un número que empieza con 9 y tenga 9 caracteres)
        if (!PHONE_PATTERN.matcher(request.getTelefono()).matches()) {
            throw new IllegalArgumentException("El teléfono debe tener 9 caracteres, ser solo números y empezar con 9");
        }

        // Validación de que el teléfono no está en uso
        if (userRepository.findByTelefono(request.getTelefono()).isPresent()) {
            throw new IllegalArgumentException("El número de teléfono ya está en uso");
        }

        // Validación de la contraseña (mínimo 8 caracteres, 1 mayúscula, 1 carácter especial)
        if (!PASSWORD_PATTERN.matcher(request.getPassword()).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, una letra mayúscula y un carácter especial");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .fecha_registro(LocalDate.now())
                .rol(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
