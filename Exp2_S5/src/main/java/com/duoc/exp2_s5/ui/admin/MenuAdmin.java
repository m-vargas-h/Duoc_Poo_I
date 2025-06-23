package com.duoc.exp2_s5.ui.admin;

import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import com.duoc.exp2_s5.ui.libro.AgregarLibroMenu;
import com.duoc.exp2_s5.ui.libro.EliminarLibroMenu;
import com.duoc.exp2_s5.ui.libro.ModificarLibroMenu;

import java.util.Scanner;

public class MenuAdmin {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public MenuAdmin(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    // Muestra el menú de administrador y maneja las opciones
    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Agregar bibliotecario");
            System.out.println("2. Eliminar bibliotecario");
            System.out.println("3. Agregar libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Editar libro");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            String linea = sc.nextLine().trim();

            // Validar entrada y convertir a entero
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    new AgregarAsistenteMenu(svc, sc).mostrar();
                    break;
                case 2:
                    new EliminarAsistenteMenu(svc, sc).mostrar();
                    break;
                case 3:
                    new AgregarLibroMenu(svc, sc).mostrar();
                    break;
                case 4:
                    new EliminarLibroMenu(svc, sc).mostrar();
                    break;
                case 5:
                    new ModificarLibroMenu(svc, sc).mostrar();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }
}