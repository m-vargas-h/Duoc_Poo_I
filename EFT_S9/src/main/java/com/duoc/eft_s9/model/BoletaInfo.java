package com.duoc.eft_s9.model;

import java.time.LocalDate;

public class BoletaInfo {
    private final String id;
    private final String patente;
    private final String tipo;
    private final int dias;
    private final double subtotal;
    private final double descuento;
    private final double iva;
    private final double total;
    private final LocalDate fechaEmision;


    public BoletaInfo(String id, String patente, String tipo, int dias,double subtotal, double descuento,
                      double iva, double total, LocalDate fechaEmision) {
        this.id = id;
        this.patente = patente;
        this.tipo = tipo;
        this.dias = dias;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.total = total;
        this.fechaEmision = fechaEmision;
    }

    public String getId() { return id; }
    public String getPatente() { return patente; }
    public String getTipo() { return tipo; }
    public int getDias() { return dias; }
    public double getSubtotal() { return subtotal; }
    public double getDescuento() { return descuento; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }
    public LocalDate getFechaEmision() { return fechaEmision; }

    @Override
    public String toString() {
        return String.format("Boleta #%s | Patente: %s | Tipo: %s | Días: %d | Total: $%.0f",
                id, patente, tipo, dias, total);
    }
}