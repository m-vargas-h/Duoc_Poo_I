package com.duoc.eft_s9.service;

import com.duoc.eft_s9.model.BoletaInfo;
import com.duoc.eft_s9.utils.RutaArchivo;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class GestorBoletas {
    private static final List<BoletaInfo> historial = new ArrayList<>();
    private static int contadorBoletas = 1;

    public static String generarIdBoleta() {
        return String.format("%05d", contadorBoletas++);
    }

    public static void registrarBoleta(BoletaInfo boleta) {
        historial.add(boleta);
        guardarEnArchivo(boleta);
    }

    public static void mostrarHistorial() {
        System.out.println("\nHISTORIAL DE BOLETAS");
        for (BoletaInfo b : historial) {
            System.out.println(b.toString());
        }
        System.out.println();
    }

    public static void cargarHistorial() {
        Path archivo = RutaArchivo.rutaBoletas();
        if (!Files.exists(archivo)) return;

        try (BufferedReader reader = Files.newBufferedReader(archivo)) {
            String linea;
            boolean primera = true;
            while ((linea = reader.readLine()) != null) {
                if (primera) {
                    primera = false; // saltar encabezado
                    continue;
                }
                String[] partes = linea.split(";");
                if (partes.length < 9) {
                    System.out.println("[ADVERTENCIA] línea incompleta en historial => " + linea);
                    continue;
                }

                String id = partes[0];
                String patente = partes[1];
                String tipo = partes[2];
                int dias = Integer.parseInt(partes[3]);
                double subtotal = Double.parseDouble(partes[4]);
                double descuento = Double.parseDouble(partes[5]);
                double iva = Double.parseDouble(partes[6]);
                double total = Double.parseDouble(partes[7]);

                LocalDate fechaEmision = null;
                if (!partes[8].isBlank() && !partes[8].equalsIgnoreCase("null")) {
                    fechaEmision = LocalDate.parse(partes[8]);
                }

                BoletaInfo b = new BoletaInfo(id, patente, tipo, dias, subtotal, descuento, iva, total, fechaEmision);
                historial.add(b);

                int idNum = Integer.parseInt(id);
                if (idNum >= contadorBoletas) {
                    contadorBoletas = idNum + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar historial de boletas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al procesar historial: " + e.getMessage());
        }
    }

    private static void guardarEnArchivo(BoletaInfo b) {
        Path archivo = RutaArchivo.rutaBoletas();
        boolean archivoExiste = Files.exists(archivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo.toFile(), true))) {
            if (!archivoExiste) {
                writer.write("ID;Patente;Tipo;Días;Subtotal;Descuento;IVA;Total;FechaEmisión");
                writer.newLine();
            }

            String fecha = (b.getFechaEmision() != null) ? b.getFechaEmision().toString() : "";

            String linea = String.format(Locale.US, "%s;%s;%s;%d;%.2f;%.2f;%.2f;%.2f;%s",
                b.getId(), b.getPatente(), b.getTipo(), b.getDias(),
                b.getSubtotal(), b.getDescuento(), b.getIva(), b.getTotal(),
                fecha
            );

            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar boleta: " + e.getMessage());
        }
    }

    public static void verBoletasFiltradas() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nFILTRAR BOLETAS");
        System.out.println("1. Ver boletas del día");
        System.out.println("2. Ver boletas del mes");
        System.out.println("3. Ver boletas por patente");
        System.out.print("Seleccione una opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        LocalDate hoy = LocalDate.now();

        switch (opcion) {
            case 1 -> {
                System.out.println("\nBoletas emitidas hoy:");
                historial.stream()
                    .filter(b -> b.getFechaEmision().isEqual(hoy))
                    .forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("\nBoletas del mes actual:");
                historial.stream()
                    .filter(b -> b.getFechaEmision().getMonth().equals(hoy.getMonth())
                            && b.getFechaEmision().getYear() == hoy.getYear())
                    .forEach(System.out::println);
            }
            case 3 -> {
                System.out.print("Ingrese patente a buscar: ");
                String buscada = scanner.nextLine().trim().toUpperCase();

                List<BoletaInfo> resultados = historial.stream()
                    .filter(b -> b.getPatente().equalsIgnoreCase(buscada))
                    .toList();

                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron boletas con la patente: " + buscada);
                } else {
                    System.out.println("\nBoletas para la patente " + buscada + ":");
                    resultados.forEach(System.out::println);
                }
            }
            default -> System.out.println("Opción no válida.");
        }
    }

    public static String obtenerUltimaBoletaID() {
        Path archivo = RutaArchivo.rutaBoletas();

        String ultimaLinea = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                ultimaLinea = linea;  // Mantiene siempre la última línea
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo de boletas: " + e.getMessage());
            return "SIN-ID";
        }

        if (ultimaLinea != null) {
            String[] partes = ultimaLinea.split(";");
            if (partes.length > 0) {
                return partes[0].trim();  // Asume que el ID va en la primera columna
            }
        }

        return "SIN-ID";
    }
}