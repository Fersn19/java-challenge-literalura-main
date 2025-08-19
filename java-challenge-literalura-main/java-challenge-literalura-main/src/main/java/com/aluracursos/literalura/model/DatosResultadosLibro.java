package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResultadosLibro(
        @JsonAlias("id") Long idLibro,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors")  List<DatosPersona> autores,
        @JsonAlias("summaries") List<String> resumen,
        @JsonAlias("translators") List<DatosPersona> traductores,//ejemplos: Metamorphosis, Crime and Punishment, Thus Spake Zarathustra: A Book for All and Non
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("copyright") boolean copyright,
        @JsonAlias("media_type") String tipoMedio,
        @JsonAlias("download_count") Long descargas
        ) {
}
