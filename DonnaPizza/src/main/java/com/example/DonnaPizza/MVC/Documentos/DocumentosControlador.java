package com.example.DonnaPizza.MVC.Documentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/documentos")
public class DocumentosControlador {


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
    public ResponseEntity<Documentos> getDocumnetos(@PathVariable("documentoId") Long id) {
        Optional<Documentos> documentos = this.servicioDocumentos.getDocumentosById(id);
        if (documentos.isPresent()) {
            return ResponseEntity.ok(documentos.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar Nuevo
    @PostMapping
    public ResponseEntity<Object> registrarDocumento(@RequestBody Documentos documentos) {
        return this.servicioDocumentos.newDocumento(documentos);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarDocumento(@PathVariable Long id, @RequestBody Documentos documentos) {
        return this.servicioDocumentos.updateDocumentos(id,documentos);
    }

    // Eliminar
    @DeleteMapping(path = "{documentoId}")
    public ResponseEntity<Object> eliminarDocumento(@PathVariable("documentoId") Long id) {
        return this.servicioDocumentos.deleteDocumento(id);
    }
}