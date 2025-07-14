package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public class VehiculoCarga extends Vehiculo {
    private double capacidadToneladas;

    public VehiculoCarga(String patente, String marca, int anio, double precioBase,
                         TipoVehiculo tipoVehiculo, double capacidadToneladas) {
        super(patente, marca, anio, precioBase, tipoVehiculo);
        if (tipoVehiculo.getCategoria() != TipoVehiculo.Categoria.CARGA) {
            throw new IllegalArgumentException("TipoVehiculo no válido para VehiculoCarga.");
        }
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularPrecioFinal() {
        double extra = capacidadToneladas * 25000;
        return precioBase + extra;
    }

    // Getters y Setters
    public double getCapacidadToneladas() { return capacidadToneladas; }
    public void setCapacidadToneladas(double capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }
}