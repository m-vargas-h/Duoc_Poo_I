
package com.duoc.exp2_s6.persistencia;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.duoc.exp2_s6.modelo.ventas.Venta;

public class PersistenciaVentas {
    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp2_S6", "Data");
    private static final Path ARCHIVO_VENTAS = RUTA_BASE.resolve("ventas.csv");

    public PersistenciaVentas() {
        try {
            Files.createDirectories(RUTA_BASE);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear carpeta Data", e);
        }
    }

    /**
     * Sobrescribe el CSV con todas las ventas:
     * Formato de cabecera:
     * usuarioId,productoId,cantidad,fechaHora,precioUnitario,total
     */
    public void guardarTodos(Collection<Venta> ventas) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(ARCHIVO_VENTAS)) {
            w.write("usuarioId,productoId,cantidad,fechaHora,precioUnitario,total");
            w.newLine();
            for (Venta v : ventas) {
                w.write(v.toCsvLine());
                w.newLine();
            }
        }
    }

    /**
     * Carga todas las ventas desde ventas.csv,
     * deserializando cada línea con Venta.fromCsvTokens(...)
     */
    public List<Venta> cargarTodos() throws IOException {
        List<Venta> lista = new ArrayList<>();
        if (!Files.exists(ARCHIVO_VENTAS)) return lista;

        try (BufferedReader r = Files.newBufferedReader(ARCHIVO_VENTAS)) {
            r.readLine(); // salta cabecera
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] tokens = linea.split(",", -1);
                lista.add(Venta.fromCsvTokens(tokens));
            }
        }
        return lista;
    }
}