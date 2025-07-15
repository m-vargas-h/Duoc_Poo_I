package com.duoc.eft_s9.ui;

import java.util.Scanner;

import com.duoc.eft_s9.interfaces.GeneradorBoleta;
import com.duoc.eft_s9.service.BoletaSimple;
import com.duoc.eft_s9.service.GestorVehiculos;


public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);
    private final GestorVehiculos gestor = new GestorVehiculos();
    private final GeneradorBoleta generadorBoleta = new BoletaSimple();

    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n-------------- MENÚ PRINCIPAL --------------");
            System.out.println("1. Agregar vehículo de carga");
            System.out.println("2. Agregar vehículo de pasajeros");
            System.out.println("3. Listar todos los vehículos");
            System.out.println("4. Filtrar vehículos por tipo");
            System.out.println("5. Generar boleta de arriendo");
            System.out.println("6. Ver cantidad de vehículos en arriendo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        agregarVehiculoCarga();
                        break;

                    case 2:
                        agregarVehiculoPasajeros();
                        break;

                    case 3:
                        listarVehiculos();
                        break;

                    case 4:
                        filtrarPorTipo();
                        break;

                    case 5:
                        generarBoleta();
                        break;

                    case 6:
                        verCantidadEnArriendo(); //todo: trabajar en el filtro de duración de arriendo
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente con un número.");
                opcion = -1;
            }

        } while (opcion != 0);
    }

    //todo: métodos auxiliares aun en construcción, revisar como se implementaran a futuro

    private void agregarVehiculoCarga() {
        System.out.println("Aqui ira la lógica para crear VehiculoCarga");
    }

    private void agregarVehiculoPasajeros() {
        System.out.println("Aqui ira la lógica para crear VehiculoPasajeros");
    }

    private void listarVehiculos() {
        gestor.listarVehiculos().forEach(System.out::println);
    }

    private void filtrarPorTipo() {
        System.out.println("Seleccione el tipo de vehículo a filtrar:");
    }

    private void generarBoleta() {
        System.out.println("Generando boleta de arriendo...");
    }

    private void verCantidadEnArriendo() {
        //todo: Lógica futura para aplicar filtro por duración de arriendo
        int cantidad = gestor.contarVehiculosEnArriendo();
        System.out.println("Cantidad de vehículos actualmente en arriendo: " + cantidad);
    }
}