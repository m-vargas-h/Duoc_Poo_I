
package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import java.util.Scanner;

public class MenuPrincipal {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public MenuPrincipal(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void iniciar() {
        int opcion = 0;
        while (opcion != 6) {

            // Mostrar menú
            System.out.println("\n===== MENÚ BIBLIOTECA =====");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Solicitar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Consultar libro");
            System.out.println("5. Consultar estado usuario");
            System.out.println("6. Salir");
            System.out.print("Selecciona opción: ");

            // Leer opción
            String linea = sc.nextLine().trim();
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            // Procesar opción
            switch (opcion) {
                case 1:
                    new RegistrarUsuarioMenu(svc, sc).mostrar();
                    break;

                case 2:
                    new SolicitarLibroMenu(svc, sc).mostrar();
                    break;

                case 3:
                    new DevolverLibroMenu(svc, sc).mostrar();
                    break;

                case 4:
                    new ConsultarLibroMenu(svc, sc).mostrar();
                    break;

                case 5:
                    new ConsultarEstadoUsuarioMenu(svc, sc).mostrar();
                    break; 

                case 6:
                    System.out.println("¡Hasta luego!");
                    break;
                    
                default:
                    System.err.println("Opción inválida, inténtalo de nuevo.");
            }
        }
    }
}