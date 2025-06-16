package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import java.util.List;
import java.util.Scanner;

// Submenu para consultar libros en la biblioteca
public class ConsultarLibroMenu {

    private final Scanner sc;
    private final ServiciosBiblioteca svc;

    public ConsultarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    // Método para mostrar el menú de consulta de libros
    // Este método permite al usuario buscar un libro por su nombre o listar todos los libros disponibles.
    public void mostrar() {
        System.out.println("\n--- Consultar Libro ---");
        System.out.println("1. Buscar libro por nombre");
        System.out.println("2. Listar todos los libros");
        System.out.print("Seleccione una opción: ");
        String opcion = sc.nextLine().trim();

        switch (opcion) {
            case "1":
                buscarLibroPorNombre();
                break;
            case "2":
                listarTodosLosLibros();
                break;
            default:
                System.err.println("Opción no válida.");
        }
    }

    // Método para buscar un libro por su nombre
    private void buscarLibroPorNombre() {
        System.out.print("Ingrese el nombre del libro: ");
        String nombre = sc.nextLine().trim();
        try {
            // Se utiliza el método de consulta actual, que devuelve un String.
            String resultado = svc.consultarLibro(nombre);
            System.out.println("\nResultado de la búsqueda:");
            System.out.println(resultado);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Método para listar todos los libros disponibles en la biblioteca
    private void listarTodosLosLibros() {
        System.out.println("\nLista completa de libros:");
        List<Libro> libros = svc.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el catálogo.");
        } else { // Si hay libros, se imprimen sus detalles
            for (Libro libro : libros) {
                System.out.printf("Nombre: %s, Autor: %s, Disponibles: %d%n",
                        libro.getNombre(), libro.getAutor(), libro.getCopiasDisponibles());
            }
        }
    }
}
