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
                HttpStatus.CREATED);
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
                    HttpStatus.NOT_FOUND);
        }

        // Verificar si el email ya está en uso por otro cliente
        Optional<Cliente> resEmail = clienteRepository.findClienteByEmail(cliente.getEmail());
        if (resEmail.isPresent() && !resEmail.get().getId_cliente().equals(id)) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ya existe un cliente con ese email");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT);
        }

        // Validaciones adicionales
        String telefono = cliente.getTelefono();
        if (telefono.length() != 9 || !telefono.startsWith("9")) {
            datosCliente.put("error", true);
            datosCliente.put("mensaje", "Ingrese un número de teléfono correcto");
            return new ResponseEntity<>(
                    datosCliente,
                    HttpStatus.CONFLICT);
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
                HttpStatus.OK);
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
}