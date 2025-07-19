package com.duoc.eft_s9;

import com.duoc.eft_s9.service.GestorBoletas;
import com.duoc.eft_s9.ui.MenuPrincipal;
import com.duoc.eft_s9.concurrence.CargaVehiculosConcurrente;
import com.duoc.eft_s9.service.GestorVehiculos;

public class EFT_S9 {
    public static void main(String[] args) {
        GestorVehiculos gestor = new GestorVehiculos();

        // Lanzar hilos de carga en paralelo
        Thread hiloCarga = new Thread(new CargaVehiculosConcurrente(gestor, false));
        Thread hiloPasajeros = new Thread(new CargaVehiculosConcurrente(gestor, true));

        hiloCarga.start();
        hiloPasajeros.start();

        try {
            hiloCarga.join();
            hiloPasajeros.join();
        } catch (InterruptedException e) {
            System.out.println("Error esperando hilos: " + e.getMessage());
        }

        // Iniciar menú con gestor ya cargado
        MenuPrincipal menu = new MenuPrincipal(gestor);
        GestorBoletas.cargarHistorial();
        menu.mostrarMenu();
    }
}