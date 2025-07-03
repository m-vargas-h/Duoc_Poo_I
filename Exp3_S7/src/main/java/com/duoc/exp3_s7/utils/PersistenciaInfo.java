package com.duoc.exp3_s7.utils;

import com.duoc.exp3_s7.model.ListaPrimos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;

public class PersistenciaInfo {

    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp3_S7", "Data");

    /**
     * Guarda la lista de primos con nombre de archivo automático y formato detallado.
     */
    public static void guardarListaRango(ListaPrimos lista, int inicio, int fin) throws IOException {
        // Asegurar que la carpeta exista
        if (!Files.exists(RUTA_BASE)) {
            Files.createDirectories(RUTA_BASE);
        }

        // Crear nombre de archivo: primos_100-500.txt
        String nombreArchivo = MessageFormat.format("primos_{0}_{1}.txt", inicio, fin);
        Path rutaArchivo = RUTA_BASE.resolve(nombreArchivo);

        try (BufferedWriter escritor = Files.newBufferedWriter(rutaArchivo, StandardOpenOption.CREATE, 
                                                               StandardOpenOption.TRUNCATE_EXISTING)) {
            escritor.write("Números primos encontrados: " + lista.contarPrimos());
            escritor.newLine();
            escritor.write("Detalle de números primos:");
            escritor.newLine();

            for (int primo : lista) {
                escritor.write(String.valueOf(primo));
                escritor.newLine();
            }
        }

        System.out.println("Archivo guardado en: " + rutaArchivo.toAbsolutePath());
    }
}