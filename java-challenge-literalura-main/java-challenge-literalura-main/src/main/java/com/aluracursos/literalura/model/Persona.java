package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "personas")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Persona() {

    }

    public Persona(DatosPersona datosPersona) {
        this.nombre = ( datosPersona.nombre().length() > 500 ? datosPersona.nombre().substring(0,499) : datosPersona.nombre());
        this.anioNacimiento = datosPersona.anioNacimiento();
        this.anioFallecimiento = datosPersona.anioFallecimiento();
    }

    //setter y getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        /*
        List<Persona> losAutores = libros.stream().map(Persona ->  this).collect(Collectors.toList());
        libros.forEach(a -> a.setAutores(losAutores));  //guarda los datos de esta autor (del  libro actual)
         */

        this.libros = libros;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anioNacimiento=" + anioNacimiento +
                ", anioFallecimiento=" + anioFallecimiento +
                ", Libros [" + libros + "]";
    }

}
