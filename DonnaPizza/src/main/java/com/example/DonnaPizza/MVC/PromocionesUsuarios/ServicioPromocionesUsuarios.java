package com.example.DonnaPizza.MVC.PromocionesUsuarios;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
//Desarrollo de anthony
@Service
@AllArgsConstructor
public class ServicioPromocionesUsuarios {

    // Link al Repository
    private final PromocionesUsuariosRepository promocionesUsuariosRepository;

    HashMap<String, Object> datosPromocionesUsuarios;

    @Autowired
    public ServicioPromocionesUsuarios(PromocionesUsuariosRepository promocionesUsuariosRepository) {
        this.promocionesUsuariosRepository = promocionesUsuariosRepository;
    }

    // Obtener Todos
    public List<PromocionesUsuarios> getPromocionesUsuarios() {
        return promocionesUsuariosRepository.findAll();
    }

    // Obtener por ID
    public Optional<PromocionesUsuarios> getPromocionesUsuariosByIdPromocionesUsuarios(Long idPromocionUsuario) {
        return promocionesUsuariosRepository.findById(idPromocionUsuario);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newPromocionesUsuarios(PromocionesUsuarios promocionesUsuarios) {
        datosPromocionesUsuarios = new HashMap<>();

        // Verificar id Existe
        Optional<PromocionesUsuarios> proUserid = promocionesUsuariosRepository.findPromocionesUsuariosByIdPromocionUsuario(promocionesUsuarios.getIdPromocionUsuario());

        // Mensaje de error id
        if (proUserid.isPresent()) {
            datosPromocionesUsuarios.put("error", true);
            datosPromocionesUsuarios.put("mensaje", "Ya existe una promocionUsuario con ese id");
            return new ResponseEntity<>(
                    datosPromocionesUsuarios,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosPromocionesUsuarios.put("mensaje", "Se ha registrado la promocionUsuario");
        promocionesUsuariosRepository.save(promocionesUsuarios);
        datosPromocionesUsuarios.put("data", promocionesUsuarios);
        return new ResponseEntity<>(
                datosPromocionesUsuarios,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updatePromocionesUsuarios(Long idPromocionUsuario, PromocionesUsuarios promocionesUsuarios) {
        datosPromocionesUsuarios = new HashMap<>();

        // Buscar promocion por ID
        Optional<PromocionesUsuarios> promocionUsuarioExistente = promocionesUsuariosRepository.findPromocionesUsuariosByIdPromocionUsuario(promocionesUsuarios.getIdPromocionUsuario());
        if (promocionUsuarioExistente.isEmpty()) {
            datosPromocionesUsuarios.put("error", true);
            datosPromocionesUsuarios.put("mensaje", "PromocionUsuario no encontrada");
            return new ResponseEntity<>(
                    datosPromocionesUsuarios,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el id ya está usado
        Optional<PromocionesUsuarios> proUserid = promocionesUsuariosRepository.findPromocionesUsuariosByIdPromocionUsuario(promocionesUsuarios.getIdPromocionUsuario());
        if (proUserid.isPresent() && !proUserid.get().getIdPromocionUsuario().equals(idPromocionUsuario)) {
            datosPromocionesUsuarios.put("error", true);
            datosPromocionesUsuarios.put("mensaje", "Ya existe una promocionUsuario con ese id");
            return new ResponseEntity<>(
                    datosPromocionesUsuarios,
                    HttpStatus.CONFLICT
            );
        }

        // Actualizar Promociones usuarios
        PromocionesUsuarios promocionesUsuariosActualizar = promocionUsuarioExistente.get();
        promocionesUsuariosActualizar.setIdPromocionUsuario(promocionesUsuarios.getIdPromocionUsuario());
        promocionesUsuariosActualizar.setIdUsuario(promocionesUsuarios.getIdUsuario());
        promocionesUsuariosActualizar.setIdPromocion(promocionesUsuarios.getIdPromocion());
        promocionesUsuariosActualizar.setEstado(promocionesUsuarios.getEstado());

        promocionesUsuariosRepository.save(promocionesUsuariosActualizar);
        datosPromocionesUsuarios.put("mensaje", "Se actualizó promocionUsuario");
        datosPromocionesUsuarios.put("data", promocionesUsuariosActualizar);

        return new ResponseEntity<>(
                datosPromocionesUsuarios,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deletePromocionesUsuarios(Long idPromocionUsuario) {
        datosPromocionesUsuarios = new HashMap<>();
        boolean existePromocionUsuario = promocionesUsuariosRepository.existsById(idPromocionUsuario);
        if (!existePromocionUsuario) {
            datosPromocionesUsuarios.put("error", true);
            datosPromocionesUsuarios.put("mensaje", "No existe promocionUsuario con ese id");
            return new ResponseEntity<>(
                    datosPromocionesUsuarios,
                    HttpStatus.CONFLICT
            );
        }
        promocionesUsuariosRepository.deleteById(idPromocionUsuario);
        datosPromocionesUsuarios.put("mensaje", "promocionUsuario eliminado");
        return new ResponseEntity<>(
                datosPromocionesUsuarios,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelPromocionesUsuarios(HttpServletResponse response) throws IOException {
        List<PromocionesUsuarios> promocionesUsuarios = promocionesUsuariosRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("promocionesUsuarios Info");

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
        titleCell.setCellValue("Info PromocionesUsuarios");
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
        String[] headers = {"ID_Promocion_usuario", "id_usuario", "id_promocion","Fecha","Estado"};
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int rowIndex = 2;
        for (PromocionesUsuarios promocionUsuario : promocionesUsuarios) {
            HSSFRow dataRow = sheet.createRow(rowIndex++);
            dataRow.createCell(0).setCellValue(promocionUsuario.getIdPromocionUsuario());
            dataRow.createCell(1).setCellValue(promocionUsuario.getIdUsuario());
            dataRow.createCell(2).setCellValue(promocionUsuario.getIdPromocion());
            dataRow.createCell(3).setCellValue(promocionUsuario.getFechaAplicacion());
            dataRow.createCell(4).setCellValue(promocionUsuario.getEstado());

            for (int i = 0; i < headers.length; i++) {
                dataRow.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Llenar datos
        int dataRowIndex = 2;
        for (PromocionesUsuarios promocionUsuario : promocionesUsuarios) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(promocionUsuario.getIdPromocionUsuario());
            dataRow.createCell(1).setCellValue(promocionUsuario.getIdUsuario());
            dataRow.createCell(2).setCellValue(promocionUsuario.getIdPromocion());
            dataRow.createCell(3).setCellValue(promocionUsuario.getFechaAplicacion());
            dataRow.createCell(4).setCellValue(promocionUsuario.getEstado());



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