package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Pedidos;
import com.example.DonnaPizza.Repository.PedidosRepository;
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
public class ServicioPedidos {

    // Link al Repository
    private final PedidosRepository pedidosRepository;

    HashMap<String, Object> datosPedidos;

    @Autowired
    public ServicioPedidos(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    // Obtener Todos
    public List<Pedidos> getPedidos() {
        return pedidosRepository.findAll();
    }

    // Obtener por ID
    public Optional<Pedidos> getPedidoById(Long id) {
        return pedidosRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPedido(Pedidos pedidos) {
        datosPedidos = new HashMap<>();

        /* // Verificar Nombre Existente
        Optional<Ingredientes> resNom = ingredientesRepository.findByNombre(ingredientes.getNombre());

        // Mnesaje de error Nombre
        if (resNom.isPresent()) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ya existe un ingrediente con ese nombre");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        } */

        // Guardar Con Exito
        datosPedidos.put("mensaje", "Se ha registrado el Pedido");
        pedidosRepository.save(pedidos);
        datosPedidos.put("data", pedidos);
        return new ResponseEntity<>(
                datosPedidos,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePedido(Long id, Pedidos pedidos) {
        datosPedidos = new HashMap<>();

        // Buscar pedido por ID
        Optional<Pedidos> pedidoExistente = pedidosRepository.findById(id);
        if (pedidoExistente.isEmpty()) {
            datosPedidos.put("error", true);
            datosPedidos.put("mensaje", "Pedido no encontrado");
            return new ResponseEntity<>(
                    datosPedidos,
                    HttpStatus.NOT_FOUND
            );
        }

        /* // Verificar si el nombre ya está usado
        Optional<Ingredientes> resNom = ingredientesRepository.findByNombre(ingredientes.getNombre());
        if (resNom.isPresent() && !resNom.get().getId_ingrediente().equals(id)) {
            datosIngredientes.put("error", true);
            datosIngredientes.put("mensaje", "Ya existe un ingrediente con ese nombre");
            return new ResponseEntity<>(
                    datosIngredientes,
                    HttpStatus.CONFLICT
            );
        } */

        // Actualizar Pedido
        Pedidos pedidoActualizar = pedidoExistente.get();
        pedidoActualizar.setId_usuario(pedidos.getId_usuario());
        pedidoActualizar.setId_cliente(pedidos.getId_cliente());
        pedidoActualizar.setFecha(pedidos.getFecha());
        pedidoActualizar.setTotal(pedidos.getTotal());
        pedidoActualizar.setId_documento(pedidos.getId_documento());


        pedidosRepository.save(pedidoActualizar);
        datosPedidos.put("mensaje", "Se actualizó el Pedido");
        datosPedidos.put("data", pedidoActualizar);

        return new ResponseEntity<>(
                datosPedidos,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePedido(Long id) {
        datosPedidos = new HashMap<>();
        boolean existePedido = pedidosRepository.existsById(id);
        if (!existePedido) {
            datosPedidos.put("error", true);
            datosPedidos.put("mensaje", "No existe un pedido con ese id");
            return new ResponseEntity<>(
                    datosPedidos,
                    HttpStatus.CONFLICT
            );
        }
        pedidosRepository.deleteById(id);
        datosPedidos.put("mensaje", "Pedido eliminado");
        return new ResponseEntity<>(
                datosPedidos,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelPedidos(HttpServletResponse response) throws IOException {
        List<Pedidos> pedidos = pedidosRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pedidos Info");

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
        titleCell.setCellValue("Info Pedidos");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // Reemplazar el 2 segun la cantidad de headers - 1

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
        String[] headers = {"ID Pedidos", "Id Usuario", "Id Cliente", "Fecha", "Total","Id Documento"};
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
        for (Pedidos pedido : pedidos) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(pedido.getId_pedido());
            dataRow.createCell(1).setCellValue(pedido.getId_usuario());
            dataRow.createCell(2).setCellValue(pedido.getId_cliente());
            dataRow.createCell(3).setCellValue(pedido.getFecha());
            dataRow.createCell(4).setCellValue(pedido.getTotal());
            dataRow.createCell(5).setCellValue(pedido.getId_documento());

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


