package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.excepcion.UsuarioNoEncontradoException;
import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.modelo.Usuario;
import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import java.util.Scanner;

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
            if (usuario.getLibrosPrestados().isEmpty()) {
                System.out.println("No tiene libros en préstamo.");
            } else {
                for (Libro libro : usuario.getLibrosPrestados()) {
                    System.out.println(libro);
                }
            }
        } catch (UsuarioNoEncontradoException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}