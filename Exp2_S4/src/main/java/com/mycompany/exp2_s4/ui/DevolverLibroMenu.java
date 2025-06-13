package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.excepcion.UsuarioNoEncontradoException;
import com.mycompany.exp2_s4.excepcion.LibroNoEncontradoException;
import com.mycompany.exp2_s4.excepcion.CopiaInvalidaException;
import com.mycompany.exp2_s4.excepcion.LibroNoPrestadoException;

import java.util.Scanner;

/**
 * Submenú para devolver un libro.
 */
public class DevolverLibroMenu {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public DevolverLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n--- Devolver Libro ---");
        System.out.print("ID de usuario: ");
        String usuarioId = sc.nextLine().trim();
        System.out.print("Título del libro: ");
        String titulo    = sc.nextLine().trim();

        try {
            svc.devolverLibro(usuarioId, titulo);
            System.out.println("Devolución realizada con éxito.");
        } catch (UsuarioNoEncontradoException |
                 LibroNoEncontradoException |
                 CopiaInvalidaException |
                 LibroNoPrestadoException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}