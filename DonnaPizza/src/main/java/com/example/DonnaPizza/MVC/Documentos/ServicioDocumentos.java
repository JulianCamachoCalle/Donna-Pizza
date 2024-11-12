package com.example.DonnaPizza.MVC.Documentos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServicioDocumentos {

    private static final Logger logger = LoggerFactory.getLogger(ServicioDocumentos.class);

    // Link al Repository
    private final DocumentosRepository documentosRepository;

    HashMap<String, Object> datosDocumentos;

    @Autowired
    public ServicioDocumentos(DocumentosRepository documentosRepository) {
        this.documentosRepository = documentosRepository;
    }

    // Obtener Todos
    public List<Documentos> getDocumentos() {
        return this.documentosRepository.findAll();
    }

    // Obtener por ID
    public Optional<Documentos> getDocumentosById(Long id) {
        return documentosRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newDocumento(Documentos documentos) {
        datosDocumentos = new HashMap<>();


        // Verificar tipoDocumento Existente
        Optional<Documentos> resTel = documentosRepository.findDocumentosByTipoDocumento(documentos.getTipoDocumento());

        // Mensaje de error tipoDocumento
        if (resTel.isEmpty()) {
            datosDocumentos.put("error", true);
            datosDocumentos.put("mensaje", "El tipo de documento no puede ser nulo");
            return new ResponseEntity<>(
                    datosDocumentos,
                    HttpStatus.CONFLICT
            );
        }

        // Mensaje de error tipoDocumento
        if (resTel.isPresent()) {
            datosDocumentos.put("error", true);
            datosDocumentos.put("mensaje", "Ya existe un documento con ese tipo de documento");
            return new ResponseEntity<>(datosDocumentos, HttpStatus.CONFLICT);
        }

        // Guardar Con Exito
        datosDocumentos.put("mensaje", "Se ha registrado el tipo de documento");
        documentosRepository.save(documentos);
        datosDocumentos.put("data", documentos);
        return new ResponseEntity<>(
                datosDocumentos,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateDocumentos(Long id, Documentos documentos) {
        datosDocumentos = new HashMap<>();

        // Buscar el cliente por ID
        Optional<Documentos> documentoExistente = documentosRepository.findById(id);
        if (documentoExistente.isEmpty()) {
            datosDocumentos.put("error", true);
            datosDocumentos.put("mensaje", "Documento no encontrado");
            return new ResponseEntity<>(
                    datosDocumentos,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el tipo de documento ya está en uso
        Optional<Documentos> resEmail = documentosRepository.findDocumentosByTipoDocumento(documentos.getTipoDocumento());
        if (resEmail.isPresent() && !resEmail.get().getIdDocumento().equals(id)) {
            datosDocumentos.put("error", true);
            datosDocumentos.put("mensaje", "Ya existe un documento con ese tipo");
            return new ResponseEntity<>(
                    datosDocumentos,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar documento
        Documentos documentoActualizar = documentoExistente.get();
        documentoActualizar.setTipoDocumento(documentos.getTipoDocumento());

        documentosRepository.save(documentoActualizar);
        datosDocumentos.put("mensaje", "Se actualizó el documento");
        datosDocumentos.put("data", documentoActualizar);

        return new ResponseEntity<>(
                datosDocumentos,
                HttpStatus.OK
        );
    }


    // Eliminar
    public ResponseEntity<Object> deleteDocumento(Long id) {
        datosDocumentos = new HashMap<>();
        boolean existeDocumento = this.documentosRepository.existsById(id);
        if (!existeDocumento) {
            datosDocumentos.put("error", true);
            datosDocumentos.put("mensaje", "No existe un documento con ese id");
            return new ResponseEntity<>(
                    datosDocumentos,
                    HttpStatus.CONFLICT
            );
        }
        documentosRepository.deleteById(id);
        datosDocumentos.put("mensaje", "Cliente eliminado");
        return new ResponseEntity<>(
                datosDocumentos,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelDocumento(HttpServletResponse response) throws IOException {
        List<Documentos> documentos = documentosRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Documentos Info");

        // Estilo del título
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        HSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 22);
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        // Crear fila del título y fusionar celdas
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Info Documentos");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // Reemplazar el 5 segun la cantidad de headers - 1

        // Estilo del encabezado
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Crear fila de encabezado
        HSSFRow row = sheet.createRow(1);
        String[] headers = {"ID_Documento", "Tipo_Documento"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Estilo de los datos
        HSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        // Llenar datos
        int dataRowIndex = 2;
        for (Documentos documento : documentos) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(documento.getIdDocumento());
            dataRow.createCell(1).setCellValue(documento.getTipoDocumento());

            // Aplicar estilo de datos a cada celda
            for (int i = 0; i < headers.length; i++) {
                dataRow.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Ajustar ancho de las columnas después de llenar los datos
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Enviar el archivo al cliente
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}