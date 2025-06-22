package com.duoc.exp2_s5.ui.libro;

import com.duoc.exp2_s5.modelo.Libro;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class EliminarLibroMenu {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public EliminarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    public void mostrar() {
        System.out.println("\n----- ELIMINAR LIBRO -----");

        // Obtener la lista de libros desde la biblioteca
        List<Libro> libros = svc.getBiblioteca().getLibros(); // o svc.getLibros() si tienes ese método.
        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles para eliminar.");
            return;
        }

        // Mostrar la lista de libros con un índice para que el admin seleccione cuál eliminar
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            System.out.printf("%d) Título: %s, Autor: %s%n", i + 1, libro.getNombre(), libro.getAutor());
        }

        System.out.print("Seleccione el número del libro a eliminar: ");
        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return;
        }

        if (opcion < 1 || opcion > libros.size()) {
            System.out.println("Opción fuera de rango.");
            return;
        }

        Libro libroSeleccionado = libros.get(opcion - 1);
        
        // Validar si el libro tiene copias prestadas: 
        // Se asume que si el libro tiene préstamos activos, la cantidad de copias disponibles
        // será menor que el total. Por lo que:
        if (libroSeleccionado.getTotalCopias() > libroSeleccionado.getCopiasDisponibles()) {
            System.out.println("No se puede eliminar el libro, ya que tiene copias en préstamo.");
            return;
        }

        // Proceder a eliminar el libro
        boolean removido = svc.eliminarLibro(libroSeleccionado);
        if (removido) {
            System.out.println("Libro eliminado con éxito.");
            try {
                svc.guardarLibros();
            } catch (IOException e) {
                System.err.println("Error al guardar cambios: " + e.getMessage());
            }
        } else {
            System.out.println("Error al eliminar el libro.");
        }
    }
}