package com.mycompany.exp2_s4.ui;

import com.mycompany.exp2_s4.servicio.Biblioteca;
import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.excepcion.UsuarioYaExisteException;

import java.util.Scanner;
import java.io.IOException;

public class RegistrarUsuarioMenu {
    Biblioteca bib = new Biblioteca();  
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public RegistrarUsuarioMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n--- Registrar Usuario ---");
        System.out.print("ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Carrera: ");
        String carrera = sc.nextLine().trim();
        System.out.print("Sede: ");
        String sede = sc.nextLine().trim();

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