package com.example.DonnaPizza.MVC.PromocionesUsuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//Desarrollo de anthony
@RestController
@RequestMapping(path = "api/v1/promocionesusuarios")
public class ControladorPromocionesUsuarios {

    private final PromocionesUsuariosRepository promocionesUsuariosRepository;
    private final ServicioPromocionesUsuarios servicioPromocionesUsuarios;

    @Autowired
    public ControladorPromocionesUsuarios(ServicioPromocionesUsuarios servicioPromocionesUsuarios, PromocionesUsuariosRepository promocionesUsuariosRepository) {
        this.servicioPromocionesUsuarios = servicioPromocionesUsuarios;
        this.promocionesUsuariosRepository = promocionesUsuariosRepository;
    }

    //Obtener todos
    @GetMapping
    public List<PromocionesUsuarios> getPromocionesUsuarios() {
        return servicioPromocionesUsuarios.getPromocionesUsuarios();
    }

    //Obtener pro id
    @GetMapping("/{promocionesusuariosId}")
    public ResponseEntity<PromocionesUsuarios> getPromocionesUsuarios(@PathVariable("promocionesusuariosId") Long  idPromocionUsuario) {
        Optional<PromocionesUsuarios> promocionesUsuarios = this.servicioPromocionesUsuarios.getPromocionesUsuariosByIdPromocionesUsuarios(idPromocionUsuario);
        if(promocionesUsuarios.isPresent()){
            return ResponseEntity.ok(promocionesUsuarios.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //Registrar nuevo
    @PostMapping
    public ResponseEntity<Object> registrarPromocionesUsuarios(@RequestBody PromocionesUsuarios promocionesUsuarios) {
        return servicioPromocionesUsuarios.newPromocionesUsuarios(promocionesUsuarios);
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPromocionesUsuarios(@PathVariable Long id, @RequestBody PromocionesUsuarios promocionesUsuarios) {
        return servicioPromocionesUsuarios.updatePromocionesUsuarios(id, promocionesUsuarios);
    }

    //Eliminar
    @DeleteMapping("/{promocionesusuariosId}")
    public ResponseEntity<Object> eliminarPromocionesUsuarios(@PathVariable("promocionesusuariosId") Long id) {
        return servicioPromocionesUsuarios.deletePromocionesUsuarios(id);
    }
}
