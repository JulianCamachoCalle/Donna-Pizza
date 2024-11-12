package com.example.DonnaPizza.MVC.Ingredientes;

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
public class ServicioIngredientes {

    // Link al Repository
    private final IngredientesRepository ingredientesRepository;

    HashMap<String, Object> datosIngredientes;

    @Autowired
    public ServicioIngredientes(IngredientesRepository ingredientesRepository) {
        this.ingredientesRepository = ingredientesRepository;
    }

    // Obtener Todos
    public List<Ingredientes> getIngredientes() {
        return ingredientesRepository.findAll();
    }

    // Obtener por ID
    public Optional<Ingredientes> getIngredienteById(Long id) {
        return ingredientesRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newIngrediente(Ingredientes ingredientes) {
        datosIngredientes = new HashMap<>();

        // Verificar Nombre Existente
        Optional<Ingredientes> resNom = ingredientesRepository.findIngredientesByNombre(ingredientes.getNombre());

        // Mnesaje de error Nombre
        if (resNom.isPresent()) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ya existe un ingrediente con ese nombre");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosIngredientes.put("mensaje", "Se ha registrado el Ingrediente");
        ingredientesRepository.save(ingredientes);
        datosIngredientes.put("data", ingredientes);
        return new ResponseEntity<>(
                datosIngredientes,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateIngrediente(Long id, Ingredientes ingredientes) {
        datosIngredientes = new HashMap<>();

        // Buscar Ingrediente por ID
        Optional<Ingredientes> ingredienteExistente = ingredientesRepository.findById(id);
        if (ingredienteExistente.isEmpty()) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ingrediente no encontrado");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya está usado
        Optional<Ingredientes> resNom = ingredientesRepository.findIngredientesByNombre(ingredientes.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_ingrediente().equals(id)) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ya existe un ingrediente con ese nombre");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Ingrediente
        Ingredientes ingredienteActualizar = ingredienteExistente.get();
        ingredienteActualizar.setNombre(ingredientes.getNombre());
        ingredienteActualizar.setCantidad_disponible(ingredientes.getCantidad_disponible());

        ingredientesRepository.save(ingredienteActualizar);
        datosIngredientes.put("mensaje", "Se actualizó el Ingrediente");
        datosIngredientes.put("data", ingredienteActualizar);

        return new ResponseEntity<>(
                datosIngredientes,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deleteIngrediente(Long id) {
        datosIngredientes = new HashMap<>();
        boolean existeIngrediente = ingredientesRepository.existsById(id);
        if (!existeIngrediente) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "No existe un ingrediente con ese id");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        }
        ingredientesRepository.deleteById(id);
        datosIngredientes.put("mensaje", "Ingrediente eliminado");
        return new ResponseEntity<>(
                datosIngredientes,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelIngredientes(HttpServletResponse response) throws IOException {
        List<Ingredientes> ingredientes = ingredientesRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Ingredientes Info");

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
        titleCell.setCellValue("Info Ingredientes");
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
        String[] headers = {"ID_Ingrediente", "Nombre", "Cantidad Disponible"};
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
        for (Ingredientes ingrediente : ingredientes) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(ingrediente.getId_ingrediente());
            dataRow.createCell(1).setCellValue(ingrediente.getNombre());
            dataRow.createCell(2).setCellValue(ingrediente.getCantidad_disponible());

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


