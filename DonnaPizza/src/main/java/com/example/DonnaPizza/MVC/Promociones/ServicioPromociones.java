package com.example.DonnaPizza.MVC.Promociones;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//Desarrollo de anthony
@Service
public class ServicioPromociones {

    private static final Logger logger = LoggerFactory.getLogger(ServicioPromociones.class);

    // Link al Repository
    private final PromocionesRepository promocionesRepository;

    HashMap<String, Object> datosPromociones;

    @Autowired
    public ServicioPromociones(PromocionesRepository promocionesRepository) {
        this.promocionesRepository = promocionesRepository;
    }

    // Obtener Todos
    public List<Promociones> getPromociones() {
        return promocionesRepository.findAll();
    }

    // Obtener por ID
    public Optional<Promociones> getPromocionesById(Long id) {
        return promocionesRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPromociones(Promociones promociones) {
        datosPromociones = new HashMap<>();

        // Verificar Nombre Existente
        Optional<Promociones> proNom = promocionesRepository.findPromocionesByNombre(promociones.getNombre());

        // Mnesaje de error Nombre
        //yif (proNom.isEmpty()) {
        //y    datosPromociones.put("error", true);
        //y    datosPromociones.put("mensaje", "El nombre no puede ser nulo");
        //y    return new ResponseEntity<>(
        //y            datosPromociones,
        //y            HttpStatus.CONFLICT
        //y    );
        //y}

        if (proNom.isPresent()) {
            datosPromociones.put("error", true);
            datosPromociones.put("mensaje", "Ya existe una promocion con ese nombre");
            return new ResponseEntity<>(
                    datosPromociones,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosPromociones.put("mensaje", "Se ha registrado la promocion");
        promocionesRepository.save(promociones);
        datosPromociones.put("data", promociones);
        return new ResponseEntity<>(
                datosPromociones,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePromociones(Long id, Promociones promociones) {
        datosPromociones = new HashMap<>();

        // Buscar promocion por ID
        Optional<Promociones> promocionExistente = promocionesRepository.findById(id);
        if (promocionExistente.isEmpty()) {
            datosPromociones.put("error", true);
            datosPromociones.put("mensaje", "Promocion no encontrada");
            return new ResponseEntity<>(
                    datosPromociones,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya está usado
        Optional<Promociones> proNom = promocionesRepository.findPromocionesByNombre(promociones.getNombre());
        if (proNom.isPresent() && !proNom.get().getId_promocion().equals(id)) {
            datosPromociones.put("error", true);
            datosPromociones.put("mensaje", "Ya existe una promocion con ese nombre");
            return new ResponseEntity<>(
                    datosPromociones,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar promocion
        Promociones promocionActualizar = promocionExistente.get();
        promocionActualizar.setId_promocion(promociones.getId_promocion());
        promocionActualizar.setNombre(promociones.getNombre());
        promocionActualizar.setDescripcion(promociones.getDescripcion());
        promocionActualizar.setDescuento(promociones.getDescuento());
        promocionActualizar.setRequisitos(promociones.getRequisitos());
        promocionActualizar.setActivo(promociones.getActivo());


        promocionesRepository.save(promocionActualizar);
        datosPromociones.put("mensaje", "Se actualizó promocion");
        datosPromociones.put("data", promocionActualizar);

        return new ResponseEntity<>(
                datosPromociones,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePromociones(Long id) {
        datosPromociones = new HashMap<>();
        boolean existePromocion = promocionesRepository.existsById(id);
        if (!existePromocion) {
            datosPromociones.put("error", true);
            datosPromociones.put("mensaje", "No existe promocion con ese id");
            return new ResponseEntity<>(
                    datosPromociones,
                    HttpStatus.CONFLICT
            );
        }
        promocionesRepository.deleteById(id);
        datosPromociones.put("mensaje", "promocion eliminado");
        return new ResponseEntity<>(
                datosPromociones,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelPromociones(HttpServletResponse response) throws IOException {
        List<Promociones> promociones = promocionesRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Promociones Info");

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
        titleCell.setCellValue("Info Promociones");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); // Reemplazar el 2 segun la cantidad de headers - 1

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
        String[] headers = {"ID_promocion", "Nombre", "Descripcion" , "Descuento", "Requisito" , "Activo"};
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
        for (Promociones promocion : promociones) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(promocion.getId_promocion());
            dataRow.createCell(1).setCellValue(promocion.getNombre());
            dataRow.createCell(2).setCellValue(promocion.getDescripcion());
            dataRow.createCell(3).setCellValue(promocion.getDescuento());
            dataRow.createCell(4).setCellValue(promocion.getRequisitos());
            dataRow.createCell(5).setCellValue(promocion.getActivo()? "Si" : "No ");



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


