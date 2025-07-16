package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public class VehiculoPasajeros extends Vehiculo {
    private int capacidadMaxima;

    public VehiculoPasajeros(String patente, String marca, int anio, int precioBase,
                             TipoVehiculo tipoVehiculo, int capacidadMaxima) {
        super(patente, marca, anio, precioBase, tipoVehiculo);
        if (tipoVehiculo.getCategoria() != TipoVehiculo.Categoria.PASAJEROS) {
            throw new IllegalArgumentException("TipoVehiculo no válido para VehiculoPasajeros.");
        }
        this.capacidadMaxima = capacidadMaxima;
    }

    @Override
    public int calcularPrecioFinal() {
        int extra = capacidadMaxima * 8000;
        return precioBase + extra;
    }

    // Getters y Setters
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
}