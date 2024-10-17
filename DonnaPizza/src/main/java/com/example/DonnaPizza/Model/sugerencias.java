package com.example.DonnaPizza.Model;


public class sugerencias {
    private long cod;
    private String nom, email,descrip;

    public sugerencias() {
      super();
    }

    public sugerencias(long cod, String nom, String email, String descrip) {
       super();
        this.cod = cod;
        this.nom = nom;
        this.email = email;
        this.descrip = descrip;
    }

    
    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

   
    
}
