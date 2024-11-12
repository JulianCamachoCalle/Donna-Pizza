package com.example.DonnaPizza.MVC.PizzasFamiliares;

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
public class ServicioPizzasFamiliares {

    // Link al Repository
    private final PizzasFamiliaresRepository pizzasFamiliaresRepository;

    // Datos para respuestas
    private final HashMap<String, Object> datosPizzasFamiliares = new HashMap<>();

    @Autowired
    public ServicioPizzasFamiliares(PizzasFamiliaresRepository pizzasFamiliaresRepository) {
        this.pizzasFamiliaresRepository = pizzasFamiliaresRepository;
    }

    // Obtener todas las pizzas familiares
    public List<PizzasFamiliares> getPizzasFamiliares() {
        return this.pizzasFamiliaresRepository.findAll();
    }

    // Obtener pizza familiar por ID
    public Optional<PizzasFamiliares> getPizzaFamiliarById(Long id) {
        return this.pizzasFamiliaresRepository.findById(id);
    }

    // Crear nuevo
    public ResponseEntity<Object> newPizzaFamiliar(PizzasFamiliares pizzaFamiliar) {
        // Verificar nombre existente
        Optional<PizzasFamiliares> resNom = pizzasFamiliaresRepository.findPizzaFamiliarByNombre(pizzaFamiliar.getNombre());

        if (resNom.isPresent()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un producto con ese nombre");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.CONFLICT);
        }

        // Guardar con éxito
        pizzasFamiliaresRepository.save(pizzaFamiliar);
        datosPizzasFamiliares.put("mensaje", "Se ha registrado el producto");
        datosPizzasFamiliares.put("data", pizzaFamiliar);
        return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.CREATED);
    }

    // Actualizar
    public ResponseEntity<Object> updatePizzaFamiliar(Long id, PizzasFamiliares pizzaFamiliar) {
        // Buscar la pizza por ID
        Optional<PizzasFamiliares> pizzaFamiliarExistente = pizzasFamiliaresRepository.findById(id);
        if (pizzaFamiliarExistente.isEmpty()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.NOT_FOUND);
        }

        // Verificar si el nombre ya está en uso
        Optional<PizzasFamiliares> resNom = pizzasFamiliaresRepository.findPizzaFamiliarByNombre(pizzaFamiliar.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_pizza_familiar().equals(id)) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un producto con ese nombre");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.CONFLICT);
        }

        // Actualizar pizza
        PizzasFamiliares pizzaFamiliaresActualizar = pizzaFamiliarExistente.get();
        pizzaFamiliaresActualizar.setNombre(pizzaFamiliar.getNombre());
        pizzaFamiliaresActualizar.setDescripcion(pizzaFamiliar.getDescripcion());
        pizzaFamiliaresActualizar.setPrecio(pizzaFamiliar.getPrecio());
        pizzaFamiliaresActualizar.setDisponible(pizzaFamiliar.getDisponible());

        pizzasFamiliaresRepository.save(pizzaFamiliaresActualizar);
        datosPizzasFamiliares.put("mensaje", "Se actualizó el producto");
        datosPizzasFamiliares.put("data", pizzaFamiliaresActualizar);

        return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.OK);
    }

    // Eliminar
    public ResponseEntity<Object> deletePizzaFamiliar(Long id) {
        if (!pizzasFamiliaresRepository.existsById(id)) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "No existe un producto con ese id");
            return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.NOT_FOUND);
        }

        pizzasFamiliaresRepository.deleteById(id);
        datosPizzasFamiliares.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(datosPizzasFamiliares, HttpStatus.OK);
    }

    // Generar Excel
    public void generarExcelPizzasFamiliares(HttpServletResponse response) throws IOException {
        List<PizzasFamiliares> pizzasFamiliares = pizzasFamiliaresRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pizzas Familiares Info");

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
        titleCell.setCellValue("Info Pizzas Familiares");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4)); // Reemplazar el 4 segun la cantidad de headers - 1

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
        String[] headers = {"ID_Pizza", "Nombre", "Descripcion", "Precio", "Disponible"};
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
        for (PizzasFamiliares pizzafamiliar : pizzasFamiliares) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(pizzafamiliar.getId_pizza_familiar());
            dataRow.createCell(1).setCellValue(pizzafamiliar.getNombre());
            dataRow.createCell(2).setCellValue(pizzafamiliar.getDescripcion());
            dataRow.createCell(3).setCellValue(pizzafamiliar.getPrecio());
            dataRow.createCell(4).setCellValue(pizzafamiliar.getDisponible());

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
