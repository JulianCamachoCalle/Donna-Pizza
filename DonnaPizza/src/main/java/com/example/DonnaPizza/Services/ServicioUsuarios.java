package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Usuarios;
import com.example.DonnaPizza.Repository.UsuariosRepository;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioUsuarios implements UserDetailsService {

    // Link al Repository
    private final UsuariosRepository usuariosRepository;

    // Contrasena
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    HashMap<String, Object> datosUsuario;

    @Autowired
    public ServicioUsuarios(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    // Obtener Todos
    public List<Usuarios> getUsuarios() {
        return this.usuariosRepository.findAll();
    }

    // Obtener por ID
    public Optional<Usuarios> getUsuarioById(Long id) {
        return this.usuariosRepository.findById(id);
    }

    // Crear Nuevo
    public ResponseEntity<Object> newUsuario(Usuarios usuarios) {
        datosUsuario = new HashMap<>();

        // Verificar Email Existente
        Optional<Usuarios> resEmail = usuariosRepository.findUsuariosByUsername(usuarios.getUsername());

        // Mensaje de error Email
        if (resEmail.isPresent()) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ya existe un usuario con ese email");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono 9 digitos
        String telefono = usuarios.getTelefono();
        if (telefono.length() != 9) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono empieza con 9
        if (!telefono.startsWith("9")) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Agregar prefijo al telefono
        if (!telefono.startsWith("+51")) {
            usuarios.setTelefono("+51 " + telefono);
        }

        // Verificar Telefono Existente
        Optional<Usuarios> resTel = usuariosRepository.findUsuariosByTelefono(usuarios.getTelefono());

        // Mensaje de error Telefono
        if (resTel.isPresent()) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ya existe un usuario con ese teléfono");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Formatear Contrasena
        usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));

        // Guardar Con Exito
        datosUsuario.put("mensaje", "Se ha registrado el Usuario");
        usuariosRepository.save(usuarios);
        datosUsuario.put("data", usuarios);
        return new ResponseEntity<>(
                datosUsuario,
                HttpStatus.CREATED
        );
    }

    // Actualizar
    public ResponseEntity<Object> updateUsuarios(Long id, Usuarios usuarios) {
        datosUsuario = new HashMap<>();

        // Buscar el usuario por ID
        Optional<Usuarios> usuarioExistente = usuariosRepository.findById(id);
        if (usuarioExistente.isEmpty()) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Usuario no encontrado");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar si el email ya está en uso por otro usuario
        Optional<Usuarios> resEmail = usuariosRepository.findUsuariosByUsername(usuarios.getUsername());
        if (resEmail.isPresent() && !resEmail.get().getId_usuario().equals(id)) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ya existe un usuario con ese email");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Validaciones adicionales
        String telefono = usuarios.getTelefono();
        if (telefono.length() != 9 || !telefono.startsWith("9")) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }

        // Prefijo de teléfono
        if (!telefono.startsWith("+51")) {
            usuarios.setTelefono("+51 " + telefono);
        }

        // Actualizar el cliente
        Usuarios usuariosActualizar = usuarioExistente.get();
        usuariosActualizar.setNombre(usuarios.getNombre());
        usuariosActualizar.setApellido(usuarios.getApellido());
        usuariosActualizar.setUsername(usuarios.getUsername());
        usuariosActualizar.setTelefono(usuarios.getTelefono());
        usuariosActualizar.setDireccion(usuarios.getDireccion());
        usuariosActualizar.setRol(usuarios.getRol());
        if (!usuarios.getPassword().isEmpty()) {
            usuariosActualizar.setPassword(passwordEncoder.encode(usuarios.getPassword()));
        }

        usuariosRepository.save(usuariosActualizar);
        datosUsuario.put("mensaje", "Se actualizó el Usuario");
        datosUsuario.put("data", usuariosActualizar);

        return new ResponseEntity<>(
                datosUsuario,
                HttpStatus.OK
        );
    }

    // Eliminar
    public ResponseEntity<Object> deleteUsuarios(Long id) {
        datosUsuario = new HashMap<>();
        boolean existeCliente = this.usuariosRepository.existsById(id);
        if (!existeCliente) {
            datosUsuario.put("error", true);
            datosUsuario.put("mensaje", "No existe un usuario con ese id");
            return new ResponseEntity<>(
                    datosUsuario,
                    HttpStatus.CONFLICT
            );
        }
        usuariosRepository.deleteById(id);
        datosUsuario.put("mensaje", "Usuario eliminado");
        return new ResponseEntity<>(
                datosUsuario,
                HttpStatus.ACCEPTED
        );
    }

    // Generar Excel
    public void generarExcelUsuarios(HttpServletResponse response) throws IOException {
        List<Usuarios> usuarios = usuariosRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Usuarios Info");

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
        titleCell.setCellValue("Info Usuarios");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8)); // Reemplazar el 8 segun la cantidad de headers - 1

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
        String[] headers = {"ID_Usuario", "Nombre", "Apellido", "Email", "Telefono", "Direccion", "Rol", "Contraseña", "Fecha Registro"};
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
        for (Usuarios usuario : usuarios) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(usuario.getId_usuario());
            dataRow.createCell(1).setCellValue(usuario.getNombre());
            dataRow.createCell(2).setCellValue(usuario.getApellido());
            dataRow.createCell(3).setCellValue(usuario.getUsername());
            dataRow.createCell(4).setCellValue(usuario.getTelefono());
            dataRow.createCell(5).setCellValue(usuario.getDireccion());
            dataRow.createCell(6).setCellValue(usuario.getRol().toString());
            dataRow.createCell(7).setCellValue(usuario.getPassword());
            dataRow.createCell(8).setCellValue(usuario.getFecha_registro());

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuarios> usuarios = usuariosRepository.findUsuariosByUsername(username);
        if (usuarios.isPresent()) {

            var usuario = usuarios.get();
            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
