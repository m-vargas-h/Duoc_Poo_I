package com.duoc.exp2_s6.ui;

import java.util.Scanner;

public class MenuUsuario {
    private final Scanner scanner = new Scanner(System.in);

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    System.out.println("▶ Registrar usuario (no implementado aún)");
                    break;

                case 2:
                    System.out.println("▶ Buscar producto (no implementado aún)");
                    break;

                case 3:
                    System.out.println("▶ Comprar producto (no implementado aún)");
                    break;

                case 4:
                    System.out.println("▶ Reservar producto (no implementado aún)");
                    break;

                case 5:
                    System.out.println("▶ Consultar estado de reservas (no implementado aún)");
                    break;

                case 6:
                    System.out.println("👋 Cerrando sesión de usuario...");
                    break;

                default:
                    System.out.println("❌ Opción no válida.");
                    break;
            }

        } while (opcion != 6);
        
    }

    private void imprimirMenu() {
        System.out.println("""
        ========== MENÚ USUARIO ==========
        1. Registrar usuario
        2. Buscar producto
        3. Comprar producto
        4. Reservar producto
        5. Consultar estado de reservas
        6. Salir
        ===================================
        """);
    }

    private int leerOpcion() {
        try {
            System.out.print("Seleccione una opción: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}