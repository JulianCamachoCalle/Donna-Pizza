package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Pagos;
import com.example.DonnaPizza.Repository.PagosRepository;
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
public class ServicioPagos {

    // Link al Repository
    private final PagosRepository pagosRepository;

    HashMap<String, Object> datosPagos;

    @Autowired
    public ServicioPagos(PagosRepository pagosRepository) {
        this.pagosRepository = pagosRepository;
    }

    // Obtener Todos
    public List<Pagos> getPagos() {
        return pagosRepository.findAll();
    }

    // Obtener por ID_pedido
    public Optional<Pagos> getPagosByIdPedido(Long id) {
        return pagosRepository.findByIdPedido(id);
    }

    // Obtener por ID_metodo de pago
    public Optional<Pagos> getPagosByIMetodoPago(Long id) {
        return pagosRepository.findByIdMetodoPago(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPago(Pagos pagos) {
        datosPagos = new HashMap<>();

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
        datosPagos.put("mensaje", "Se ha registrado el Pago");
        pagosRepository.save(pagos);
        datosPagos.put("data", pagos);
        return new ResponseEntity<>(
                datosPagos,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePago(Long id, Pagos pagos) {
        datosPagos = new HashMap<>();

        // Buscar pagos por ID
        Optional<Pagos> pagoExistente = pagosRepository.findById(id);
        if (pagoExistente.isEmpty()) {
            datosPagos.put("error", true);
            datosPagos.put("mensaje", "Pago no encontrado");
            return new ResponseEntity<>(
                    datosPagos,
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

        // Actualizar Pago
        Pagos pagoActualizar = pagoExistente.get();
        pagoActualizar.setId_pedido(pagos.getId_pedido());
        pagoActualizar.setId_metodo_pago(pagos.getId_metodo_pago());
        pagoActualizar.setMonto(pagos.getMonto());
        pagoActualizar.setFecha(pagos.getFecha());


        pagosRepository.save(pagoActualizar);
        datosPagos.put("mensaje", "Se actualizó el Pago");
        datosPagos.put("data", pagoActualizar);

        return new ResponseEntity<>(
                datosPagos,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePago(Long id) {
        datosPagos = new HashMap<>();
        boolean existePago = pagosRepository.existsById(id);
        if (!existePago) {
            datosPagos.put("error", true);
            datosPagos.put("mensaje", "No existe un pago con ese id");
            return new ResponseEntity<>(
                    datosPagos,
                    HttpStatus.CONFLICT
            );
        }
        pagosRepository.deleteById(id);
        datosPagos.put("mensaje", "Pago eliminado");
        return new ResponseEntity<>(
                datosPagos,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelPagos(HttpServletResponse response) throws IOException {
        List<Pagos> pagos = pagosRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pagos Info");

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
        titleCell.setCellValue("Info Pagos");
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
        String[] headers = {"ID Pago", "Id Pedido", "Id Metodo Pago", "Monto", "Fecha"};
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
        for (Pagos pago : pagos) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(pago.getId_pago());
            dataRow.createCell(1).setCellValue(pago.getId_pedido());
            dataRow.createCell(2).setCellValue(pago.getId_metodo_pago());
            dataRow.createCell(3).setCellValue(pago.getMonto());
            dataRow.createCell(4).setCellValue(pago.getFecha());

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


