package com.duoc.eft_s9.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.duoc.eft_s9.concurrence.CargaVehiculosConcurrente;
import com.duoc.eft_s9.enums.TipoVehiculo;
import com.duoc.eft_s9.exceptions.ArriendoPersistenciaException;
import com.duoc.eft_s9.exceptions.DiasArriendoInvalidosException;
import com.duoc.eft_s9.exceptions.HistorialLecturaException;
import com.duoc.eft_s9.exceptions.VehiculoNoDisponibleException;
import com.duoc.eft_s9.interfaces.GeneradorBoleta;
import com.duoc.eft_s9.model.ArriendoInfo;
import com.duoc.eft_s9.model.Vehiculo;
import com.duoc.eft_s9.model.VehiculoCarga;
import com.duoc.eft_s9.model.VehiculoPasajeros;
import com.duoc.eft_s9.service.*;
import com.duoc.eft_s9.utils.ArchivoVehiculoManager;
import com.duoc.eft_s9.utils.ValidadorFormato;


public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);
    private final GestorVehiculos gestor;

    // Constructor que recibe el gestor desde afuera (desde main)
    public MenuPrincipal(GestorVehiculos gestor) {
        this.gestor = gestor;
    }

    public void iniciar() {
        //gestor.cargarVehiculosDesdeArchivos(); // Carga vehículos al iniciar el menú

        //? [Debug]: Mostrar vehículos cargados al iniciar
        ////System.out.println("Vehículos cargados:");
        ////for (Vehiculo v : gestor.listarVehiculos()) {
        ////    System.out.println("- " + v.getPatente());
        ////}
        
        int opcion;

        do {
            System.out.println("\n-------------- MENÚ PRINCIPAL --------------");
            System.out.println("1. Agregar vehículo de carga");
            System.out.println("2. Agregar vehículo de pasajeros");
            System.out.println("3. Listar todos los vehículos");
            System.out.println("4. Filtrar vehículos por tipo");
            System.out.println("5. Generar boleta de arriendo");
            System.out.println("6. Ver cantidad de vehículos en arriendo");
            System.out.println("7. Filtrar boletas por fecha o patente");
            System.out.println("8. Finalizar arriendo de vehículo");
            System.out.println("9. Ver historial de arriendos");
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
                    
                    case 7:
                        GestorBoletas.verBoletasFiltradas();
                        break;

                    case 8:
                        finalizarArriendoVehiculo();
                        break;

                    case 9:
                        mostrarHistorialArriendos();
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

    private TipoVehiculo seleccionarTipoVehiculo() {
        TipoVehiculo[] tipos = TipoVehiculo.values();
        System.out.println("Seleccione el tipo de vehículo:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, tipos[i].name(), tipos[i].getCategoria());
        }

        try {
            System.out.print("Opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion >= 1 && opcion <= tipos.length) {
                return tipos[opcion - 1];
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }

        System.out.println("Opción no válida. Se utilizará CAMION por defecto.");
        return TipoVehiculo.CAMION;
    }


    private void agregarVehiculoCarga() {
        try {
            System.out.print("Ingrese patente (AB-1234 o ABCD-12): ");
            String input = scanner.nextLine();
            String patenteValidada = ValidadorFormato.validarPatente(input);

            if (patenteValidada == null) return; // formato inválido

            if (gestor.buscarVehiculoPorPatente(patenteValidada) != null) {
                System.out.println("Ya existe un vehículo con esa patente.");
                return;
            }

            System.out.print("Ingrese marca: ");
            String marca = scanner.nextLine().trim();

            System.out.print("Ingrese año: ");
            int anio = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Ingrese precio base: ");
            int precioBase = Integer.parseInt(scanner.nextLine().trim());

            TipoVehiculo tipo = seleccionarTipoVehiculo();

            System.out.print("Ingrese capacidad en toneladas: ");
            int capacidadToneladas = Integer.parseInt(scanner.nextLine().trim());

            VehiculoCarga carga = new VehiculoCarga(
                patenteValidada,
                marca,
                anio,
                precioBase,
                tipo,
                capacidadToneladas
            );

            if (gestor.agregarVehiculo(carga)) {
                ArchivoVehiculoManager.guardarVehiculoCarga(carga);
                System.out.println("Vehículo de carga agregado y guardado correctamente.");
            } else {
                System.out.println("No se pudo agregar el vehículo.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Error] uno de los campos numéricos es inválido.");
        } catch (Exception e) {
            System.out.println("Error al ingresar vehículo de carga: " + e.getMessage());
        }
    }

    private void agregarVehiculoPasajeros() {
        try {
            System.out.print("Ingrese patente (AB-1234 o ABCD-12): ");
            String input = scanner.nextLine();
            String patenteValidada = ValidadorFormato.validarPatente(input);

            if (patenteValidada == null) return;

            if (gestor.buscarVehiculoPorPatente(patenteValidada) != null) {
                System.out.println("Ya existe un vehículo con esa patente.");
                return;
            }

            System.out.print("Ingrese marca: ");
            String marca = scanner.nextLine().trim();

            System.out.print("Ingrese año: ");
            int anio = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Ingrese precio base: ");
            int precioBase = Integer.parseInt(scanner.nextLine().trim());

            TipoVehiculo tipo = seleccionarTipoVehiculo();

            System.out.print("Ingrese capacidad máxima de pasajeros: ");
            int capacidadMaxima = Integer.parseInt(scanner.nextLine().trim());

            VehiculoPasajeros pasajero = new VehiculoPasajeros(
                patenteValidada,
                marca,
                anio,
                precioBase,
                tipo,
                capacidadMaxima
            );

            if (gestor.agregarVehiculo(pasajero)) {
                ArchivoVehiculoManager.guardarVehiculoPasajeros(pasajero);
                System.out.println("Vehículo de pasajeros agregado y guardado correctamente.");
            } else {
                System.out.println("No se pudo agregar el vehículo.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Error] uno de los campos numéricos es inválido.");
        } catch (Exception e) {
            System.out.println("Error al ingresar vehículo de pasajeros: " + e.getMessage());
        }
    }


    private void listarVehiculos() {
        List<Vehiculo> lista = gestor.listarVehiculos();

        if (lista.isEmpty()) {
            System.out.println("No hay vehículos registrados actualmente.");
            return;
        }

        System.out.println("-------- LISTADO DE VEHÍCULOS --------");
        for (int i = 0; i < lista.size(); i++) {
            Vehiculo v = lista.get(i);
            System.out.printf("[%d] %s%n", i + 1, v);
        }
        System.out.println("---------------------------------------");
    }

    private void filtrarPorTipo() {
        TipoVehiculo[] tipos = TipoVehiculo.values();
        System.out.println("Seleccione el tipo de vehículo a filtrar:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, tipos[i].name(), tipos[i].getCategoria());
        }

        try {
            System.out.print("Opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion >= 1 && opcion <= tipos.length) {
                TipoVehiculo seleccionado = tipos[opcion - 1];

                List<Vehiculo> filtrados = gestor.listarVehiculos().stream()
                    .filter(v -> v.getTipoVehiculo().equals(seleccionado))
                    .toList();

                if (filtrados.isEmpty()) {
                    System.out.println("No hay vehículos registrados del tipo " + seleccionado.name());
                } else {
                    System.out.println("------ VEHÍCULOS TIPO " + seleccionado.name() + " ------");
                    for (Vehiculo v : filtrados) {
                        System.out.println(v);
                    }
                    System.out.println("----------------------------------");
                }
            } else {
                System.out.println("Opción fuera de rango.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
        }
    }

    private void generarBoleta() {
        System.out.println("\nGENERAR BOLETA DE ARRIENDO");

        try {
            System.out.print("Ingrese RUT del cliente: ");
            String rut = scanner.nextLine().trim();

            System.out.print("Ingrese nombre del cliente: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Ingrese la patente del vehículo: ");
            String patente = scanner.nextLine().trim().toUpperCase();

            Vehiculo vehiculo = gestor.buscarVehiculoPorPatente(patente);
            if (vehiculo == null) {
                System.out.println("Vehículo no encontrado.");
                return;
            }

            if (!vehiculo.isDisponible()) {
                throw new VehiculoNoDisponibleException("El vehículo ya está en arriendo. Debe ser devuelto antes de continuar.");
            }

            System.out.print("Ingrese días de arriendo: ");
            int dias = obtenerDiasDeArriendo();

            GeneradorBoleta generador = new BoletaSimple();
            generador.generarBoleta(vehiculo, dias);

            gestor.guardarTodosLosVehiculos();

            String idBoleta = GestorBoletas.obtenerUltimaBoletaID();
            ArriendoInfo arriendo = new ArriendoInfo(rut, nombre, LocalDate.now(), idBoleta);

            try {
                GestorArriendos.registrarArriendo(arriendo);
            } catch (ArriendoPersistenciaException e) {
                System.out.println(e.getMessage());
            }

        } catch (VehiculoNoDisponibleException | DiasArriendoInvalidosException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al generar boleta: " + e.getMessage());
        }
    }

    private int obtenerDiasDeArriendo() throws DiasArriendoInvalidosException {
        try {
            int dias = Integer.parseInt(scanner.nextLine());
            if (dias <= 0) {
                throw new DiasArriendoInvalidosException("Los días deben ser mayores a cero.");
            }
            return dias;
        } catch (NumberFormatException e) {
            throw new DiasArriendoInvalidosException("Debe ingresar un número válido para los días.");
        }
    }

    private void finalizarArriendoVehiculo() {
        System.out.println("\nFINALIZAR ARRIENDO DE VEHÍCULO");

        System.out.print("Ingrese la patente del vehículo a devolver: ");
        String patente = scanner.nextLine().trim().toUpperCase();

        Vehiculo v = gestor.buscarVehiculoPorPatente(patente);
        if (v == null) {
            System.out.println("Vehículo no encontrado en el sistema.");
            return;
        }

        if (v.isDisponible()) {
            System.out.println("El vehículo ya está disponible. No requiere devolución.");
            return;
        }

        v.setDisponible(true);
        gestor.finalizarArriendo(patente);
        gestor.guardarTodosLosVehiculos();
        System.out.println("Vehículo marcado como disponible. El arriendo ha sido finalizado exitosamente.");
    }

    private void verCantidadEnArriendo() {
        //todo: Lógica futura para aplicar filtro por duración de arriendo
        int cantidad = gestor.contarVehiculosEnArriendo();
        System.out.println("Cantidad de vehículos actualmente en arriendo: " + cantidad);
    }

    private void cargarVehiculosConcurrentemente() {
        System.out.println("\nIniciando carga concurrente de vehículos...");

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

        System.out.println("Vehículos cargados: " + gestor.listarVehiculos().size());
    }

    private void mostrarHistorialArriendos() {
    System.out.println("\n------ HISTORIAL DE ARRIENDOS ------");

    try {
        List<ArriendoInfo> historial = GestorArriendos.cargarHistorial();
        if (historial.isEmpty()) {
            System.out.println("No hay registros de arriendos.");
            return;
        }

        for (ArriendoInfo info : historial) {
            System.out.printf("- Cliente: %s (%s), Fecha: %s, Boleta ID: %s%n",
                info.getNombreCliente(), info.getRutCliente(), info.getFecha(), info.getIdBoleta());
        }

    } catch (HistorialLecturaException e) {
        System.out.println("No se pudo cargar el historial:");
        System.out.println(e.getMessage());
    }
}
}