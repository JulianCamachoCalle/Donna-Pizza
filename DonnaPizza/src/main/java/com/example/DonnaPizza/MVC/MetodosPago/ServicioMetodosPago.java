package com.example.DonnaPizza.MVC.MetodosPago;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
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

@Service
public class ServicioMetodosPago {

    // Link al Repository
    private final MetodosPagoRepository metodosPagoRepository;

    HashMap<String, Object> datosMetodosPago;

    @Autowired
    public ServicioMetodosPago(MetodosPagoRepository metodosPagoRepository) {
        this.metodosPagoRepository = metodosPagoRepository;
    }

    // Obtener Todos
    public List<MetodosPago> getMetodosPago() {
        return metodosPagoRepository.findAll();
    }

    // Obtener por ID
    public Optional<MetodosPago> getMetodosPagoById(Long id) {
        return metodosPagoRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newMetodosPago(MetodosPago metodosPago) {
        datosMetodosPago = new HashMap<>();

        // Verificar Nombre Existente
        Optional<MetodosPago> resNom = metodosPagoRepository.findByNombre(metodosPago.getNombre());

        // Mnesaje de error Nombre
        if (resNom.isPresent()) {
            datosMetodosPago.put("error", true);
            datosMetodosPago.put("mensaje", "Ya existe un metodo de pago con ese nombre");
            return new ResponseEntity<>(
                    datosMetodosPago,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosMetodosPago.put("mensaje", "Se ha registrado el metodo de pago");
        metodosPagoRepository.save(metodosPago);
        datosMetodosPago.put("data", metodosPago);
        return new ResponseEntity<>(
                datosMetodosPago,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateMetodosPago(Long id, MetodosPago metodosPago) {
        datosMetodosPago = new HashMap<>();

        // Buscar metodo de pago por ID
        Optional<MetodosPago> metodosPagoExistente = metodosPagoRepository.findById(id);
        if (metodosPagoExistente.isEmpty()) {
            datosMetodosPago.put("error", true);
            datosMetodosPago.put("mensaje", "Metodo de pago no encontrado");
            return new ResponseEntity<>(
                    datosMetodosPago,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya está usado
        Optional<MetodosPago> resNom = metodosPagoRepository.findByNombre(metodosPago.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_metodo_pago().equals(id)) {
            datosMetodosPago.put("error", true);
            datosMetodosPago.put("mensaje", "Ya existe un metodo de pago con ese nombre");
            return new ResponseEntity<>(
                    datosMetodosPago,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar metodo de pago
        MetodosPago metodosPagoActualizar = metodosPagoExistente.get();
        metodosPagoActualizar.setNombre(metodosPago.getNombre());
        metodosPagoActualizar.setDescripcion(metodosPago.getDescripcion());

        metodosPagoRepository.save(metodosPagoActualizar);
        datosMetodosPago.put("mensaje", "Se actualizó el metodo de pago");
        datosMetodosPago.put("data", metodosPagoActualizar);

        return new ResponseEntity<>(
                datosMetodosPago,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deleteMetodosPago(Long id) {
        datosMetodosPago = new HashMap<>();
        boolean existeMetodosPago = metodosPagoRepository.existsById(id);
        if (!existeMetodosPago) {
            datosMetodosPago.put("error", true);
            datosMetodosPago.put("mensaje", "No existe un metodo de pago con ese id");
            return new ResponseEntity<>(
                    datosMetodosPago,
                    HttpStatus.CONFLICT
            );
        }
        metodosPagoRepository.deleteById(id);
        datosMetodosPago.put("mensaje", "metodo de pago eliminado");
        return new ResponseEntity<>(
                datosMetodosPago,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelMetodosPago(HttpServletResponse response) throws IOException {
        List<MetodosPago> metodosPago = metodosPagoRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("MetodosPago Info");

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
        titleCell.setCellValue("Info MetodosPago");
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
        String[] headers = {"ID_Metodos de Pago", "Nombre", "Descripcion"};
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
        for (MetodosPago metodoPago : metodosPago) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(metodoPago.getId_metodo_pago());
            dataRow.createCell(1).setCellValue(metodoPago.getNombre());
            dataRow.createCell(2).setCellValue(metodoPago.getDescripcion());

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


