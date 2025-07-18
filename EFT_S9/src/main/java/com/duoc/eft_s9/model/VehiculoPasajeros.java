package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public class VehiculoPasajeros extends Vehiculo {
    private int capacidadPasajeros;

    // Constructor principal con control de disponibilidad
    public VehiculoPasajeros(String patente, String marca, int anio, int precioBase,
                             TipoVehiculo tipoVehiculo, int capacidadPasajeros, boolean disponible) {
        super(patente, marca, anio, precioBase, tipoVehiculo, disponible);
        this.capacidadPasajeros = capacidadPasajeros;
    }

    // Constructor simplificado con disponibilidad por defecto
    public VehiculoPasajeros(String patente, String marca, int anio, int precioBase,
                             TipoVehiculo tipoVehiculo, int capacidadPasajeros) {
        super(patente, marca, anio, precioBase, tipoVehiculo);
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public int getCapacidadPasajeros() { return capacidadPasajeros; }

    @Override
    public int calcularPrecioFinal() {
        return precioBase + (capacidadPasajeros * 200);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Capacidad: %d pasajeros", capacidadPasajeros);
    }
}