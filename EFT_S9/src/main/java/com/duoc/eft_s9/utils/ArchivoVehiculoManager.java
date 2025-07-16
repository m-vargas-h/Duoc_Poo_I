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
            String linea = String.format("%s;%s;%d;%d;%s;%d",
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getAnio(),
                vehiculo.getPrecioBase(),
                vehiculo.getTipoVehiculo().name(),
                vehiculo.getCapacidadToneladas());
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar vehículo de carga: " + e.getMessage());
        }
    }

    public static void guardarVehiculoPasajeros(VehiculoPasajeros vehiculo) {
        Path archivo = RutaArchivo.rutaPasajeros();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo.toFile(), true))) {
            String linea = String.format("%s;%s;%d;%d;%s;%d",
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getAnio(),
                vehiculo.getPrecioBase(),
                vehiculo.getTipoVehiculo().name(),
                vehiculo.getCapacidadMaxima());
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
                if (partes.length == 6) {
                    String patente = partes[0];
                    String marca = partes[1];
                    int anio = Integer.parseInt(partes[2]);
                    int precioBase = Integer.parseInt(partes[3]);
                    TipoVehiculo tipo = TipoVehiculo.valueOf(partes[4]);
                    int capacidad = Integer.parseInt(partes[5]);

                    VehiculoCarga carga = new VehiculoCarga(patente, marca, anio, precioBase, tipo, capacidad);
                    lista.add(carga);
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
                if (partes.length == 6) {
                    String patente = partes[0];
                    String marca = partes[1];
                    int anio = Integer.parseInt(partes[2]);
                    int precioBase = Integer.parseInt(partes[3]);
                    TipoVehiculo tipo = TipoVehiculo.valueOf(partes[4]);
                    int capacidad = Integer.parseInt(partes[5]);

                    VehiculoPasajeros pasajero = new VehiculoPasajeros(patente, marca, anio, precioBase, tipo, capacidad);
                    lista.add(pasajero);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al cargar vehículos de pasajeros: " + e.getMessage());
        }

        return lista;
    }
}