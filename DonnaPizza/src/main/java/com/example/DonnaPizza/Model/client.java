
package com.example.DonnaPizza.Model;

import java.time.LocalDate;

public class client {
    private String nombre;
    private String apellidos;
    private String correo;
    private String numero;
    private String direccion;

    public client() {
    }

    public client(String nombre, String apellidos, String correo, String numero, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.numero = numero;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", numero='" + numero + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
