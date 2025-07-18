package com.duoc.eft_s9.model;

import com.duoc.eft_s9.enums.TipoVehiculo;

public abstract class Vehiculo {
    protected String patente;
    protected String marca;
    protected int anio;
    protected int precioBase;
    protected TipoVehiculo tipoVehiculo;
    private boolean disponible;

    // Constructor completo
    public Vehiculo(String patente, String marca, int anio, int precioBase,
                    TipoVehiculo tipoVehiculo, boolean disponible) {
        this.patente = patente;
        this.marca = marca;
        this.anio = anio;
        this.precioBase = precioBase;
        this.tipoVehiculo = tipoVehiculo;
        this.disponible = disponible;
    }

    // Constructor por defecto con disponibilidad en true
    public Vehiculo(String patente, String marca, int anio, int precioBase, TipoVehiculo tipoVehiculo) {
        this(patente, marca, anio, precioBase, tipoVehiculo, true);
    }

    // Método abstracto que se implementa en clases hijas
    public abstract int calcularPrecioFinal();

    // Getters
    public String getPatente() { return patente; }
    public String getMarca() { return marca; }
    public int getAnio() { return anio; }
    public int getPrecioBase() { return precioBase; }
    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public boolean isDisponible() { return disponible; }

    // Setters
    public void setMarca(String marca) { this.marca = marca; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setPrecioBase(int precioBase) { this.precioBase = precioBase; }
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return String.format(
            "Patente: %s | Marca: %s | Año: %d | Tipo: %s | Precio Base: %d | Disponible: %s",
            patente, marca, anio, tipoVehiculo.name(), precioBase,
            disponible ? "Sí" : "No"
        );
    }
}