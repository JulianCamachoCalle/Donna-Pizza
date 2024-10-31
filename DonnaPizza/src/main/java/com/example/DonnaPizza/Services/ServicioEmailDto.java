package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.EmailDto;
import com.example.DonnaPizza.Repository.EmailDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioEmailDto {

    // Inyección del repositorio
    private final EmailDtoRepository emailDtoRepository;
    private HashMap<String, Object> datosEmail;

    @Autowired
    public ServicioEmailDto(EmailDtoRepository emailDtoRepository) {
        this.emailDtoRepository = emailDtoRepository;
    }

    // **Obtener todos los Emails**
    public List<EmailDto> getEmails() {
        return emailDtoRepository.findAll();
    }

    // **Obtener Email por ID**
    public ResponseEntity<Object> getEmailById(Long id) {
        datosEmail = new HashMap<>();
        Optional<EmailDto> email = emailDtoRepository.findById(id);

        if (email.isEmpty()) {
            datosEmail.put("error", true);
            datosEmail.put("mensaje", "Email no encontrado");
            return new ResponseEntity<>(datosEmail, HttpStatus.NOT_FOUND);
        }

        datosEmail.put("data", email.get());
        return new ResponseEntity<>(datosEmail, HttpStatus.OK);
    }

    // **Crear un nuevo Email**
    public ResponseEntity<Object> newEmail(EmailDto emailDto) {
        datosEmail = new HashMap<>();

        // Validar que los campos no estén vacíos
        if (emailDto.getEnviando() == null || emailDto.getEnviando().isEmpty() ||
            emailDto.getReceptor() == null || emailDto.getReceptor().isEmpty()) {
            datosEmail.put("error", true);
            datosEmail.put("mensaje", "El campo 'enviando' y 'receptor' son obligatorios");
            return new ResponseEntity<>(datosEmail, HttpStatus.BAD_REQUEST);
        }

        // Guardar el email
        emailDtoRepository.save(emailDto);
        datosEmail.put("mensaje", "Email registrado con éxito");
        datosEmail.put("data", emailDto);
        return new ResponseEntity<>(datosEmail, HttpStatus.CREATED);
    }

    // **Actualizar un Email**
    public ResponseEntity<Object> updateEmail(Long id, EmailDto emailActualizado) {
        datosEmail = new HashMap<>();

        // Buscar el email por ID
        Optional<EmailDto> emailExistente = emailDtoRepository.findById(id);

        if (emailExistente.isEmpty()) {
            datosEmail.put("error", true);
            datosEmail.put("mensaje", "Email no encontrado");
            return new ResponseEntity<>(datosEmail, HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del email existente
        EmailDto email = emailExistente.get();
        email.setEnviando(emailActualizado.getEnviando());
        email.setMensaje(emailActualizado.getMensaje());
        email.setReceptor(emailActualizado.getReceptor());

        emailDtoRepository.save(email);
        datosEmail.put("mensaje", "Email actualizado con éxito");
        datosEmail.put("data", email);

        return new ResponseEntity<>(datosEmail, HttpStatus.OK);
    }

    // **Eliminar un Email**
    public ResponseEntity<Object> deleteEmail(Long id) {
        datosEmail = new HashMap<>();

        // Verificar si existe el email por ID
        boolean existe = emailDtoRepository.existsById(id);
        if (!existe) {
            datosEmail.put("error", true);
            datosEmail.put("mensaje", "No existe un email con ese ID");
            return new ResponseEntity<>(datosEmail, HttpStatus.NOT_FOUND);
        }

        // Eliminar el email
        emailDtoRepository.deleteById(id);
        datosEmail.put("mensaje", "Email eliminado con éxito");

        return new ResponseEntity<>(datosEmail, HttpStatus.ACCEPTED);
    }
}
