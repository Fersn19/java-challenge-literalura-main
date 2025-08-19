package com.aluracursos.literalura.utils;

import java.util.Scanner;

public class Menus {
    private Scanner teclado = new Scanner(System.in);

    public String obtenDatosTeclado() {
        return teclado.nextLine();
    }

    public void imprimeBanner() {
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println("--------  🧠 🧠 🧠  Bienvenidos a LiterAlura - ScreenBook  🧠 🧠 🧠");
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        for (String linea : Constantes.banner) {
            System.out.println( String.format("%s%s%s", Constantes.purple, linea, Constantes.reset) );
        }
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
    }

    public int menuPrincipal() {
        int opcion = -1;
        while (opcion == -1) {
            System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
            System.out.println("Menú Principal");
            System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
            var menu = """
                    En Gutendex:
                    1 - Buscar libro por título 
                    
                    En la Base de Datos:
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Listar libros por idioma Paginado
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = validaEsEntero( obtenDatosTeclado() );
            if (opcion < 0 || opcion > 6) {
                opcion = -1;
                mensajeCorto("  Opción inválida");
            }
        }
        return opcion;
    }

    public Integer validaEsEntero(String valor) {
        Integer opcion;
        try {
            opcion = Integer.valueOf( valor );
        } catch (Exception e) {
            opcion = -1;
            mensajeCorto("  Opción inválida");
        }

        return opcion;
    }

    public void despedida() {
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println(Constantes.CS_LINEA_CORTA_IZQ +  String.format("%s%s%s", Constantes.purple, "🧠 Gracias por Usar a LiterAlura - ScreenBook 🧠", Constantes.reset));
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        for (String linea : Constantes.banner) {
            String.format("%s%s%s\n\n", Constantes.purple, linea, Constantes.reset);
        }
    }

    public void mensajeCorto(String mensaje) {
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_CORTA_IZQ + mensaje);

        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);
        System.out.println(Constantes.CS_LINEA_SHORT_IZQ);

    }
}
