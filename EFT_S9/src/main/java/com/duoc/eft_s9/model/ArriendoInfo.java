package com.duoc.eft_s9.model;

import java.time.LocalDate;

public class ArriendoInfo {
    private final String rutCliente;
    private final String nombreCliente;
    private final LocalDate fecha;
    private final String idBoleta;

    public ArriendoInfo(String rutCliente, String nombreCliente, LocalDate fecha, String idBoleta) {
        this.rutCliente = rutCliente;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.idBoleta = idBoleta;
    }

    public String getRutCliente() { 
        return rutCliente; 
    }

    public String getNombreCliente() { 
        return nombreCliente; 
    }

    public LocalDate getFecha() { 
        return fecha; 
    }

    public String getIdBoleta() { 
        return idBoleta; 
    }

    @Override
    public String toString() {
        return String.format("Boleta: %s | Fecha: %s | Cliente: %s (%s)",
            idBoleta, fecha, nombreCliente, rutCliente);
    }
}