package com.duoc.eft_s9.service;

import com.duoc.eft_s9.model.Vehiculo;
import com.duoc.eft_s9.model.VehiculoCarga;
import com.duoc.eft_s9.model.VehiculoPasajeros;
import com.duoc.eft_s9.utils.ArchivoVehiculoManager;
import com.duoc.eft_s9.utils.RutaArchivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class GestorVehiculos {
    private final Map<String, Vehiculo> vehiculos = Collections.synchronizedMap(new HashMap<>());
    private final Set<String> patentesEnArriendo = new HashSet<>();

    /**
     * Agrega un vehículo al gestor.
     * @param v Vehículo a agregar.
     * @return true si se agregó correctamente, false si ya existe.
     */
    public boolean agregarVehiculo(Vehiculo v) {
        String clave = v.getPatente().trim().toUpperCase();
        if (!vehiculos.containsKey(clave)) {
            vehiculos.put(clave, v);
            return true;
        }
        return false;
    }

    public void cargarVehiculosDesdeArchivos() {
        List<VehiculoCarga> carga = ArchivoVehiculoManager.cargarVehiculosCarga();
        List<VehiculoPasajeros> pasajeros = ArchivoVehiculoManager.cargarVehiculosPasajeros();

        for (Vehiculo v : carga) {
            agregarVehiculo(v);
        }
        for (Vehiculo v : pasajeros) {
            agregarVehiculo(v);
        }
    }

    public void cargarVehiculosPasajeros() {
        List<VehiculoPasajeros> pasajeros = ArchivoVehiculoManager.cargarVehiculosPasajeros();
        for (VehiculoPasajeros v : pasajeros) {
            agregarVehiculo(v);
        }
    }

    public void cargarVehiculosCarga() {
        List<VehiculoCarga> carga = ArchivoVehiculoManager.cargarVehiculosCarga();
        for (VehiculoCarga v : carga) {
            agregarVehiculo(v);
        }
    }

    // Método para verificar si un vehículo existe por patente
    public boolean existeVehiculo(String patente) {
        return vehiculos.containsKey(patente);
    }

    /**
     * Filtra vehículos por tipo.
     * @param tipo Tipo de vehículo a filtrar.
     * @return Lista de vehículos del tipo especificado.
     */
    public List<Vehiculo> listarVehiculos() {
        return new ArrayList<>(vehiculos.values());
    }

    public int contarVehiculosEnArriendo() {
        return patentesEnArriendo.size();
    }

    public List<Vehiculo> obtenerVehiculosEnArriendo() {
        return patentesEnArriendo.stream()
            .map(vehiculos::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) {
        if (patente == null || patente.isBlank()) return null;
        return vehiculos.get(patente.trim().toUpperCase());
    }

    // Opcional: método para registrar arriendo (para mantener patentes en uso)
    public void registrarArriendo(String patente) {
        if (vehiculos.containsKey(patente)) {
            patentesEnArriendo.add(patente);
        }
    }

    // Opcional: liberar vehículo (cuando termina el arriendo)
    public void finalizarArriendo(String patente) {
        patentesEnArriendo.remove(patente);
    }

    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return vehiculos.values().stream()
            .filter(Vehiculo::isDisponible)
            .collect(Collectors.toList());
    }

    // Método para guardar todos los vehículos en sus respectivos archivos, evitando disponibilidad desactualizada
    public void guardarTodosLosVehiculos() {
        Path archivoCarga = RutaArchivo.rutaCarga();
        Path archivoPasajeros = RutaArchivo.rutaPasajeros();

        try (
            BufferedWriter writerCarga = new BufferedWriter(new FileWriter(archivoCarga.toFile()));
            BufferedWriter writerPasajeros = new BufferedWriter(new FileWriter(archivoPasajeros.toFile()))
        ) {
            for (Vehiculo v : vehiculos.values()) {
                String linea = null;
                if (v instanceof VehiculoCarga) {
                    VehiculoCarga vc = (VehiculoCarga) v;
                    linea = String.format("%s;%s;%d;%d;%s;%d;%s",
                        vc.getPatente(),
                        vc.getMarca(),
                        vc.getAnio(),
                        vc.getPrecioBase(),
                        vc.getTipoVehiculo().name(),
                        vc.getCapacidadToneladas(),
                        vc.isDisponible() ? "true" : "false"
                    );
                    writerCarga.write(linea);
                    writerCarga.newLine();
                } else if (v instanceof VehiculoPasajeros) {
                    VehiculoPasajeros vp = (VehiculoPasajeros) v;
                    linea = String.format("%s;%s;%d;%d;%s;%d;%s",
                        vp.getPatente(),
                        vp.getMarca(),
                        vp.getAnio(),
                        vp.getPrecioBase(),
                        vp.getTipoVehiculo().name(),
                        vp.getCapacidadPasajeros(),
                        vp.isDisponible() ? "true" : "false"
                    );
                    writerPasajeros.write(linea);
                    writerPasajeros.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar vehículos: " + e.getMessage());
        }
    }
}