package com.duoc.eft_s9.service;

import com.duoc.eft_s9.model.Vehiculo;

import java.util.*;
import java.util.stream.Collectors;

public class GestorVehiculos {
    private final Map<String, Vehiculo> vehiculos = new HashMap<>();
    private final Set<String> patentesEnArriendo = new HashSet<>(); // asumiendo manejo temporal

    /**
     * Agrega un vehículo al gestor.
     * @param v Vehículo a agregar.
     * @return true si se agregó correctamente, false si ya existe.
     */
    public boolean agregarVehiculo(Vehiculo v) {
        if (!vehiculos.containsKey(v.getPatente())) {
            vehiculos.put(v.getPatente(), v);
            return true;
        }
        return false;
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
}