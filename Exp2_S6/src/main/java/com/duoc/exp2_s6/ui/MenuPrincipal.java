
package com.duoc.exp2_s6.ui;

import com.duoc.exp2_s6.ui.usuarios.*;
import com.duoc.exp2_s6.ui.admin.*;
import com.duoc.exp2_s6.modelo.Admin;
import com.duoc.exp2_s6.persistencia.PersistenciaInfo;
import com.duoc.exp2_s6.servicio.ServiciosBiblioteca;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public MenuPrincipal(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    // Muestra el menú principal y maneja las opciones del usuario
    public void iniciar() {
        int opcion = -1;
        while (opcion != 6) {

            // Saludo de bienvenida
            System.out.println("¡Bienvenido a la Biblioteca!");
            System.out.println("Por favor, selecciona una opción del menú:");


            // Mostrar menú
            System.out.println("\n----- MENÚ BIBLIOTECA -----");
            System.out.println("0. INGRESO ADMINISTRADOR");
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
                case 0:
                    accesoAdministrativo();
                    break;
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

    // Método para manejar el acceso administrativo
    private void accesoAdministrativo() {
        System.out.println("\n----- ACCESO ADMINISTRATIVO -----");
        System.out.print("Ingrese su ID (RUT sin puntos y con guion): ");
        String rut = sc.nextLine().trim().toLowerCase(); // evitamos errores de formato por ingresar k o K
        System.out.print("Ingrese su clave: ");
        String clave = sc.nextLine().trim();

        try {
            Map<String, Admin> credenciales = PersistenciaInfo.cargarAdmin();

            //? [DEBUG] Descomentar para ver las credenciales cargadas
            //System.out.println("Debug: Credenciales cargadas: " + credenciales);
        
            Admin usuario = credenciales.get(rut);

            // Verificar si el usuario existe y si la clave es correcta
            if (usuario == null || !usuario.checkPassword(clave)) {
                System.out.println("✗ Credenciales incorrectas.");
                return;
            }

            // Si las credenciales son correctas, mostrar el menú correspondiente
            System.out.println("Bienvenido, " + usuario.getNombre());
            switch (usuario.getRole()) {
                case ADMIN:
                    new MenuAdmin(svc, sc).iniciar();      // menú exclusivo para administradores
                    break;
                case ASISTENTE:
                    new MenuAsistente(svc, sc).iniciar();  // menú con permisos limitados
                    break;
                default:
                    System.out.println("Rol no reconocido.");
            }

        } catch (IOException e) {
            System.err.println("Error cargando credenciales: " + e.getMessage());
        }
    }
}