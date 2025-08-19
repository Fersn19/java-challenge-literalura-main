package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Idioma;
import com.aluracursos.literalura.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IdiomaRepository extends JpaRepository<Idioma,String> {
    Persona findByNombre(String nombre);

    @Query("SELECT CONCAT(i.codigo, ' - ', i.nombre) FROM Idioma i")
    List<Idioma> findIdiomasConNombre();

}
