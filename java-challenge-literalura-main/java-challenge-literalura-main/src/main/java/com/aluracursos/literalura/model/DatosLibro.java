package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("count") Integer total,
        @JsonAlias("next") String linkSiguiente,
        @JsonAlias("previous") String linkAnterior,
        @JsonAlias("results") List<DatosResultadosLibro> resultadosLibro
        ) {
}
