package com.example.DonnaPizza.Model;

import java.io.Serializable;
import java.util.Objects;

public class IngredientesEntradaId implements Serializable {
    private Long id_entrada;
    private Long id_ingrediente;

    // Constructor vacío
    public IngredientesEntradaId() {
    }

    // Constructor con parámetros
    public IngredientesEntradaId(Long id_entrada, Long id_ingrediente) {
        this.id_entrada = id_entrada;
        this.id_ingrediente = id_ingrediente;
    }

    // Getters y Setters
    public Long getIdEntrada() {
        return id_entrada;
    }

    public void setIdEntrada(Long id_entrada) {
        this.id_entrada = id_entrada;
    }

    public Long getIdIngrediente() {
        return id_ingrediente;
    }

    public void setIdIngrediente(Long id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientesEntradaId)) return false;
        IngredientesEntradaId that = (IngredientesEntradaId) o;
        return Objects.equals(id_entrada, that.id_entrada) && 
               Objects.equals(id_ingrediente, that.id_ingrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_entrada, id_ingrediente);
    }
}
