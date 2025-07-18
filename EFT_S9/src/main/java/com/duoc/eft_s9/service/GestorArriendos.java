package com.duoc.eft_s9.service;

import com.duoc.eft_s9.exceptions.ArriendoPersistenciaException;
import com.duoc.eft_s9.exceptions.HistorialLecturaException;
import com.duoc.eft_s9.model.ArriendoInfo;
import com.duoc.eft_s9.utils.RutaArchivo;

import java.io.*;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class GestorArriendos {
    private static final Path archivo = RutaArchivo.rutaHistorialArriendos(); 

    public static void registrarArriendo(ArriendoInfo info) throws ArriendoPersistenciaException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo.toFile(), true))) {
            String linea = String.format("%s;%s;%s;%s",
                info.getRutCliente(),
                info.getNombreCliente(),
                info.getFecha(),
                info.getIdBoleta()
            );
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            throw new ArriendoPersistenciaException("Error al guardar historial de arriendo: " + e.getMessage());
        }
    }

    public static List<ArriendoInfo> cargarHistorial() throws HistorialLecturaException {
        List<ArriendoInfo> historial = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length != 4) {
                    System.out.println("[ADVERTENCIA] Línea ignorada por formato incorrecto: " + linea);
                    continue;
                }

                String rut = partes[0].trim();
                String nombre = partes[1].trim();
                LocalDate fecha = LocalDate.parse(partes[2].trim());
                String idBoleta = partes[3].trim();

                historial.add(new ArriendoInfo(rut, nombre, fecha, idBoleta));
            }
        } catch (IOException | DateTimeException e) {
            throw new HistorialLecturaException("Error al leer historial de arriendos: " + e.getMessage());
        }

        return historial;
    }
}