package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.excepcion.*;

import java.io.IOException;
import java.util.Scanner;

// Submenu para solicitar un libro
public class SolicitarLibroMenu {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public SolicitarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n----- Solicitar Libro -----");
        System.out.print("ID de usuario (Su rut tal como lo registro): ");
        String usuarioId = sc.nextLine().trim();
        System.out.print("Título del libro: ");
        String titulo    = sc.nextLine().trim();

        // Intentar solicitar el libro
        try {
            svc.solicitarLibro(usuarioId, titulo);
            System.out.println("Préstamo realizado con éxito.");
        } catch (MaximoPrestamoException e) {
            System.err.println("Límite alcanzado: " + e.getMessage());
        } catch (UsuarioNoEncontradoException |
                LibroNoEncontradoException |
                LibroNoDisponibleException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (IOException io) {
            System.err.println("ERROR I/O: " + io.getMessage());
        }

    }
}
