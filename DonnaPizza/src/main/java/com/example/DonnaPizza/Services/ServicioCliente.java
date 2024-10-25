
package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Cliente;
import com.example.DonnaPizza.Repository.ClienteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente {

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

    // Crear Nuevo y Actualizar
    public ResponseEntity<Object> newCliente(Cliente cliente) {
        datosCliente = new HashMap<>();

        // Verificar Email Existente
        Optional<Cliente> resEmail = clienteRepository.findClienteByEmail(cliente.getEmail());

        // Mensaje de error Email
        if (resEmail.isPresent() && cliente.getId_cliente() == null) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ya existe un cliente con ese email");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Verificar telefono 9 digitos
        String telefono = cliente.getTelefono();
        if (telefono.length() != 9 && cliente.getId_cliente() == null) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        // Agregar prefijo al telefono
        if (!telefono.startsWith("+51") && cliente.getId_cliente() == null) {
            cliente.setTelefono("+51 " + telefono);
        }

        // Verificar Telefono Existente
        Optional<Cliente> resTel = clienteRepository.findClienteByTelefono(cliente.getTelefono());

        // Mensaje de error Telefono
        if (resTel.isPresent() && cliente.getId_cliente() == null) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ya existe un cliente con ese teléfono");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT
            );
        }

        datosCliente.put("mensaje", "Se ha registrado el Cliente");

        //Actualizar
        if(cliente.getId_cliente() != null) {
            datosCliente.put("mensaje", "Se actualizo el Cliente");
        }

        // Guardar Con Exito
        clienteRepository.save(cliente);
        datosCliente.put("data", cliente);
        return new ResponseEntity<>(
                datosCliente,
                HttpStatus.CREATED);
    }

    // Eliminar
    public ResponseEntity<Object> deleteCliente(Long id) {
        datosCliente = new HashMap<>();
        boolean existe = this.clienteRepository.existsById(id);
        if (!existe) {
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
}
