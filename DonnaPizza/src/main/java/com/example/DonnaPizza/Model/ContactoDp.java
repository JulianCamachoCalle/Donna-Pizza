package com.example.DonnaPizza.Model;

public class ContactoDp {
    private String nombre, email,telefono;

    public ContactoDp(String nombre, String email, String telefono) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public ContactoDp() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}

