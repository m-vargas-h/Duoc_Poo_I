package com.duoc.eft_s9.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.duoc.eft_s9.enums.TipoVehiculo;
import com.duoc.eft_s9.exceptions.*;
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

    public void mostrarMenu() {

        //? [Debug]: Mostrar vehículos cargados al iniciar
        ////System.out.println("Vehículos cargados:");
        ////for (Vehiculo v : gestor.listarVehiculos()) {
        ////    System.out.println("- " + v.getPatente());
        ////}
        
        int opcion;

        do {
            System.out.println("\n-------------- MENU PRINCIPAL --------------");
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

    private Object[] solicitarDatosBaseVehiculo() {
        System.out.print("Ingrese patente (AB-1234 o ABCD-12): ");
        String input = scanner.nextLine().trim().toUpperCase();
        String patente = ValidadorFormato.validarPatente(input);
        if (patente == null) return null;

        if (gestor.buscarVehiculoPorPatente(patente) != null) {
            System.out.println("Ya existe un vehículo con esa patente.");
            return null;
        }

        System.out.print("Ingrese marca: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Ingrese año: ");
        int anio = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Ingrese precio base: ");
        int precioBase = Integer.parseInt(scanner.nextLine().trim());

        TipoVehiculo tipo = seleccionarTipoVehiculo();

        return new Object[] { patente, marca, anio, precioBase, tipo };
    }

    private void agregarVehiculoCarga() {
        try {
            Object[] datos = solicitarDatosBaseVehiculo();
            if (datos == null) return;

            System.out.print("Ingrese capacidad en toneladas: ");
            int capacidadToneladas = Integer.parseInt(scanner.nextLine().trim());

            VehiculoCarga carga = new VehiculoCarga(
                (String) datos[0],
                (String) datos[1],
                (int)    datos[2],
                (int)    datos[3],
                (TipoVehiculo) datos[4],
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
            Object[] datos = solicitarDatosBaseVehiculo();
            if (datos == null) return;

            System.out.print("Ingrese capacidad máxima de pasajeros: ");
            int capacidadMaxima = Integer.parseInt(scanner.nextLine().trim());

            VehiculoPasajeros pasajero = new VehiculoPasajeros(
                (String) datos[0],
                (String) datos[1],
                (int)    datos[2],
                (int)    datos[3],
                (TipoVehiculo) datos[4],
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
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < 1 || opcion > tipos.length) {
                System.out.println("Opción fuera de rango.");
                return;
            }

            TipoVehiculo seleccionado = tipos[opcion - 1];
            List<Vehiculo> filtrados = gestor.filtrarPorTipo(seleccionado);

            if (filtrados.isEmpty()) {
                System.out.println("No hay vehículos registrados del tipo " + seleccionado.name());
                return;
            }

            System.out.println("------ VEHÍCULOS TIPO " + seleccionado.name() + " ------");
            filtrados.forEach(System.out::println);
            System.out.println("----------------------------------");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
        }
    }

    // Métodos auxiliar para capturar datos del cliente
    private String[] capturarDatosCliente() {
        String rut;
        do {
            System.out.print("Ingrese RUT del cliente (ej: 12345678-K): ");
            rut = scanner.nextLine().trim();
            if (!ValidadorFormato.validarFormatoRut(rut)) {
                System.out.println("Formato de RUT inválido. Intente nuevamente.");
                rut = null;
            }
        } while (rut == null);

        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine().trim();

        return new String[] { rut, nombre };
    }

    // Método auxiliar para validar vehículo y lanzar excepción si no está disponible
    private Vehiculo validarVehiculoParaArriendo(String patente) throws VehiculoNoDisponibleException {
        Vehiculo v = gestor.buscarVehiculoPorPatente(patente);
        if (v == null) throw new IllegalArgumentException("Vehículo no encontrado.");
        if (!v.isDisponible()) throw new VehiculoNoDisponibleException("El vehículo está en arriendo.");
        return v;
    }

    // Método auxiliar para crear boleta y guardar, lanzando excepción si falla
    private void crearYGenerarBoleta(Vehiculo v, int dias) {
        GeneradorBoleta generador = new BoletaSimple();
        generador.generarBoleta(v, dias);
        gestor.guardarTodosLosVehiculos();
    }

    // Método auxiliar para registrar arriendo en el gestor de arriendos
    private void registrarArriendo(String rut, String nombre, String idBoleta) {
        ArriendoInfo info = new ArriendoInfo(rut, nombre, LocalDate.now(), idBoleta);
        try {
            GestorArriendos.registrarArriendo(info);
        } catch (ArriendoPersistenciaException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }

    // Método principal para generar boleta de arriendo
    // Captura datos del cliente, valida vehículo, obtiene días de arriendo y genera boleta
    // Registra arriendo en el gestor de arriendos
    private void generarBoleta() {
        System.out.println("\nGENERAR BOLETA DE ARRIENDO");

        try {
            String[] cliente = capturarDatosCliente();

            System.out.print("Ingrese la patente del vehículo: ");
            String patente = scanner.nextLine().trim().toUpperCase();

            Vehiculo vehiculo = validarVehiculoParaArriendo(patente);
            System.out.print("Ingrese días de arriendo: ");
            int dias = obtenerDiasDeArriendo();

            crearYGenerarBoleta(vehiculo, dias);

            String idBoleta = GestorBoletas.obtenerUltimaBoletaID();
            registrarArriendo(cliente[0], cliente[1], idBoleta);

        } catch (VehiculoNoDisponibleException | DiasArriendoInvalidosException | IllegalArgumentException e) {
            System.out.println("[ADVERTENCIA] " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error inesperado al generar boleta: " + e.getMessage());
        }
    }

    // Método auxiliar para capturar y validar los días de arriendo
    // Lanza excepción si el número es inválido o menor a 1
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

    // Método para finalizar arriendo de un vehículo
    // Solicita patente, verifica si existe y si está en arriendo
    private void finalizarArriendoVehiculo() {
        System.out.println("\nFINALIZAR ARRIENDO DE VEHICULO");

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

    // Método para ver la cantidad de vehículos actualmente en arriendo
    // Muestra la cantidad total de vehículos en arriendo
    private void verCantidadEnArriendo() {
        //todo: Lógica futura para aplicar filtro por duración de arriendo
        int cantidad = gestor.contarVehiculosEnArriendo();
        System.out.println("Cantidad de vehículos actualmente en arriendo: " + cantidad);
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