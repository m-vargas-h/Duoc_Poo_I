package com.duoc.exp2_s6.ui;

import com.duoc.exp2_s6.servicio.ComicCollectorService;
import java.util.Scanner;

public class MenuPrincipal {

    private final ComicCollectorService servicio;
    private final Scanner scanner;

    public MenuPrincipal(Scanner scanner, ComicCollectorService servicio) {
        this.scanner = scanner;
        this.servicio = servicio;
    }

    public void mostrar() {
        int opcion;

        do {
            imprimirBienvenida();
            opcion = leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    new MenuAdministrador(scanner, servicio).login();
                    break;

                case 2:
                    new MenuUsuario(servicio).mostrar();
                    break;

                case 3:
                    System.out.println("Cerrando sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 3);
    }

    private void imprimirBienvenida() {
        System.out.println("""
            ========== COMIC COLLECTOR SYSTEM ==========
            1. Ingresar como Administrador
            2. Ingresar como Usuario
            3. Salir del sistema
            ===========================================
            """);
    }

    private int leerOpcion(Scanner scanner) {
        System.out.print("Seleccione su rol: ");
        String linea = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}