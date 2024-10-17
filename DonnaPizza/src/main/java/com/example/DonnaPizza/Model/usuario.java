package com.example.DonnaPizza.Model;

import java.time.LocalDate;

public class usuario {
    private String nombre;
    private String apellidos;
    private String correo;
    private String numero;
    private LocalDate cumpleanos;
    private String contrasena;

    public usuario() {
    }

    public usuario(String nombre, String apellidos, String correo, String numero, LocalDate cumpleanos, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.numero = numero;
        this.cumpleanos = cumpleanos;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(LocalDate cumpleanos) {
        this.cumpleanos = cumpleanos;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", numero='" + numero + '\'' +
                ", cumpleanos=" + cumpleanos +
                '}';
    }
}
