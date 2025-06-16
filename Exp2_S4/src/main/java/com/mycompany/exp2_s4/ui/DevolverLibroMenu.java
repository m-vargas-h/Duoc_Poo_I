package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.modelo.Usuario;
import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.excepcion.UsuarioNoEncontradoException;
import com.mycompany.exp2_s4.excepcion.LibroNoEncontradoException;
import com.mycompany.exp2_s4.excepcion.CopiaInvalidaException;
import com.mycompany.exp2_s4.excepcion.LibroNoPrestadoException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Submenu para devolver un libro
public class DevolverLibroMenu {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public DevolverLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n--- Devolver Libro ---");
        System.out.print("ID de usuario (Su RUT tal como lo registró): ");
        String usuarioId = sc.nextLine().trim();

        // Obtener los libros en préstamo del usuario
        Usuario usuario;
        try {
            usuario = svc.consultarUsuario(usuarioId);
        } catch (UsuarioNoEncontradoException e) {
            System.err.println("ERROR: " + e.getMessage());
            return;
        }

        List<Libro> librosPrestados = usuario.getLibrosPrestados();

        if (librosPrestados.isEmpty()) {
            System.out.println("No tiene libros en préstamo.");
            return;
        }

        // Mostrar los títulos con numeración para selección
        System.out.println("\nLibros actualmente en préstamo:");
        for (int i = 0; i < librosPrestados.size(); i++) {
            System.out.println((i + 1) + ". " + librosPrestados.get(i).getNombre());
        }

        // Solicitar al usuario que elija un número
        System.out.print("\nSeleccione el número del libro que desea devolver: ");
        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
            if (opcion < 1 || opcion > librosPrestados.size()) {
                System.out.println("Opción inválida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
            return;
        }

        // Obtener el título del libro seleccionado
        String titulo = librosPrestados.get(opcion - 1).getNombre();

        // Intentar devolver el libro
        try {
            svc.devolverLibro(usuarioId, titulo);
            System.out.println("Devolución realizada con éxito.");
        } catch (UsuarioNoEncontradoException e) { // Capturamos la excepción faltante
            System.err.println("ERROR: " + e.getMessage());
        } catch (LibroNoEncontradoException |
                CopiaInvalidaException |
                LibroNoPrestadoException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (IOException io) {
            System.err.println("ERROR de E/S al actualizar stock: " + io.getMessage());
        }

    }
    
    /*    // Método para mostrar el menú de devolución de libro
    public void mostrar() {
        System.out.println("\n--- Devolver Libro ---");
        System.out.print("ID de usuario (Su rut tal como lo registró): ");
        String usuarioId = sc.nextLine().trim();

        
        System.out.print("Título del libro: ");
        String titulo    = sc.nextLine().trim();

        // Intentar devolver el libro
        // Se captura la excepción si el usuario no existe, el libro no se encuentra,
        // la copia es inválida o el libro no está prestado.
        try {
            svc.devolverLibro(usuarioId, titulo);
            System.out.println("Devolución realizada con éxito.");
        } catch (UsuarioNoEncontradoException |
                 LibroNoEncontradoException |
                 CopiaInvalidaException |
                 LibroNoPrestadoException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (IOException io) {
            System.err.println("ERROR de E/S al actualizar stock: " + io.getMessage());
        }

    } */
}