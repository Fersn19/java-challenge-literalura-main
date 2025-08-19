package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private Long idLibro;

    @Column(unique = true, length = 500)
    private String titulo;

    //@ElementCollection(fetch = FetchType.EAGER)
    //@CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "id_libro"))
    //@Column(name = "idioma")
    //private List<String> idiomas;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_idiomas",
            joinColumns = @JoinColumn(name = "id_libro"),
            inverseJoinColumns = @JoinColumn(name = "codigo")  // <- DEBE ser el campo que es @Id en la entidd Idioma
    )
    private List<Idioma> idiomas;

    private Long descargas;
    private boolean copyright;

    //@ManyToMany(mappedBy = "libros", cascade=CascadeType.ALL, fetch = FetchType.EAGER)  //IMPORTANTE: el campo libro es el campo que pertenece a la clase autores. La carga es de tipo anciosa y no perezosa
		@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)	
		@JoinTable(
				name = "persona_libro",
				joinColumns = @JoinColumn(name = "libro_id"),
				inverseJoinColumns = @JoinColumn(name = "persona_id")
		)
    private List<Persona> autores;


    //Contructor ppor default
    public Libro() {

    }

    public Libro(DatosResultadosLibro libro) {
        this.idLibro = libro.idLibro();
        this.titulo = ( libro.titulo().length() > 500 ? libro.titulo().substring(0,499) : libro.titulo() );
        this.idiomas = libro.idiomas().stream().map(elIdioma -> new Idioma(elIdioma, null)).collect(Collectors.toUnmodifiableList());
        this.descargas = libro.descargas();
        this.copyright = libro.copyright();

        this.autores = libro.autores().stream().map(autor ->  new Persona(autor)).collect(Collectors.toList());
    }

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public List<Persona> getAutores() {
        return autores;
    }

    public void setAutores(List<Persona> autores) {
        List<Libro> losLibros = autores.stream().map(libro ->  this).collect(Collectors.toList());
        autores.forEach(a -> a.setLibros(losLibros));  //guarda los datos de esta autor (del  libro actual)
        this.autores = autores;
    }

    @Override
    public String toString() {
        return  "idLibro=" + this.idLibro +
                ", titulo='" + this.titulo +
                ", autores=" + this.autores +
                ", idiomas=" + this.idiomas +
                ", descargas=" + this.descargas +
                ", copyright=" + this.copyright;
    }

    /*
    Código	Nombre del idioma
        en	Inglés
        tl	Tagalo
        enm	Inglés medio
        fi	Finés / Finlandés
        pt	Portugués
        hu	Húngaro
        is	Islandés
        pl	Polaco
        el	Griego
        zh	Chino
        it	Italiano
        es	Español
        la	Latín
        de	Alemán
        eo	Esperanto
        nl	Neerlandés / Holandés
        fr	Francés
     */
}
