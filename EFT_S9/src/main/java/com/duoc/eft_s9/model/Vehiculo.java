package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public abstract class Vehiculo {
    protected String patente;
    protected String marca;
    protected int anio;
    protected int precioBase;
    protected TipoVehiculo tipoVehiculo;

    public Vehiculo(String patente, String marca, int anio, int precioBase, TipoVehiculo tipoVehiculo) {
        this.patente = patente;
        this.marca = marca;
        this.anio = anio;
        this.precioBase = precioBase;
        this.tipoVehiculo = tipoVehiculo;
    }

    public abstract int calcularPrecioFinal();

    // Getters
    public String getPatente() { return patente; }
    public String getMarca() { return marca; }
    public int getAnio() { return anio; }
    public int getPrecioBase() { return precioBase; }
    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }

    // Setters
    public void setMarca(String marca) { this.marca = marca; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setPrecioBase(int precioBase) { this.precioBase = precioBase; }
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    @Override
    public String toString() {
        return String.format("Patente: %s | Marca: %s | Año: %d | Tipo: %s | Precio Base: %d",
                patente, marca, anio, tipoVehiculo.name(), precioBase);
    }
}