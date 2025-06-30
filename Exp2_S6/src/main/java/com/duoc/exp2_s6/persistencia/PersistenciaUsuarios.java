
package com.duoc.exp2_s6.persistencia;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.duoc.exp2_s6.modelo.base.Usuario;

public class PersistenciaUsuarios {
    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp2_S6", "Data");
    private static final Path ARCHIVO_USUARIOS = RUTA_BASE.resolve("usuarios.csv");

    public PersistenciaUsuarios() {
        try {
            Files.createDirectories(RUTA_BASE);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear carpeta Data", e);
        }
    }

    /**
     * Guarda (sobrescribe) todos los usuarios en usuarios.csv.
     * Formato de cabecera: id,nombre,email
     */
    public void guardarTodos(Collection<Usuario> usuarios) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(ARCHIVO_USUARIOS)) {
            // cabecera opcional
            w.write("id,nombre,email");
            w.newLine();
            for (Usuario u : usuarios) {
                w.write(u.toCsvLine());
                w.newLine();
            }
        }
    }

    /**
     * Carga todos los usuarios desde usuarios.csv,
     * saltando la cabecera y deserializando cada línea.
     */
    public List<Usuario> cargarTodos() throws IOException {
        List<Usuario> lista = new ArrayList<>();
        if (!Files.exists(ARCHIVO_USUARIOS)) return lista;

        try (BufferedReader r = Files.newBufferedReader(ARCHIVO_USUARIOS)) {
            r.readLine(); // saltar cabecera
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] tokens = linea.split(",", -1);
                lista.add(Usuario.fromCsvTokens(tokens));
            }
        }
        return lista;
    }
}