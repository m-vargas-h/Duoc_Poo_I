package com.duoc.exp2_s6.ui;

import com.duoc.exp2_s6.servicio.ComicCollectorService;
import java.util.Scanner;

public class MenuPrincipal {
    public static void main(String[] args) {
        ComicCollectorService servicio = new ComicCollectorService();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            imprimirBienvenida();
            opcion = leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    // Inyectamos el servicio
                    new MenuAdministrador(servicio).mostrar();
                    break;

                case 2:
                    new MenuUsuario(servicio).mostrar();
                    break;

                case 3:
                    System.out.println("👋 Cerrando sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("❌ Opción no válida.");
            }

        } while (opcion != 3);
    }

    private static void imprimirBienvenida() {
        System.out.println("""
        ========== COMIC COLLECTOR SYSTEM ==========
        1. Ingresar como Administrador
        2. Ingresar como Usuario
        3. Salir del sistema
        ============================================
        """);
    }

    private static int leerOpcion(Scanner scanner) {
        try {
            System.out.print("Seleccione su rol: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}