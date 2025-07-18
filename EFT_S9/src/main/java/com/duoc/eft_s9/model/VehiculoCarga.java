package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public class VehiculoCarga extends Vehiculo {
    private int capacidadToneladas;

    // Constructor principal con control de disponibilidad
    public VehiculoCarga(String patente, String marca, int anio, int precioBase,
                         TipoVehiculo tipoVehiculo, int capacidadToneladas, boolean disponible) {
        super(patente, marca, anio, precioBase, tipoVehiculo, disponible);
        this.capacidadToneladas = capacidadToneladas;
    }

    // Constructor simplificado con disponibilidad por defecto
    public VehiculoCarga(String patente, String marca, int anio, int precioBase,
                         TipoVehiculo tipoVehiculo, int capacidadToneladas) {
        super(patente, marca, anio, precioBase, tipoVehiculo);
        this.capacidadToneladas = capacidadToneladas;
    }

    public int getCapacidadToneladas() { return capacidadToneladas; }

    @Override
    public int calcularPrecioFinal() {
        return precioBase + (capacidadToneladas * 500);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Capacidad: %d toneladas", capacidadToneladas);
    }
}