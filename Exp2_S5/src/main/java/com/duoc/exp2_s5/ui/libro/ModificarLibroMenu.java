package com.duoc.exp2_s5.ui.libro;

import com.duoc.exp2_s5.modelo.Libro;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ModificarLibroMenu {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public ModificarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    // Muestra el menú para modificar un libro y maneja las opciones
    public void mostrar() {
        System.out.println("\n----- MODIFICAR LIBRO -----");

        // Obtener la lista de libros desde la biblioteca
        List<Libro> libros = svc.getBiblioteca().getLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles para modificar.");
            return;
        }

        // Mostrar la lista de libros con índice para selección
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            System.out.printf("%d) Título: %s, Autor: %s%n", i + 1, libro.getNombre(), libro.getAutor());
        }

        System.out.print("Seleccione el número del libro a modificar: ");
        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Entrada no válida.");
            return;
        }
        if (opcion < 1 || opcion > libros.size()) {
            System.out.println("Opción fuera de rango.");
            return;
        }

        Libro libroSeleccionado = libros.get(opcion - 1);

        // Verifica si el libro tiene copias prestadas.
        // Si el total de copias es mayor que las disponibles, significa que hay una o más copias prestadas.
        if (libroSeleccionado.getTotalCopias() > libroSeleccionado.getCopiasDisponibles()) {
            System.out.println("No se puede modificar el libro porque tiene copias prestadas.");
            return;
        }

        // Pedir nueva información; se permite dejar el campo vacío para conservar el valor actual.
        System.out.println("Ingrese la nueva información del libro. (Deje vacío para mantener el valor actual)");

        // Recolectar datos del libro
        System.out.printf("Título actual: %s%nNuevo título: ", libroSeleccionado.getNombre());
        String nuevoTitulo = sc.nextLine().trim();
        if (!nuevoTitulo.isEmpty()) {
            libroSeleccionado.setNombre(nuevoTitulo);
        }

        System.out.printf("Autor actual: %s%nNuevo autor: ", libroSeleccionado.getAutor());
        String nuevoAutor = sc.nextLine().trim();
        if (!nuevoAutor.isEmpty()) {
            libroSeleccionado.setAutor(nuevoAutor);
        }

        System.out.printf("Clasificación actual: %s%nNueva clasificación: ", libroSeleccionado.getClasificacion());
        String nuevaClasificacion = sc.nextLine().trim();
        if (!nuevaClasificacion.isEmpty()) {
            libroSeleccionado.setClasificacion(nuevaClasificacion);
        }

        System.out.printf("Editorial actual: %s%nNueva editorial: ", libroSeleccionado.getEditorial());
        String nuevaEditorial = sc.nextLine().trim();
        if (!nuevaEditorial.isEmpty()) {
            libroSeleccionado.setEditorial(nuevaEditorial);
        }

        // Dado que el libro no tiene préstamos (total == disponible), permitimos modificar el stock.
        System.out.printf("Total de copias actual: %d%nNuevo total de copias: ", libroSeleccionado.getTotalCopias());
        String totalCopiasStr = sc.nextLine().trim();
        if (!totalCopiasStr.isEmpty()) {
            try {
                int nuevoTotal = Integer.parseInt(totalCopiasStr);
                if (nuevoTotal < 1) {
                    System.out.println("El total de copias debe ser al menos 1.");
                    return;
                }
                // Al no haber préstamos, asignamos directamente el nuevo total a ambos campos.
                libroSeleccionado.setTotalCopias(nuevoTotal);
                libroSeleccionado.setCopiasDisponibles(nuevoTotal);
            } catch (NumberFormatException e) {
                System.out.println("Valor numérico inválido para el total de copias.");
                return;
            }
        }

        // Persistir los cambios en el catálogo mediante el servicio.
        try {
            svc.guardarLibros();
            System.out.println("Libro modificado exitosamente.");
        } catch (IOException ex) {
            System.err.println("Error al guardar cambios: " + ex.getMessage());
        }
    }
}