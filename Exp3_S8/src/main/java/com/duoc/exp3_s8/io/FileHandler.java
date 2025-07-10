package com.duoc.exp3_s8.io;

import com.duoc.exp3_s8.model.PrimeList;

import java.io.*;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class FileHandler {

    private static final Path BASE_PATH =
        Paths.get(System.getProperty("user.dir"), "Exp3_S8", "Data");

    /**
     * Carga números primos desde un CSV, linea a linea.
     */
    public static void loadPrimesFromCsv(String csvPath, PrimeList list)
            throws IOException {
        Path path = Paths.get(csvPath);
        if (!Files.exists(path)) {
            System.out.println("[WARN] CSV not found at " + path.toAbsolutePath()
                            + " — se omite carga desde archivo.");
            return;
        }
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                int n = Integer.parseInt(line.trim());
                list.add(n);
            }
        }
    }

    /**
     * Escribe mensajes encriptados junto con el conteo de primos actual.
     */
    public static void writeEncryptedMessages(String outPath,
                                              List<String> messages,
                                              PrimeList list)
            throws IOException {
        try (FileWriter fw = new FileWriter(outPath)) {
            for (String msg : messages) {
                // Por ahora, no ciframos; solo contextualizamos
                fw.write(msg + " -> " + list.getPrimesCount());
                fw.write(System.lineSeparator());
            }
        }
    }

    /**
     * Guarda la lista de primos de un rango en un archivo de texto.
     */
    public static String savePrimeListRange(PrimeList list,
                                            int start,
                                            int end)
            throws IOException {
        if (!Files.exists(BASE_PATH)) {
            Files.createDirectories(BASE_PATH);
        }

        String fileName = MessageFormat.format("primes_{0}-{1}.txt",
                                               start, end);
        Path filePath = BASE_PATH.resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                 filePath,
                 StandardOpenOption.CREATE,
                 StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write("Primes found: " + list.getPrimesCount());
            writer.newLine();
            writer.write("Detail of primes:");
            writer.newLine();
            for (Integer prime : list) {
                writer.write(prime.toString());
                writer.newLine();
            }
        }

        return filePath.toAbsolutePath().toString();
    }
}