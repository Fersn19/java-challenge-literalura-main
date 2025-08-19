package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface PersonaRepository extends JpaRepository<Persona,Long> {
    Persona findByNombre(String nombre);

    @Query("SELECT p FROM Persona p LEFT JOIN FETCH p.libros WHERE p.anioFallecimiento >= :anio And p.anioNacimiento <= :anio")
    List<Persona> findByAnioAutor(@Param("anio") Integer year);
}
