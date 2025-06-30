package com.duoc.exp2_s6.persistencia;

import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.productos.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

public class PersistenciaProductos {
    // ruta base para los archivos de datos
    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp2_S6", "Data");

    // ruta del archivo de productos
    private static final Path ARCHIVO_PRODUCTOS = RUTA_BASE.resolve("productos.csv");

    private final Map<String,Function<String[],Producto>> factories = Map.of(
        "Comic",         Comic::fromCsvTokens,
        "NovelaGrafica", NovelaGrafica::fromCsvTokens,
        "Coleccionable", Coleccionable::fromCsvTokens,
        "JuegoMesa",     JuegoMesa::fromCsvTokens,
        "TCG",           TCG::fromCsvTokens
    );

    public PersistenciaProductos() {
        try {
            Files.createDirectories(RUTA_BASE);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear carpeta Data", e);
        }
    }

    // Guarda una colección de productos en el archivo CSV.
    public void guardarTodos(Collection<Producto> productos) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(ARCHIVO_PRODUCTOS)) {
            w.write("id,tipo,titulo,precio,stock,estado,camposExtra...");
            w.newLine();
            for (Producto p : productos) {
                w.write(p.toCsvLine());
                w.newLine();
            }
        }
    }

    // Carga todos los productos desde el archivo CSV.
    public List<Producto> cargarTodos() throws IOException {
        List<Producto> lista = new ArrayList<>();
        if (!Files.exists(ARCHIVO_PRODUCTOS)) return lista;

        try (BufferedReader r = Files.newBufferedReader(ARCHIVO_PRODUCTOS)) {
            r.readLine(); // cabecera
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] tokens = linea.split(",", -1);
                String tipo     = tokens[1];
                Function<String[],Producto> factory = factories.get(tipo);
                if (factory == null) {
                    throw new IllegalStateException("Tipo CSV desconocido: " + tipo);
                }
                lista.add(factory.apply(tokens));
            }
        }
        return lista;
    }
}