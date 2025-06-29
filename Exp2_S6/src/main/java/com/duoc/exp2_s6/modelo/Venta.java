package com.duoc.exp2_s6.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Venta {
    private final Usuario usuario;
    private final Producto producto;
    private final int cantidad;
    private final LocalDateTime fechaHora;

    /**
     * Crea una venta de un cierto producto por un usuario.
     *
     * @param usuario   quien realiza la compra (no nulo)
     * @param producto  producto comprado (no nulo)
     * @param cantidad  unidades vendidas; debe ser >= 1
     * @param fechaHora fecha y hora de la venta (no nulo)
     */
    public Venta(Usuario usuario,
                 Producto producto,
                 int cantidad,
                 LocalDateTime fechaHora) {
        this.usuario   = Objects.requireNonNull(usuario,   "Usuario no puede ser nulo");
        this.producto  = Objects.requireNonNull(producto,  "Producto no puede ser nulo");
        if (cantidad < 1) {
            throw new IllegalArgumentException("Cantidad debe ser al menos 1");
        }
        this.cantidad  = cantidad;
        this.fechaHora = Objects.requireNonNull(fechaHora, "FechaHora no puede ser nulo");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /** Calcula el monto total de esta venta. */
    public double getTotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return String.format(
            "[%s] %s vendida a %s x%d → $%.2f (total: $%.2f)",
            fechaHora,
            producto.getTitulo(),
            usuario.getNombre(),
            cantidad,
            producto.getPrecio(),
            getTotal()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, producto, fechaHora, cantidad);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venta)) return false;
        Venta v = (Venta) o;
        return cantidad == v.cantidad
            && usuario.equals(v.usuario)
            && producto.equals(v.producto)
            && fechaHora.equals(v.fechaHora);
    }
}