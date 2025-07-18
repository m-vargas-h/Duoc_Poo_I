package com.duoc.eft_s9.utils;

import com.duoc.eft_s9.model.*;
import com.duoc.eft_s9.enums.TipoVehiculo;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class ArchivoVehiculoManager {

    public static void guardarVehiculoCarga(VehiculoCarga vehiculo) {
        Path archivo = RutaArchivo.rutaCarga();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo.toFile(), true))) {
            String linea = String.format("%s;%s;%d;%d;%s;%d;%s",
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getAnio(),
                vehiculo.getPrecioBase(),
                vehiculo.getTipoVehiculo().name(),
                vehiculo.getCapacidadToneladas(),
                vehiculo.isDisponible() ? "true" : "false"
            );
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar vehículo de carga: " + e.getMessage());
        }
    }

    public static void guardarVehiculoPasajeros(VehiculoPasajeros vehiculo) {
        Path archivo = RutaArchivo.rutaPasajeros();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo.toFile(), true))) {
            String linea = String.format("%s;%s;%d;%d;%s;%d;%s",
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getAnio(),
                vehiculo.getPrecioBase(),
                vehiculo.getTipoVehiculo().name(),
                vehiculo.getCapacidadPasajeros(),
                vehiculo.isDisponible() ? "true" : "false"
            );
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar vehículo de pasajeros: " + e.getMessage());
        }
    }

    public static List<VehiculoCarga> cargarVehiculosCarga() {
        List<VehiculoCarga> lista = new ArrayList<>();
        Path archivo = RutaArchivo.rutaCarga();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 7) {
                    String patente = partes[0].trim().toUpperCase();
                    String marca = partes[1].trim();
                    int anio = Integer.parseInt(partes[2].trim());
                    int precioBase = Integer.parseInt(partes[3].trim());
                    TipoVehiculo tipo = TipoVehiculo.valueOf(partes[4].trim().toUpperCase());
                    int capacidad = Integer.parseInt(partes[5].trim());
                    boolean disponible = Boolean.parseBoolean(partes[6].trim());

                    VehiculoCarga carga = new VehiculoCarga(
                        patente, marca, anio, precioBase, tipo, capacidad, disponible
                    );
                    lista.add(carga);
                } else {
                    System.out.println("Línea incompleta ignorada: " + linea);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al cargar vehículos de carga: " + e.getMessage());
        }

        return lista;
    }

    public static List<VehiculoPasajeros> cargarVehiculosPasajeros() {
        List<VehiculoPasajeros> lista = new ArrayList<>();
        Path archivo = RutaArchivo.rutaPasajeros();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 7) {
                    String patente = partes[0].trim().toUpperCase();
                    String marca = partes[1].trim();
                    int anio = Integer.parseInt(partes[2].trim());
                    int precioBase = Integer.parseInt(partes[3].trim());
                    TipoVehiculo tipo = TipoVehiculo.valueOf(partes[4].trim().toUpperCase());
                    int capacidad = Integer.parseInt(partes[5].trim());
                    boolean disponible = Boolean.parseBoolean(partes[6].trim());

                    VehiculoPasajeros pasajero = new VehiculoPasajeros(
                        patente, marca, anio, precioBase, tipo, capacidad, disponible
                    );
                    lista.add(pasajero);
                } else {
                    System.out.println("Línea incompleta ignorada: " + linea);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al cargar vehículos de pasajeros: " + e.getMessage());
        }

        return lista;
    }
}