package com.duoc.exp2_s5.ui.admin;

import com.duoc.exp2_s5.modelo.Admin;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import com.duoc.exp2_s5.persistencia.PersistenciaInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EliminarAsistenteMenu {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public EliminarAsistenteMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc  = sc;
    }

    public void mostrar() {
        System.out.println("\n----- ELIMINAR ASISTENTE -----");
        Map<String, Admin> creds;
        try {
            creds = PersistenciaInfo.cargarAdmin();
        } catch (IOException e) {
            System.err.println("Error cargando credenciales: " + e.getMessage());
            return;
        }

        // Filtrar solo asistente
        List<Admin> asistentes = new ArrayList<>();
        for (Admin a : creds.values()) {
            if (a.getRole() == Admin.Role.ASISTENTE) {
                asistentes.add(a);
            }
        }

        if (asistentes.isEmpty()) {
            System.out.println("No hay asistentes registrados.");
            return;
        }

        // Mostrar lista numerada
        for (int i = 0; i < asistentes.size(); i++) {
            Admin a = asistentes.get(i);
            System.out.printf("%d) %s - %s%n", i + 1, a.getRut(), a.getNombre());
        }
        System.out.print("Seleccione el número de asistente a eliminar: ");
        int idx;
        try {
            idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }
        if (idx < 0 || idx >= asistentes.size()) {
            System.out.println("Opción fuera de rango.");
            return;
        }

        Admin seleccionado = asistentes.get(idx);
        System.out.printf("¿Confirma eliminar al asistente %s (%s)? [S/N]: ",
            seleccionado.getRut(), seleccionado.getNombre());
        String resp = sc.nextLine().trim().toUpperCase();
        if (!resp.equals("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        try {
            boolean ok = svc.eliminarAsistente(seleccionado.getRut());
            if (ok) {
                System.out.println("✓ Asistente eliminado exitosamente.");
            } else {
                System.out.println("✗ No se pudo eliminar (quizá ya no existe).");
            }
        } catch (IOException e) {
            System.err.println("Error guardando cambios: " + e.getMessage());
        }
    }
}