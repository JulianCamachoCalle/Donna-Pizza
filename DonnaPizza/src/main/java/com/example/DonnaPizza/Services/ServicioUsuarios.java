package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Usuarios;
import com.example.DonnaPizza.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuarios {

    // Link al Repository
    private final UsuariosRepository usuariosRepository;

    // Contrasena
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    HashMap<String, Object> datosUsuario;

    @Autowired
    public ServicioUsuarios(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    // Obtener Todos
    public List<Usuarios> getUsuarios() {
        return this.usuariosRepository.findAll();
    }

    // Obtener por ID
    public Optional<Usuarios> getUsuarioById(Long id) {
        return this.usuariosRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newUsuario(Usuarios usuarios) {
        datosUsuario = new HashMap<>();

        // Verificar Email Existente
        Optional<Usuarios> resEmail = usuariosRepository.findUsuariosByEmail(usuarios.getEmail());

        // Mensaje de error Email
        if (resEmail.isPresent()) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ya existe un usuario con ese email");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono 9 digitos
        String telefono = usuarios.getTelefono();
        if (telefono.length() != 9) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono empieza con 9
        if (!telefono.startsWith("9")) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Agregar prefijo al telefono
        if (!telefono.startsWith("+51")) {
            usuarios.setTelefono("+51 " + telefono);
        }

        // Verificar Telefono Existente
        Optional<Usuarios> resTel = usuariosRepository.findUsuariosByTelefono(usuarios.getTelefono());

        // Mensaje de error Telefono
        if (resTel.isPresent()) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ya existe un usuario con ese teléfono");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Formatear Contrasena
        usuarios.setContraseña(passwordEncoder.encode(usuarios.getContraseña()));

        // Guardar Con Exito
        datosUsuario.put("mensaje", "Se ha registrado el Usuario");
        usuariosRepository.save(usuarios);
        datosUsuario.put("data", usuarios);
        return new ResponseEntity<>(
                datosUsuario,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateUsuarios(Long id, Usuarios usuarios) {
        datosUsuario = new HashMap<>();

        // Buscar el usuario por ID
        Optional<Usuarios> usuarioExistente = usuariosRepository.findById(id);
        if (usuarioExistente.isEmpty()) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Usuario no encontrado");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el email ya está en uso por otro usuario
        Optional<Usuarios> resEmail = usuariosRepository.findUsuariosByEmail(usuarios.getEmail());
        if (resEmail.isPresent() && !resEmail.get().getId_usuario().equals(id)) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ya existe un usuario con ese email");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Validaciones adicionales
        String telefono = usuarios.getTelefono();
        if (telefono.length() != 9 || !telefono.startsWith("9")) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Prefijo de teléfono
        if (!telefono.startsWith("+51")) {
            usuarios.setTelefono("+51 " + telefono);
        }

        // Actualizar el cliente
        Usuarios usuariosActualizar = usuarioExistente.get();
        usuariosActualizar.setNombre(usuarios.getNombre());
        usuariosActualizar.setApellido(usuarios.getApellido());
        usuariosActualizar.setEmail(usuarios.getEmail());
        usuariosActualizar.setTelefono(usuarios.getTelefono());
        usuariosActualizar.setDireccion(usuarios.getDireccion());
        usuariosActualizar.setRol(usuarios.getRol());
        if (!usuarios.getContraseña().isEmpty()) {
            usuariosActualizar.setContraseña(passwordEncoder.encode(usuarios.getContraseña()));
        }

        usuariosRepository.save(usuariosActualizar);
        datosUsuario.put("mensaje", "Se actualizó el Usuario");
        datosUsuario.put("data", usuariosActualizar);

        return new ResponseEntity<>(
                datosUsuario,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deleteUsuarios(Long id) {
        datosUsuario = new HashMap<>();
        boolean existeCliente = this.usuariosRepository.existsById(id);
        if (!existeCliente) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "No existe un usuario con ese id");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }
        usuariosRepository.deleteById(id);
        datosUsuario.put("mensaje", "Usuario eliminado");
        return new ResponseEntity<>(
                datosUsuario,
                HttpStatus.ACCEPTED
        );
    }
}
