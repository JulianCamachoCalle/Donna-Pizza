package com.example.DonnaPizza.MVC.Entrada;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioEntrada {
    private final EntradaRepository entradaRepository;

    @Autowired
    public ServicioEntrada(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    // Crear una nueva entrada
    public Entrada crearEntrada(Entrada entrada) {
        return entradaRepository.save(entrada);
    }

    // Obtener una entrada por ID
    public Optional<Entrada> obtenerEntradaPorId(Long id) {
        return entradaRepository.findById(id);
    }

    // Obtener todas las entradas
    public List<Entrada> obtenerTodasLasEntradas() {
        return entradaRepository.findAll();
    }

    // Actualizar una entrada existente
    public Entrada actualizarEntrada(Long id, Entrada nuevaEntrada) {
        return entradaRepository.findById(id)
                .map(entrada -> {
                    entrada.setNombre(nuevaEntrada.getNombre());
                    entrada.setDescripcion(nuevaEntrada.getDescripcion());
                    entrada.setPrecio(nuevaEntrada.getPrecio());
                    entrada.setDisponible(nuevaEntrada.getDisponible());
                    return entradaRepository.save(entrada);
                })
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada"));
    }

    // Eliminar una entrada por ID
    public void eliminarEntrada(Long id) {
        if (entradaRepository.existsById(id)) {
            entradaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Entrada no encontrada");
        }
    }

    public void generarExcelEntrada(HttpServletResponse response) throws IOException {
        List<Entrada> entradas = entradaRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Entradas Info");

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
        titleCell.setCellValue("Info Entradas");
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
        String[] headers = {"ID_Entrada", "Nombre", "Descripcion", "Precio", "Disponible"};
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
        for (Entrada entrada : entradas) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(entrada.getId_entrada());
            dataRow.createCell(1).setCellValue(entrada.getNombre());
            dataRow.createCell(2).setCellValue(entrada.getDescripcion());
            dataRow.createCell(3).setCellValue(entrada.getPrecio());
            dataRow.createCell(4).setCellValue(entrada.getDisponible() ? "Disponible" : "No Disponible");


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