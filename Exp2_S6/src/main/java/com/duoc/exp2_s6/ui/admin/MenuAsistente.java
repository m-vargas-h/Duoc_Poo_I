package com.duoc.exp2_s6.ui.admin;

import com.duoc.exp2_s6.servicio.ServiciosBiblioteca;
import com.duoc.exp2_s6.ui.libro.AgregarLibroMenu;
import com.duoc.exp2_s6.ui.libro.ModificarCantidadLibroMenu;

import java.util.Scanner;

public class MenuAsistente {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public MenuAsistente(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    // Muestra el menú del asistente y maneja las opciones
    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n===== MENÚ ASISTENTE =====");
            System.out.println("1. Agregar libro");
            System.out.println("2. Editar stock de libro");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            // Validar entrada y convertir a entero
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }
            
            switch (opcion) {
                case 1:
                    new AgregarLibroMenu(svc, sc).mostrar();
                    break;
                case 2:
                    new ModificarCantidadLibroMenu(svc, sc).mostrar();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
            
        } while (opcion != 0);
    }
}
