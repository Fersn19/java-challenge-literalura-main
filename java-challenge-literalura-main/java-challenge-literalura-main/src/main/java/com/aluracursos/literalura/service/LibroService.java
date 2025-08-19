package com.aluracursos.literalura.service;

import com.aluracursos.literalura.client.ConsumoAPI;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Idioma;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.Persona;
import com.aluracursos.literalura.repository.IdiomaRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.repository.PersonaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class LibroService {
    private final String URL_BASE = "https://gutendex.com/books";

    @Autowired
    ConsumoAPI consumoApi;

    @Autowired
    LibroRepository repositorioLibro;

    @Autowired
    PersonaRepository repositorioPersona;

    @Autowired
    IdiomaRepository repositorioIdioma;

    private ConvierteDatos conversor = new ConvierteDatos();

    public LibroService() {

    }

    @Transactional
    public void buscarLibroPortitulo(String nombreLibro) {
        String url = URL_BASE + "/?search=" + nombreLibro.replace(" ", "%20");
        System.out.println("--------");
        System.out.println("Espere unos momentos....:");
        System.out.println("--------");
        String json = consumoApi.obtenerDatos(url);
        //System.out.println("json = " + json);
        DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);
        System.out.println("1.- url: " + url + "; total de libros obtenidos: " + datos.total() + ". Link siguiente: " + datos.linkSiguiente() + ". Link anterior: " + datos.linkAnterior());
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("--------");


        datos.resultadosLibro().forEach( datosLibro -> {

            //Valida que el libro no se encuentre en la BD
            Optional<Libro> libroExistente = repositorioLibro.findByIdLibro(datosLibro.idLibro());
            if ( libroExistente.isPresent() ){
                System.out.println("--------");
                System.out.println(String.format("-------- El Libro [ %s ] ya existe.", libroExistente.get().getTitulo()));
                System.out.println("--------");
            }
            else {
                // Crea un nuevo libro
                Libro nuevoLibro = new Libro(datosLibro);
                nuevoLibro.setId(null); // ⚠️ importante: evitar conflictos con id asignado manualmente

								// Procesa los autores
                List<Persona> autoresFinales = new ArrayList<>();

                for (Persona autor : nuevoLibro.getAutores()) {
                    Persona existente = repositorioPersona.findByNombre(autor.getNombre());
                    if (existente != null) {
                        existente.getLibros().add(nuevoLibro);
                        autoresFinales.add(existente);
                    } else {
                        autor.setLibros(List.of(nuevoLibro));
                        autoresFinales.add(autor);
                    }
                }

                nuevoLibro.setAutores(autoresFinales);
                //System.out.println(">>>>> El libro es: " + nuevoLibro);
                repositorioLibro.save(nuevoLibro);
                imprimeLibro(nuevoLibro);

            }
        });

    }

    public void buscarTodosLosLibrosRegistrados() {
        List<Libro>  libros = repositorioLibro.findAll();
        if (libros.isEmpty()) {
            System.out.println("--------");
            System.out.println(String.format("-------- No existen libros registrados."));
            System.out.println("--------");
        } else {
            libros.stream().forEach( libro -> imprimeLibro(libro));
        }
    }

    public void buscarAutoresRegistrados() {
        List<Persona> autores = repositorioPersona.findAll();
        if (autores.isEmpty()) {
            System.out.println("--------");
            System.out.println(String.format("-------- No existen autores registrados."));
            System.out.println("--------");
        } else {
            autores.stream().forEach( autor -> imprimeAutor(autor));
        }
    }

    public void buscarAutoresRegistradosPorAño(Integer year) {
        List<Persona> autores = repositorioPersona.findByAnioAutor(year);
        if (autores.isEmpty()) {
            System.out.println("--------");
            System.out.println(String.format("-------- No existen autores registrados."));
            System.out.println("--------");
        } else {
            autores.stream().forEach( autor -> imprimeAutor(autor));
        }
    }

    public boolean obtenerTodosLosIdiomas() {
        List<Idioma> idiomas = repositorioIdioma.findAll();
        if (idiomas.isEmpty()) {
            System.out.println("--------");
            System.out.println(String.format("-------- No existen idiomas registrados."));
            System.out.println("--------");
            return false;
        }

        //idiomas.forEach(System.out::println);

        System.out.println("--------");
        System.out.println(String.format("-------- Los idiomas disponibles son: "));
        System.out.println(String.format("-------- Ejemplos:  en / en,es / es,fr,en / de,zh,fi,pt "));
        System.out.println("--------");
        int count = 0;
        for (Idioma idioma : idiomas) {
            System.out.printf("%-25s", idioma.toString()); // ajusta el ancho si es necesario
            count++;
            if (count % 5 == 0) {
                System.out.println(); // salto de línea cada 5 idiomas
            }
        }
        if (count % 5 != 0) {
            System.out.println(); // salto final si no fue múltiplo exacto
        }
        return true;
    }

    public void buscarLibrosPorIdiomas(String misIdiomas) {
        List<String> codigos = Arrays.stream(misIdiomas.split(","))
                .map(String::trim)
                .toList();
        List<Libro>  libros = repositorioLibro.findByIdiomas(codigos);
        if (libros.isEmpty()) {
            System.out.println("--------");
            System.out.println(String.format("-------- No existen libros registrados."));
            System.out.println("--------");
        } else {
            libros.stream().forEach( libro -> imprimeLibro(libro));
        }
    }

    public void buscarLibrosPorIdiomasPaginado(String misIdiomas) {
        List<String> codigos = Arrays.stream(misIdiomas.split(","))
                .map(String::trim)
                .toList();

        int pagina = 0;
        int tamanio = 5;
        Page<Libro> libros;
        Scanner scanner = new Scanner(System.in);

        do {
            Pageable pageable = PageRequest.of(pagina, tamanio);
            libros = repositorioLibro.findByIdiomas(codigos, pageable);
            if (libros.isEmpty() && pagina == 0) {
                System.out.println("--------");
                System.out.println(String.format("-------- No existen libros registrados."));
                System.out.println("--------");
                return;
            }

            System.out.println("--------");
            System.out.println("\nPágina " + (pagina + 1) + " de " + libros.getTotalPages());
            System.out.println("--------");
            libros.getContent().forEach(libro -> imprimeLibro(libro));

            if (!libros.isLast()) {
                System.out.println("\nPresiona 'c' o 'q' seguido de Enter para continuar o para salir:");
                String entrada = scanner.nextLine();
               if (entrada.equalsIgnoreCase("q")) {
                   System.out.println("--------");
                    System.out.println(String.format("-------- Has salido de la paginación."));
                   System.out.println("--------");
                    break;
                }
            }

            pagina++;

        } while (!libros.isLast());
    }

    private void imprimeLibro(Libro libro) {
        String info = "\n" +
                    "Titulo: " + libro.getTitulo() +  "\n" +
                    "Autor(es): " + imprimeAutores(libro.getAutores()) + "\n" +
                    "Idioma(s): " + libro.getIdiomas() + "\n" +
                    "Total de descargas: " + libro.getDescargas() + "\n";
        System.out.println( String.format(info) );
    }

    private String imprimeAutores(List<Persona> autores) {
        if (autores == null || autores.isEmpty()) {
            return "";
        }

        if (autores.size() == 1) {
            return autores.get(0).getNombre();
        }

        StringBuilder nombre = new StringBuilder("[ ");
        for (int i = 0; i < autores.size(); i++) {
            nombre.append(autores.get(i).getNombre());
            if (i < autores.size() - 1) {
                nombre.append(" <--> ");
            }
        }
        nombre.append(" ]");

        return nombre.toString();
    }

    private void imprimeAutor(Persona autor) {
        String info = "\n" +
                "Autor: " + autor.getNombre() +  "\n" +
                "Fecha de nacimiento: " + autor.getAnioNacimiento() + "\n" +
                "Fecha de fallecimiento: " + autor.getAnioFallecimiento() + "\n" +
                "Libro(s): " + imprimeLibros(autor.getLibros()) + "\n";
        System.out.println( String.format(info) );
    }

    private String imprimeLibros(List<Libro> libros) {
        if (libros == null || libros.isEmpty()) {
            return "";
        }

        if (libros.size() == 1) {
            return libros.get(0).getTitulo();
        }

        StringBuilder titulo = new StringBuilder("[ ");
        for (int i = 0; i < libros.size(); i++) {
            titulo.append(libros.get(i).getTitulo());
            if (i < libros.size() - 1) {
                titulo.append(" <--> ");
            }
        }
        titulo.append(" ]");

        return titulo.toString();
    }

}
