package com.duoc.exp2_s6.modelo;

public class ResumenVenta {
    private final Producto producto;
    private long cantidadTotal;
    private double ingresoTotal;

    public ResumenVenta(Producto producto) {
        this.producto      = producto;
        this.cantidadTotal = 0;
        this.ingresoTotal  = 0.0;
    }

    /** Acumula una venta en este resumen */
    public void addVenta(Venta v) {
        cantidadTotal += v.getCantidad();
        ingresoTotal  += v.getTotal();
    }

    public Producto getProducto() {
        return producto;
    }

    public long getCantidadTotal() {
        return cantidadTotal;
    }

    public double getIngresoTotal() {
        return ingresoTotal;
    }
}