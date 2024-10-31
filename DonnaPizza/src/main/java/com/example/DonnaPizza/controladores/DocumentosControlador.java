package com.example.DonnaPizza.controladores;

import com.example.DonnaPizza.Model.Documentos;
import com.example.DonnaPizza.Services.ServicioDocumentos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/documentos")
public class DocumentosControlador {

    // Link al Servicio
    private final ServicioDocumentos servicioDocumentos;

    @Autowired
    public DocumentosControlador(ServicioDocumentos servicioDocumentos) {
        this.servicioDocumentos = servicioDocumentos;
    }

    // Obtener Todos
    @GetMapping
    public List<Documentos> getDocumentos() {
        return this.servicioDocumentos.getDocumentos();
    }

    // Obtener por Id
    @GetMapping("{documentoId}")
    public ResponseEntity<Documentos> getCliente(@PathVariable("documentosId") Long id) {
        ResponseEntity<Object> response = this.servicioDocumentos.getDocumentoById(id);

        if (response.getStatusCode().is2xxSuccessful()) {
            Documentos documento = (Documentos) ((HashMap<String, Object>) response.getBody()).get("data");
            return ResponseEntity.ok(documento); // Retorna el documento encontrado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarCliente(@RequestBody Documentos documentos) {
        return this.servicioDocumentos.newDocumento(documentos); // Cambia Cliente a Documentos si corresponde
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarCliente(@PathVariable Long id, @RequestBody Documentos documentos) {
        return this.servicioDocumentos.updateDocumento(id, documentos); // Cambia Cliente a Documentos si corresponde
    }

    // Eliminar
    @DeleteMapping(path = "{documentosId}")
    public ResponseEntity<Object> eliminarDocumentos(@PathVariable("documentosId") Long id) {
        return this.servicioDocumentos.deleteDocumento(id);
    }
}
