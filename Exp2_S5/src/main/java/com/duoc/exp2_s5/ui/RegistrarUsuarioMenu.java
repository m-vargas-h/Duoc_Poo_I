package com.duoc.exp2_s5.ui;

import com.duoc.exp2_s5.excepcion.UsuarioYaExisteException;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.io.IOException;
import java.util.Scanner;

/*
 * Submenu para registrar un nuevo usuario
 * Este menú permite al usuario ingresar su ID, nombre, carrera y sede.
 * Se valida la entrada y se maneja la excepción si el usuario ya existe.
 * si el registro es exitoso, se guarda en disco.
 */
public class RegistrarUsuarioMenu {

    private final ServiciosBiblioteca svc;
    private final Scanner sc;

    public RegistrarUsuarioMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    public void mostrar() {
        System.out.println("\n----- Registrar Usuario -----");
        System.out.print("Ingrese su ID (RUT, sin puntos y con guion, ej. 19133090-0): ");
        String id = sc.nextLine().trim();

        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();

        // Lista predefinida de carreras
        String[] carreras = {
            "ANALISTA PROGRAMADOR COMPUTACIONAL",
            "INGENIERIA COMERCIAL",
            "ADEMINISTRACION DE EMPRESAS",
            "ADMINISTRACION PUBLICA",
            "INGENIERIA EN CONSTRUCCION",
            "INGENIERIA CIVIL INDUSTRIAL"
        };
        
        // Mostrar las carreras disponibles
        System.out.println("\nSeleccione su carrera:");
        for (int i = 0; i < carreras.length; i++) {
            System.out.println((i + 1) + ". " + carreras[i]);
        }
        int seleccionCarrera = Integer.parseInt(sc.nextLine());
        if(seleccionCarrera < 1 || seleccionCarrera > carreras.length) {
            System.err.println("Selección inválida para carrera.");
            return;
        }
        String carrera = carreras[seleccionCarrera - 1];

        // Lista predefinida de sedes
        String[] sedes = {
            "SEDE MAIPU",
            "SEDE PUERTO MONTT",
            "SEDE ALAMEDA",
            "CAMPUS VIRTUAL"
        };

        // Mostrar las sedes disponibles
        System.out.println("\nSeleccione la sede:");
        for (int i = 0; i < sedes.length; i++) {
            System.out.println((i + 1) + ". " + sedes[i]);
        }
        int seleccionSede = Integer.parseInt(sc.nextLine());
        if(seleccionSede < 1 || seleccionSede > sedes.length) {
            System.err.println("Selección inválida para sede.");
            return;
        }
        String sede = sedes[seleccionSede - 1];

        // Intentar registrar el usuario
        try {
            svc.registrarUsuario(id, nombre, carrera, sede);
            System.out.println("Usuario registrado con éxito.");
        } catch (UsuarioYaExisteException e) {
            System.err.println("Ya existe: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error guardando en disco: " + e.getMessage());
        }
    }
}