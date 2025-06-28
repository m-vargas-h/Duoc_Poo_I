package com.duoc.exp2_s6.ui.usuarios;

import com.duoc.exp2_s6.servicio.ServiciosBiblioteca;
import com.duoc.exp2_s6.modelo.*;
import com.duoc.exp2_s6.excepcion.UsuarioNoEncontradoException;
import com.duoc.exp2_s6.excepcion.LibroNoEncontradoException;
import com.duoc.exp2_s6.excepcion.CopiaInvalidaException;
import com.duoc.exp2_s6.excepcion.LibroNoPrestadoException;

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
    
}