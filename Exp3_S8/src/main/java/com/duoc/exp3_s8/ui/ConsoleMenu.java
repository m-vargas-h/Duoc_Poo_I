package com.duoc.exp3_s8.ui;

import com.duoc.exp3_s8.model.PrimeList;
import com.duoc.exp3_s8.io.FileHandler;
import com.duoc.exp3_s8.utils.PrimeUtils;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();

    private Future<PrimeList> generationTask;
    private PrimeList primeList = new PrimeList();
    private int lastRangeStart, lastRangeEnd;

    public void show() {
        while (true) {
            System.out.println("\n-------- Menú de SafeVoteSystem --------");
            System.out.println("1. Cargar números primos desde CSV");
            System.out.println("2. Generar primos en un rango (en segundo plano)");
            System.out.println("3. Mostrar total de primos");
            System.out.println("4. Guardar lista de primos en archivo");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int option = safeNextInt();
            switch (option) {
                case 1: 
                    handleLoadCsv();       
                    break;

                case 2: 
                    handleGenerateRange(); 
                    break;

                case 3: 
                    handleShowCount();     
                    break;

                case 4:  
                    handleSavePrimeList(); 
                    break;

                case 5:
                    System.out.println("Saliendo del sistema.");
                    bgExecutor.shutdownNow();
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private int safeNextInt() {
        try {
            int val = scanner.nextInt();
            scanner.nextLine(); // consumir salto
            return val;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private void handleLoadCsv() {
        File carpeta = new File(System.getProperty("user.dir") + "/Exp3_S8/Data");
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (archivos == null || archivos.length == 0) {
            System.out.println("📂 No se encontraron archivos CSV en la carpeta Data.");
            return;
        }

        System.out.println("\n🗂 Archivos disponibles:");
        for (int i = 0; i < archivos.length; i++) {
            System.out.println((i + 1) + ". " + archivos[i].getName());
        }

        System.out.print("Selecciona un archivo por número: ");
        int opcion = safeNextInt();
        if (opcion < 1 || opcion > archivos.length) {
            System.out.println("Opción inválida.");
            return;
        }

        String archivoSeleccionado = archivos[opcion - 1].getName();
        try {
            FileHandler.loadPrimesFromCsv(archivoSeleccionado, primeList);
            System.out.println("✅ Lista cargada desde: " + archivoSeleccionado);
            System.out.println("Total actual de primos: " + primeList.getPrimesCount());
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    private void handleGenerateRange() {
        System.out.print("Inicio del rango: ");
        lastRangeStart = safeNextInt();
        System.out.print("Fin del rango: ");
        lastRangeEnd = safeNextInt();

        // Si hay una tarea en curso, pedir confirmación antes de reemplazarla
        if (generationTask != null && !generationTask.isDone()) {
            System.out.print("[ADVERTENCIA] Aún hay una generación en curso. ¿Deseas reemplazarla? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (!respuesta.equals("S")) {
                System.out.println("Generación actual preservada.");
                return;
            }

            System.out.println("Iniciando nueva generación con seguimiento de progreso...");
            long inicio = System.nanoTime();

            generationTask = bgExecutor.submit(() -> {
                PrimeList lista = PrimeUtils.generatePrimeList(
                    lastRangeStart,
                    lastRangeEnd,
                    2, 2,
                    true // progreso visible
                );
                long fin = System.nanoTime();
                System.out.printf("%n Generación completada en %.2f segundos.%n", (fin - inicio) / 1e9);
                return lista;
            });

            return;
        }

        // Nueva generación en segundo plano sin progreso
        long inicio = System.nanoTime();

        generationTask = bgExecutor.submit(() -> {
            PrimeList lista = PrimeUtils.generatePrimeList(
                lastRangeStart,
                lastRangeEnd,
                2, 2,
                false // sin progreso visible
            );
            long fin = System.nanoTime();
            System.out.printf("%n [SISTEMA] Generación completada en %.2f segundos.%n", (fin - inicio) / 1e9);
            return lista;
        });

        System.out.println("[SISTEMA] Generación iniciada en segundo plano.");
    }

    private void handleShowCount() {
        if (generationTask != null && !generationTask.isDone()) {
            System.out.println("Generando... primos actuales: " + primeList.getPrimesCount());
        } else {
            System.out.println("Total de primos: " + primeList.getPrimesCount());
        }
    }

    private void handleSavePrimeList() {
        if (lastRangeEnd <= lastRangeStart) {
            System.out.println("[ADVERTENCIA] No se ha generado un rango válido aún.");
            return;
        }

        // Mostrar progreso si generación sigue activa
        if (generationTask != null && !generationTask.isDone()) {
            System.out.println("[ADVERTENCIA] La generación aún está en curso. Mostrando progreso:");
            generationTask = bgExecutor.submit(() -> {
                PrimeList lista = PrimeUtils.generatePrimeList(
                    lastRangeStart,
                    lastRangeEnd,
                    2, 2,
                    true // activa progreso visible
                );
                System.out.println(); // salto visual al finalizar
                return lista;
            });
            return;
        }

        try {
            if (generationTask != null) {
                primeList = generationTask.get(); // espera resultado
            }

            long inicio = System.nanoTime();
            String file = FileHandler.savePrimeListRange(
                primeList, lastRangeStart, lastRangeEnd
            );
            long fin = System.nanoTime();

            System.out.println("[SISTEMA] Lista de primos guardada en: " + file);
            System.out.printf(" Guardado completado en %.2f segundos.%n", (fin - inicio) / 1e9);
        } catch (Exception e) {
            System.out.println("[ERROR] Error al guardar archivo: " + e.getMessage());
        }
    }
}