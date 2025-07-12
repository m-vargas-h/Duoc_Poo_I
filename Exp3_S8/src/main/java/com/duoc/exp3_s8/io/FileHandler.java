package com.duoc.exp3_s8.io;

import com.duoc.exp3_s8.model.PrimeList;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHandler {

    private static final Path RUTA_BASE = Paths.get(System.getProperty("user.dir"), "Exp3_S8", "Data");


    //Carga la lista de primos desde un archivo CSV ubicado en /Data.
    //Soporta formato vertical, horizontal (comas) e ignora encabezados informativos.
    public static void loadPrimesFromCsv(String csvFileName, PrimeList list) throws IOException {
        Path path = RUTA_BASE.resolve(csvFileName);

        if (!Files.exists(path)) {
            System.out.println("[ADVERTENCIA] Archivo CSV no encontrado en "
                    + path.toAbsolutePath() + " — se omite carga desde archivo.");
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("Se han encontrado")) continue;
                if (line.matches(".*[a-zA-Z].*")) continue;

                try {
                    String[] tokens = line.split(",");
                    for (String token : tokens) {
                        int n = Integer.parseInt(token.trim());
                        list.add(n);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("[OMITIDO] Entrada inválida: " + line);
                }
            }
        }
    }


    //Guarda la lista de primos en formato CSV dentro de /Data,
    //con encabezado informativo compatible con la carga.
    public static String savePrimeListRange(PrimeList list, int start, int end) throws IOException {
        Files.createDirectories(RUTA_BASE);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyy_HHmm");
        String timestamp = LocalDateTime.now().format(formatter);
        String fileName = String.format("primos_%d_a_%d_%s.csv", start, end, timestamp);
        Path output = RUTA_BASE.resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            int count = list.getPrimesCount();
            writer.write("Se han encontrado " + count + " números primos, el detalle es:");
            writer.newLine();

            for (int prime : list) {
                writer.write(Integer.toString(prime));
                writer.newLine();
            }
        }

        return output.toAbsolutePath().toString();
    }

    // Guarda mensajes cifrados en un archivo dentro de /Data.
    // Aplica cifrado básico para propósitos de demostración.
    public static void writeEncryptedMessages(String fileName, List<String> messages,
                                             PrimeList primeList) throws IOException {
        Files.createDirectories(RUTA_BASE);

        Path path = RUTA_BASE.resolve(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String message : messages) {
                int offset = primeList.getPrimesCount();
                String encrypted = new StringBuilder(message)
                        .reverse()
                        .append("#")
                        .append(offset)
                        .toString();
                writer.write(encrypted);
                writer.newLine();
            }
        }
    }
}