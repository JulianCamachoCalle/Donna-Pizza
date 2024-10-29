package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.PizzasFamiliares;
import com.example.DonnaPizza.Repository.PizzasFamiliaresRepository;
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

    // Link al Respository
    private final PizzasFamiliaresRepository pizzasfamiliaresRepository;

    HashMap<String, Object> datosPizzasFamiliares;

    @Autowired
    public ServicioPizzasFamiliares(PizzasFamiliaresRepository pizzasfamiliaresRepository) {
        this.pizzasfamiliaresRepository = pizzasfamiliaresRepository;
    }

    // Obtener Todos
    public List<PizzasFamiliares> getPizzasFamiliares() {
        return this.pizzasfamiliaresRepository.findAll();
    }

    // Obtener por ID
    public Optional<PizzasFamiliares> getPizzaFamiliarById(Long id) {
        return pizzasfamiliaresRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPizzaFamiliar(PizzasFamiliares pizzafamiliar) {
        datosPizzasFamiliares = new HashMap<>();

        // Verificar Nombre Existente
        Optional<PizzasFamiliares> resNom = pizzasfamiliaresRepository.findPizzaFamiliarByNombre(pizzafamiliar.getNombre());

        // Mensaje de error Nombre
        if (resNom.isPresent()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosPizzasFamiliares.put("mensaje", "Se ha registrado el Producto");
        pizzasfamiliaresRepository.save(pizzafamiliar);
        datosPizzasFamiliares.put("data", pizzafamiliar);
        return new ResponseEntity<>(
                datosPizzasFamiliares,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePizzaFamiliar(Long id, PizzasFamiliares pizzafamiliar) {
        datosPizzasFamiliares = new HashMap<>();

        // Buscar la pizza por ID
        Optional<PizzasFamiliares> pizzasfamiliaresExistente = pizzasfamiliaresRepository.findById(id);
        if (pizzasfamiliaresExistente.isEmpty()) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya esta en uso
        Optional<PizzasFamiliares> resNom = pizzasfamiliaresRepository.findPizzaFamiliarByNombre(pizzafamiliar.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_pizza_familiar().equals(id)) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Pizza
        PizzasFamiliares pizzasfamiliaresActualizar = pizzasfamiliaresExistente.get();
        pizzasfamiliaresActualizar.setNombre(pizzafamiliar.getNombre());
        pizzasfamiliaresActualizar.setDescripcion(pizzafamiliar.getDescripcion());
        pizzasfamiliaresActualizar.setPrecio(pizzafamiliar.getPrecio());
        pizzasfamiliaresActualizar.setDisponible(pizzafamiliar.getDisponible());

        pizzasfamiliaresRepository.save(pizzasfamiliaresActualizar);
        datosPizzasFamiliares.put("mensaje", "Se actualizó el Producto");
        datosPizzasFamiliares.put("data", pizzafamiliar);

        return new ResponseEntity<>(
                datosPizzasFamiliares,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePizzaFamiliar(Long id) {
        datosPizzasFamiliares = new HashMap<>();
        boolean existePizzaFamiliar = this.pizzasfamiliaresRepository.existsById(id);
        if (!existePizzaFamiliar) {
            datosPizzasFamiliares.put("error", true);
            datosPizzasFamiliares.put("message", "No existe un Producto con ese id");
            return new ResponseEntity<>(
                    datosPizzasFamiliares,
                    HttpStatus.CONFLICT
            );
        }
        pizzasfamiliaresRepository.deleteById(id);
        datosPizzasFamiliares.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(
                datosPizzasFamiliares,
                HttpStatus.OK
        );
    }

    // Generar Excel
    public void generarExcelPizzasFamiliares(HttpServletResponse response) throws IOException {
        List<PizzasFamiliares> pizzasFamiliares = pizzasfamiliaresRepository.findAll();

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
