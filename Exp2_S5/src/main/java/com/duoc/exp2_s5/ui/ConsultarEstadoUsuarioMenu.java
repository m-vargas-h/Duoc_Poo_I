package com.duoc.exp2_s5.ui;

import com.duoc.exp2_s5.excepcion.UsuarioNoEncontradoException;
import com.duoc.exp2_s5.modelo.*;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.util.Scanner;

// Submenu para consultar el estado de un usuario
public class ConsultarEstadoUsuarioMenu {

    private final Scanner sc;
    private final ServiciosBiblioteca svc;

    // Constructor que recibe tanto el Servicio como la instancia de Scanner
    public ConsultarEstadoUsuarioMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    // Método mostrar() que se invoca desde el menú principal
    public void mostrar() {
        System.out.println("\n--- Consultar Usuario ---");
        System.out.print("Ingrese su ID (RUT, sin puntos y con guion, ej. 19133090-0): ");
        String id = sc.nextLine().trim();

        try {
            Usuario usuario = svc.consultarUsuario(id);
            System.out.println("\nInformación del usuario:");
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Carrera: " + usuario.getCarrera());
            System.out.println("Sede: " + usuario.getSede());

            System.out.println("\nLibros actualmente en préstamo:");
            
            // Verificamos si el usuario tiene libros prestados
            if (usuario.getLibrosPrestados().isEmpty()) {
                System.out.println("No tiene libros en préstamo.");
            } else {
                // Listamos los libros prestados
                for (Libro libro : usuario.getLibrosPrestados()) {
                    System.out.println("- " + libro.getNombre());
                }
            }
        } catch (UsuarioNoEncontradoException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}