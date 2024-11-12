package com.example.DonnaPizza.MVC.Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente {

    private static final Logger logger = LoggerFactory.getLogger(ServicioCliente.class);

    // Link al Repository
    private final ClienteRepository clienteRepository;

    HashMap<String, Object> datosCliente;

    @Autowired
    public ServicioCliente(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Obtener Todos
    public List<Cliente> getClientes() {
        return this.clienteRepository.findAll();
    }

    // Obtener por ID
    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newCliente(Cliente cliente) {
        datosCliente = new HashMap<>();

        // Verificar Email Existente
        Optional<Cliente> resEmail = clienteRepository.findClienteByEmail(cliente.getEmail());

        // Mensaje de error Email
        if (resEmail.isPresent()) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ya existe un cliente con ese email");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono 9 digitos
        String telefono = cliente.getTelefono();
        if (telefono.length() != 9) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono empieza con 9
        if (!telefono.startsWith("9")) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Agregar prefijo al telefono
        if (!telefono.startsWith("+51")) {
            cliente.setTelefono("+51 " + telefono);
        }

        // Verificar Telefono Existente
        Optional<Cliente> resTel = clienteRepository.findClienteByTelefono(cliente.getTelefono());

        // Mensaje de error Telefono
        if (resTel.isPresent()) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ya existe un cliente con ese teléfono");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Guardar Con Exito
        datosCliente.put("mensaje", "Se ha registrado el Cliente");
        clienteRepository.save(cliente);
        datosCliente.put("data", cliente);
        return new ResponseEntity<>(
                datosCliente,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateCliente(Long id, Cliente cliente) {
        datosCliente = new HashMap<>();

        // Buscar el cliente por ID
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isEmpty()) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Cliente no encontrado");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el email ya está en uso por otro cliente
        Optional<Cliente> resEmail = clienteRepository.findClienteByEmail(cliente.getEmail());
        if (resEmail.isPresent() && !resEmail.get().getId_cliente().equals(id)) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ya existe un cliente con ese email");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Validaciones adicionales
        String telefono = cliente.getTelefono();
        if (telefono.length() != 9 || !telefono.startsWith("9")) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Prefijo de teléfono
        if (!telefono.startsWith("+51")) {
            cliente.setTelefono("+51 " + telefono);
        }

        // Actualizar el cliente
        Cliente clienteActualizar = clienteExistente.get();
        clienteActualizar.setNombre(cliente.getNombre());
        clienteActualizar.setApellido(cliente.getApellido());
        clienteActualizar.setEmail(cliente.getEmail());
        clienteActualizar.setTelefono(cliente.getTelefono());
        clienteActualizar.setDireccion(cliente.getDireccion());

        clienteRepository.save(clienteActualizar);
        datosCliente.put("mensaje", "Se actualizó el Cliente");
        datosCliente.put("data", clienteActualizar);

        return new ResponseEntity<>(
                datosCliente,
                HttpStatus.OK
        );
    }


    // Eliminar
    public ResponseEntity<Object> deleteCliente(Long id) {
        datosCliente = new HashMap<>();
        boolean existeCliente = this.clienteRepository.existsById(id);
        if (!existeCliente) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "No existe un cliente con ese id");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }
        clienteRepository.deleteById(id);
        datosCliente.put("mensaje", "Cliente eliminado");
        return new ResponseEntity<>(
                datosCliente,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelClientes(HttpServletResponse response) throws IOException {
        List<Cliente> clientes = clienteRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Clientes Info");

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
        titleCell.setCellValue("Info Clientes");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 4, 0,  5)); // Reemplazar el 5 segun la cantidad de headers - 1

        // Cargar imagen desde la carpeta static
        InputStream imageInputStream = new ClassPathResource("static/img/logo_color.png").getInputStream();
        int pictureIdx = workbook.addPicture(imageInputStream.readAllBytes(), Workbook.PICTURE_TYPE_JPEG);
        imageInputStream.close();

        // Crear un lienzo de dibujo en la hoja
        Drawing drawing = sheet.createDrawingPatriarch();

        // Crear un ancla para la imagen
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();

        // Establecer el rango que ocupará la imagen
        anchor.setCol1(4);
        anchor.setRow1(0);
        anchor.setCol2(5);
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
        String[] headers = {"ID_Cliente", "Nombre", "Apellido", "Email", "Telefono", "Direccion"};
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
        for (Cliente cliente : clientes) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(cliente.getId_cliente());
            dataRow.createCell(1).setCellValue(cliente.getNombre());
            dataRow.createCell(2).setCellValue(cliente.getApellido());
            dataRow.createCell(3).setCellValue(cliente.getEmail());
            dataRow.createCell(4).setCellValue(cliente.getTelefono());
            dataRow.createCell(5).setCellValue(cliente.getDireccion());

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