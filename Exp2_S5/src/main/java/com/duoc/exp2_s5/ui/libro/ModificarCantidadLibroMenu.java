package com.duoc.exp2_s5.ui.libro;

import com.duoc.exp2_s5.modelo.Libro;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ModificarCantidadLibroMenu {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public ModificarCantidadLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    public void mostrar() {
        System.out.println("\n----- MODIFICAR CANTIDAD DE LIBRO (AYUDANTE) -----");

        // Obtener la lista de libros desde la biblioteca
        List<Libro> libros = svc.getBiblioteca().getLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles para modificar.");
            return;
        }

        // Mostrar la lista de libros con su índice y datos relevantes
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            System.out.printf("%d) Título: %s, Autor: %s, Total: %d, Disponibles: %d%n", 
                i + 1, 
                libro.getNombre(), 
                libro.getAutor(),
                libro.getTotalCopias(), 
                libro.getCopiasDisponibles());
        }

        // Pedir al ayudante que seleccione el libro que desea modificar
        System.out.print("Seleccione el número del libro para modificar su cantidad: ");
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

        // Verificar que el libro no tenga copias prestadas
        // Es decir, que el total de copias sea igual a las copias disponibles
        if (libroSeleccionado.getTotalCopias() > libroSeleccionado.getCopiasDisponibles()) {
            System.out.println("No se puede modificar la cantidad, el libro tiene copias prestadas.");
            return;
        }

        // Mostrar el stock actual y pedir el nuevo total de copias
        System.out.printf("Total de copias actual: %d.%n", libroSeleccionado.getTotalCopias());
        System.out.print("Ingrese el nuevo total de copias: ");
        String nuevoTotalStr = sc.nextLine().trim();

        if (nuevoTotalStr.isEmpty()) {
            System.out.println("No se ingresó un nuevo valor. Operación cancelada.");
            return;
        }

        int nuevoTotal;
        try {
            nuevoTotal = Integer.parseInt(nuevoTotalStr);
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido.");
            return;
        }

        if (nuevoTotal < 1) {
            System.out.println("El total de copias debe ser al menos 1.");
            return;
        }

        // Como el libro no tiene préstamos (disponibles == total), 
        // se actualizan ambos campos.
        try {
            libroSeleccionado.setTotalCopias(nuevoTotal);
            libroSeleccionado.setCopiasDisponibles(nuevoTotal);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Guardar los cambios en el catálogo (actualizar CSV)
        try {
            svc.guardarLibros();
            System.out.println("Cantidad modificada exitosamente.");
        } catch (IOException ex) {
            System.err.println("Error al guardar cambios: " + ex.getMessage());
        }
    }
}