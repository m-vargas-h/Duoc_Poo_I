package com.duoc.exp2_s6.persistencia;

import com.duoc.exp2_s6.modelo.Reserva;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PersistenciaReservas {
    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp2_S6", "Data");
    private static final Path ARCHIVO_RESERVAS = RUTA_BASE.resolve("reservas.csv");

    public PersistenciaReservas() {
        try { Files.createDirectories(RUTA_BASE); }
        catch(IOException e){
            throw new RuntimeException("No se pudo crear carpeta Data", e);
        }
    }

    public void guardarTodos(Collection<Reserva> regs) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(ARCHIVO_RESERVAS)) {
            w.write("id,usuarioId,productoId,cantidad,fechaHora,estado");
            w.newLine();
            for (Reserva r : regs) {
                w.write(r.toCsvLine());
                w.newLine();
            }
        }
    }

    public List<Reserva> cargarTodos() throws IOException {
        List<Reserva> lista = new ArrayList<>();
        if (!Files.exists(ARCHIVO_RESERVAS)) return lista;
        try (BufferedReader r = Files.newBufferedReader(ARCHIVO_RESERVAS)) {
            r.readLine(); // salta cabecera
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] t = linea.split(",", -1);
                lista.add(Reserva.fromCsvTokens(t));
            }
        }
        return lista;
    }
}