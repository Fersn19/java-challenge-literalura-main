package com.aluracursos.literalura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "idiomas")
public class Idioma {
    @Id
    private String codigo;
    private String nombre;

    public Idioma() {}

    public Idioma(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters y setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}