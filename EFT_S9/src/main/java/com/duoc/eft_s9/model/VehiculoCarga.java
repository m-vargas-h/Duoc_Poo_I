package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public class VehiculoCarga extends Vehiculo {
    private int capacidadToneladas;

    public VehiculoCarga(String patente, String marca, int anio, int precioBase,
                         TipoVehiculo tipoVehiculo, int capacidadToneladas) {
        super(patente, marca, anio, precioBase, tipoVehiculo);
        if (tipoVehiculo.getCategoria() != TipoVehiculo.Categoria.CARGA) {
            throw new IllegalArgumentException("TipoVehiculo no válido para VehiculoCarga.");
        }
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public int calcularPrecioFinal() {
        int extra = capacidadToneladas * 25000;
        return precioBase + extra;
    }

    // Getters y Setters
    public int getCapacidadToneladas() { return capacidadToneladas; }
    public void setCapacidadToneladas(int capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }
}