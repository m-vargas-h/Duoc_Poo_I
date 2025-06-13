package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.excepcion.UsuarioNoEncontradoException;
import com.mycompany.exp2_s4.excepcion.LibroNoEncontradoException;
import com.mycompany.exp2_s4.excepcion.LibroNoDisponibleException;

import java.util.Scanner;

/**
 * Submenú para solicitar (prestar) un libro.
 */
public class SolicitarLibroMenu {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public SolicitarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n--- Solicitar Libro ---");
        System.out.print("ID de usuario: ");
        String usuarioId = sc.nextLine().trim();
        System.out.print("Título del libro: ");
        String titulo    = sc.nextLine().trim();

        try {
            svc.solicitarLibro(usuarioId, titulo);
            System.out.println("Préstamo realizado con éxito.");
        } catch (UsuarioNoEncontradoException |
                 LibroNoEncontradoException |
                 LibroNoDisponibleException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
