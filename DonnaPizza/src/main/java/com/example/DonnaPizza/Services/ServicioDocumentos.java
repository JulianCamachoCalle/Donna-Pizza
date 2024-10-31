package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Documentos;
import com.example.DonnaPizza.Repository.DocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioDocumentos {

    // Inyección del repositorio
    private final DocumentosRepository documentosRepository;
    private HashMap<String, Object> datosDocumento;

    @Autowired
    public ServicioDocumentos(DocumentosRepository documentosRepository) {
        this.documentosRepository = documentosRepository;
    }

    // **Obtener todos los Documentos**
    public List<Documentos> getDocumentos() {
        return documentosRepository.findAll();
    }

    // **Obtener Documento por ID**
    public ResponseEntity<Object> getDocumentoById(Long id) {
        datosDocumento = new HashMap<>();
        Optional<Documentos> documento = documentosRepository.findById(id);

        if (documento.isEmpty()) {
            datosDocumento.put("error", true);
            datosDocumento.put("mensaje", "Documento no encontrado");
            return new ResponseEntity<>(datosDocumento, HttpStatus.NOT_FOUND);
        }

        datosDocumento.put("data", documento.get());
        return new ResponseEntity<>(datosDocumento, HttpStatus.OK);
    }

    // **Crear un nuevo Documento**
    public ResponseEntity<Object> newDocumento(Documentos documento) {
        datosDocumento = new HashMap<>();

        // Validar que el tipo de documento no esté vacío
        if (documento.getIdDocumento() == null || documento.getTipoDocumento().isEmpty()) {
            datosDocumento.put("error", true);
            datosDocumento.put("mensaje", "El tipo de documento no puede estar vacío");
            return new ResponseEntity<>(datosDocumento, HttpStatus.BAD_REQUEST);
        }

        // Guardar el documento
        documentosRepository.save(documento);
        datosDocumento.put("mensaje", "Documento registrado con éxito");
        datosDocumento.put("data", documento);
        return new ResponseEntity<>(datosDocumento, HttpStatus.CREATED);
    }

    // **Actualizar un Documento**
    public ResponseEntity<Object> updateDocumento(Long id, Documentos documentoActualizado) {
        datosDocumento = new HashMap<>();

        // Buscar el documento por ID
        Optional<Documentos> documentoExistente = documentosRepository.findById(id);

        if (documentoExistente.isEmpty()) {
            datosDocumento.put("error", true);
            datosDocumento.put("mensaje", "Documento no encontrado");
            return new ResponseEntity<>(datosDocumento, HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del documento existente
        Documentos documento = documentoExistente.get();
        documento.setTipoDocumento(documentoActualizado.getTipoDocumento());

        documentosRepository.save(documento);
        datosDocumento.put("mensaje", "Documento actualizado con éxito");
        datosDocumento.put("data", documento);

        return new ResponseEntity<>(datosDocumento, HttpStatus.OK);
    }

    // **Eliminar un Documento**
    public ResponseEntity<Object> deleteDocumento(Long id) {
        datosDocumento = new HashMap<>();

        // Verificar si existe el documento por ID
        boolean existe = documentosRepository.existsById(id);
        if (!existe) {
            datosDocumento.put("error", true);
            datosDocumento.put("mensaje", "No existe un documento con ese ID");
            return new ResponseEntity<>(datosDocumento, HttpStatus.NOT_FOUND);
        }

        // Eliminar el documento
        documentosRepository.deleteById(id);
        datosDocumento.put("mensaje", "Documento eliminado con éxito");

        return new ResponseEntity<>(datosDocumento, HttpStatus.ACCEPTED);
    }
}
