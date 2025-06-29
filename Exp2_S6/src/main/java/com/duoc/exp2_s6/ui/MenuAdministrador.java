package com.duoc.exp2_s6.ui;

import java.util.Scanner;

public class MenuAdministrador {
    private final Scanner scanner = new Scanner(System.in);

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    System.out.println("▶ Agregar producto (no implementado aún)");
                    break;

                case 2:
                    System.out.println("▶ Eliminar producto (no implementado aún)");
                    break;

                case 3:
                    System.out.println("▶ Listar usuarios (no implementado aún)");
                    break;

                case 4:
                    System.out.println("▶ Modificar stock (no implementado aún)");
                    break;

                case 5:
                    System.out.println("▶ Cambiar estado de producto (no implementado aún)");
                    break;

                case 6:
                    System.out.println("▶ Ver resumen de ventas (no implementado aún)");
                    break;

                case 7:
                    System.out.println("🔙 Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("❌ Opción no válida.");
                    break;
            }

        } while (opcion != 7);
        
    }

    private void imprimirMenu() {
        System.out.println("""
        ========== MENÚ ADMINISTRADOR ==========
        1. Agregar producto al inventario
        2. Eliminar producto
        3. Listar usuarios registrados
        4. Modificar stock de producto
        5. Cambiar estado de producto
        6. Ver resumen de ventas
        7. Salir al menú principal
        =========================================
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