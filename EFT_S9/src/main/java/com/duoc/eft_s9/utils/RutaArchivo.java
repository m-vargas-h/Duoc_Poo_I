package com.duoc.eft_s9.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RutaArchivo {
    // Ruta base para los archivos de datos
    public static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "EFT_S9", "Data");

    // Ruta para el archivo de vehículos de carga
    public static Path rutaCarga() {
        return RUTA_BASE.resolve("vehiculos_carga.csv");
    }

    // Ruta para el archivo de vehículos de pasajeros
    public static Path rutaPasajeros() {
        return RUTA_BASE.resolve("vehiculos_pasajeros.csv");
    }

    // Ruta para el archivo de historial de boletas
    public static Path rutaBoletas() {
        return RUTA_BASE.resolve("historial_boletas.csv");
    }

    //Todo: Aquí pueden ir mas rutas según sea necesario
}