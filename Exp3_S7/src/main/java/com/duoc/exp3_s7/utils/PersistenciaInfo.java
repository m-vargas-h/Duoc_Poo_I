package com.duoc.exp3_s7.utils;

import com.duoc.exp3_s7.model.ListaPrimos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;

public class PersistenciaInfo {
    // Ruta base para guardar los archivos
    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp3_S7", "Data");

    // Método para guardar la lista de números primos en un archivo
    public static String guardarListaRango(ListaPrimos lista, int inicio, int fin) throws IOException {
        if (!Files.exists(RUTA_BASE)) {
            Files.createDirectories(RUTA_BASE);
        }

        String nombreArchivo = MessageFormat.format("primos_{0}-{1}.txt", inicio, fin);
        Path rutaArchivo = RUTA_BASE.resolve(nombreArchivo);

        // Verificar si el archivo ya existe y eliminarlo si es necesario
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaArchivo, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            escritor.write("Números primos encontrados: " + lista.contarPrimos());
            escritor.newLine();
            escritor.write("Detalle de números primos:");
            escritor.newLine();
            // Escribir cada número primo en una nueva línea
            for (int primo : lista) {
                escritor.write(String.valueOf(primo));
                escritor.newLine();
            }
        }

        // Retornar la ruta absoluta del archivo creado
        return rutaArchivo.toAbsolutePath().toString();
    }
}