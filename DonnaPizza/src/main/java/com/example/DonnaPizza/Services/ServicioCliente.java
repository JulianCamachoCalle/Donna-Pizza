
package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Cliente;
import com.example.DonnaPizza.Repository.ClienteDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente {
    
    @Autowired
    private ClienteDAO clienteDao;
    
    //Select
    public List<Cliente> getList() {
        return clienteDao.findAll();
    }
    
    //INSERT Y UPDATE
    public Cliente save(Cliente clientes) {
        return clienteDao.save(clientes);
    }
    
    //SELECT
    public Cliente get(Long id) {
        return clienteDao.findById(id).orElse(null);
    }
    
    //DELETE
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }
}
