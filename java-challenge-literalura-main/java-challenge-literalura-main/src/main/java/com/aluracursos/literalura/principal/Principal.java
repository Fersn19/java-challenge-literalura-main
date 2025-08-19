package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.service.LibroService;
import com.aluracursos.literalura.utils.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Principal {
    Menus menu = new Menus();

    @Autowired
    LibroService servicio;


    public void muestraMenu() {
        menu.imprimeBanner();
        var opcion = -1;
        while (opcion != 0) {
            opcion = menu.menuPrincipal();
            if ( opcion != 0) {
                procesaOpcion(opcion);
            } else {
                menu.despedida();
            }
        }
    }

    public void procesaOpcion(int opcion) {
        switch (opcion) {
            case 1:  //Buscar libro por título, toma la info de Gutendex y la pasa a la BD (sino existe el registro)
                System.out.println("Por favor escribe el nombre del libro que deseas buscar: ");
                String nombreLibro = menu.obtenDatosTeclado();
                servicio.buscarLibroPortitulo(nombreLibro);
                break;
            case 2:  //Listar libros registrados (en BD)
                servicio.buscarTodosLosLibrosRegistrados();
                break;
            case 3:  //Listar autores registrados (en BD)
                servicio.buscarAutoresRegistrados();
                break;
            case 4:  //Listar autores vivos en un determinado año  (en BD)
                System.out.println("Por favor escribe el Año en que vivia el autor: ");
                Integer year = menu.validaEsEntero( menu.obtenDatosTeclado() );
                if ( year != -1 ) {
                    servicio.buscarAutoresRegistradosPorAño(year);
                }
                break;
            case 5:  //Listar libros por idiomas (en BD)
                servicio.obtenerTodosLosIdiomas();
                System.out.println("Por favor escribe el idioma(s) que desea consultar: ");
                String misIdiomas = menu.obtenDatosTeclado();
                servicio.buscarLibrosPorIdiomas(misIdiomas);
                break;
            case 6:
                servicio.obtenerTodosLosIdiomas();
                System.out.println("Por favor escribe el idioma(s) que desea consultar: ");
                String masIdiomas = menu.obtenDatosTeclado();
                servicio.buscarLibrosPorIdiomasPaginado(masIdiomas);
                break;
            default:
                menu.mensajeCorto("  Opción inválida");
        }
    }
}
