package com.example.DonnaPizza.MVC.Pizzas;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPizzas {

    // Link al Respository
    private final PizzasRepository pizzasRepository;

    HashMap<String, Object> datosPizzas;

    @Autowired
    public ServicioPizzas(PizzasRepository pizzasRepository) {
        this.pizzasRepository = pizzasRepository;
    }

    // Obtener Todos
    public List<Pizzas> getPizzas() {
        return this.pizzasRepository.findAll();
    }

    // Obtener por ID
    public Optional<Pizzas> getPizzaById(Long id) {
        return pizzasRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPizza(Pizzas pizza) {
        datosPizzas = new HashMap<>();

        // Verificar Nombre Existente
        Optional<Pizzas> resNom = pizzasRepository.findPizzaByNombre(pizza.getNombre());

        // Mensaje de error Nombre
        if (resNom.isPresent()) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosPizzas.put("mensaje", "Se ha registrado el Producto");
        pizzasRepository.save(pizza);
        datosPizzas.put("data", pizza);
        return new ResponseEntity<>(
                datosPizzas,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePizza(Long id, Pizzas pizza) {
        datosPizzas = new HashMap<>();

        // Buscar la pizza por ID
        Optional<Pizzas> pizzasExistente = pizzasRepository.findById(id);
        if (pizzasExistente.isEmpty()) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "Pizza no encontrada");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el nombre ya esta en uso
        Optional<Pizzas> resNom = pizzasRepository.findPizzaByNombre(pizza.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_pizza().equals(id)) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "Ya existe un Producto con ese nombre");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Pizza
        Pizzas pizzasActualizar = pizzasExistente.get();
        pizzasActualizar.setNombre(pizza.getNombre());
        pizzasActualizar.setDescripcion(pizza.getDescripcion());
        pizzasActualizar.setPrecio(pizza.getPrecio());
        pizzasActualizar.setDisponible(pizza.getDisponible());

        pizzasRepository.save(pizzasActualizar);
        datosPizzas.put("mensaje", "Se actualizó el Producto");
        datosPizzas.put("data", pizza);

        return new ResponseEntity<>(
                datosPizzas,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePizza(Long id) {
        datosPizzas = new HashMap<>();
        boolean existePizza = this.pizzasRepository.existsById(id);
        if (!existePizza) {
            datosPizzas.put("error", true);
            datosPizzas.put("message", "No existe un Producto con ese id");
            return new ResponseEntity<>(
                    datosPizzas,
                    HttpStatus.CONFLICT
            );
        }
        pizzasRepository.deleteById(id);
        datosPizzas.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(
                datosPizzas,
                HttpStatus.OK
        );
    }

    // Generar Excel
    public void generarExcelPizzas(HttpServletResponse response) throws IOException {
        List<Pizzas> pizzas = pizzasRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pizzas Info");

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
        titleStyle.setAlignment(HorizontalAlignment.LEFT);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Crear fila del título y fusionar celdas
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Info Pizzas");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 4, 0, 4)); // Reemplazar el 4 segun la cantidad de headers - 1

        // Cargar imagen desde la carpeta static
        InputStream imageInputStream = new ClassPathResource("static/img/logo_color.png").getInputStream();
        int pictureIdx = workbook.addPicture(imageInputStream.readAllBytes(), Workbook.PICTURE_TYPE_JPEG);
        imageInputStream.close();

        // Crear un lienzo de dibujo en la hoja
        Drawing drawing = sheet.createDrawingPatriarch();

        // Crear un ancla para la imagen
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();

        // Establecer el rango que ocupará la imagen
        anchor.setCol1(3);
        anchor.setRow1(0);
        anchor.setCol2(4);
        anchor.setRow2(4);

        // Crear la imagen
        Picture picture = drawing.createPicture(anchor, pictureIdx);

        // Ajustar el tamaño de la imagen si es necesario
        picture.resize(2,1.25);

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
        HSSFRow row = sheet.createRow(5);
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
        int dataRowIndex = 6;
        for (Pizzas pizza : pizzas) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(pizza.getId_pizza());
            dataRow.createCell(1).setCellValue(pizza.getNombre());
            dataRow.createCell(2).setCellValue(pizza.getDescripcion());
            dataRow.createCell(3).setCellValue(pizza.getPrecio());
            dataRow.createCell(4).setCellValue(pizza.getDisponible());

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
