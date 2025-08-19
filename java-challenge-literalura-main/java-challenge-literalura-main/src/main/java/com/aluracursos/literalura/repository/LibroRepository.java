package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByIdLibro(Long idLibro);

    @Query("SELECT DISTINCT idioma  FROM Libro l JOIN l.idiomas idioma")
    List<String> findDistinctIdiomas();

    @Query("SELECT l FROM Libro l JOIN l.idiomas i WHERE i.codigo IN :codigos")
    List<Libro> findByIdiomas(@Param("codigos") List<String> codigos);

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.idiomas i WHERE i.codigo IN :codigos")
    Page<Libro> findByIdiomas(@Param("codigos") List<String> codigos, Pageable pageable);

}
