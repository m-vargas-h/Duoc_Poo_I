package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.excepcion.LibroNoEncontradoException;

import java.util.Scanner;

/**
 * Submenú para consultar la disponibilidad de un libro.
 */
public class ConsultarLibroMenu {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public ConsultarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n--- Consultar Libro ---");
        System.out.print("Título del libro: ");
        String titulo = sc.nextLine().trim();

        try {
            String estado = svc.consultarLibro(titulo);
            System.out.printf("Estado de '%s': %s%n", titulo, estado);
        } catch (LibroNoEncontradoException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
