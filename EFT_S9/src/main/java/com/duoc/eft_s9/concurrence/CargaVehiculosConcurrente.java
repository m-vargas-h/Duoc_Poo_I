package com.duoc.eft_s9.concurrence;

import com.duoc.eft_s9.service.GestorVehiculos;

public class CargaVehiculosConcurrente implements Runnable {
    private final GestorVehiculos gestor;
    private final boolean cargaPasajeros;

    public CargaVehiculosConcurrente(GestorVehiculos gestor, boolean cargaPasajeros) {
        this.gestor = gestor;
        this.cargaPasajeros = cargaPasajeros;
    }

    @Override
    public void run() {
        try {
            if (cargaPasajeros) {
                System.out.println("🧵 Hilo de carga de vehículos de pasajeros iniciado...");
                Thread.sleep(500); // Simula tiempo de carga
                gestor.cargarVehiculosPasajeros();
                System.out.println("Hilo finalizado: pasajeros (" + gestor.obtenerVehiculosDisponibles().size() + 
                                                                " disponibles)");
            } else {
                System.out.println("🧵 Hilo de carga de vehículos de carga iniciado...");
                Thread.sleep(500); // Simula tiempo de carga
                gestor.cargarVehiculosCarga();
                System.out.println("Hilo finalizado: carga (" + gestor.obtenerVehiculosDisponibles().size() + 
                                                            " disponibles)");
            }
        } catch (InterruptedException e) {
            System.out.println("Error en hilo de carga: " + e.getMessage());
        }
    }
}