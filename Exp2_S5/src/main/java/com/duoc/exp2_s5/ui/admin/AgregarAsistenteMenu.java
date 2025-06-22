package com.duoc.exp2_s5.ui.admin;

import com.duoc.exp2_s5.modelo.Admin;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.io.IOException;
import java.util.Scanner;

public class AgregarAsistenteMenu {
    private ServiciosBiblioteca svc;
    private Scanner sc;

    public AgregarAsistenteMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n----- AGREGAR ASISTENTE -----");
        System.out.print("RUT (sin puntos, con guion): ");
        String rut = sc.nextLine().trim().toLowerCase();
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Clave: ");
        String clave = sc.nextLine().trim();

        try {
            boolean ok = svc.agregarAsistente(rut, nombre, clave);
            if (ok) {
                System.out.println("Asistente agregado exitosamente.");
            } else {
                System.out.println("El RUT ya existe, no es posible agregarlo.");
            }
        } catch (IOException e) {
            System.err.println("Error al guardar credenciales: " + e.getMessage());
        }
    }
}