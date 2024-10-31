package com.example.DonnaPizza.Model;

import java.io.Serializable;
import java.util.Objects;

public class IngredientesPastaId implements Serializable {
    private Long id_pasta;
    private Long id_ingrediente;

    // Constructor
    public IngredientesPastaId(Long id_pasta, Long id_ingrediente) {
        this.id_pasta = id_pasta;
        this.id_ingrediente = id_ingrediente;
    }

    // Getters y Setters
    public Long getId_pasta() {
        return id_pasta;
    }

    public void setId_pasta(Long id_pasta) {
        this.id_pasta = id_pasta;
    }

    public Long getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(Long id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    // Implementación de equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientesPastaId)) return false;
        IngredientesPastaId that = (IngredientesPastaId) o;
        return Objects.equals(id_pasta, that.id_pasta) &&
                Objects.equals(id_ingrediente, that.id_ingrediente);
    }

    // Implementación de hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(id_pasta, id_ingrediente);
    }
}
