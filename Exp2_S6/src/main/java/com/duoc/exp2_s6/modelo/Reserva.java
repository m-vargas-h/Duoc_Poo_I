package com.duoc.exp2_s6.modelo;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Reserva {
    public enum Estado { PENDIENTE, CUMPLIDA, CANCELADA }

    private final String id;
    private final Usuario usuario;
    private final Producto producto;
    private final int cantidad;
    private final LocalDateTime fechaHora;
    private Estado estado;

    public Reserva(Usuario usuario, Producto producto, int cantidad) {
        this.id         = UUID.randomUUID().toString();
        this.usuario    = Objects.requireNonNull(usuario, "Usuario no puede ser null");
        this.producto   = Objects.requireNonNull(producto, "Producto no puede ser null");
        if (cantidad < 1) throw new IllegalArgumentException("Cantidad mínima 1");
        this.cantidad   = cantidad;
        this.fechaHora  = LocalDateTime.now();
        this.estado     = Estado.PENDIENTE;
    }

    public String getId() { 
        return id; 
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

    public Estado getEstado() { 
        return estado; 
    }

    public void cumplir() {
        if (estado != Estado.PENDIENTE) 
            throw new IllegalStateException("Reserva no está pendiente");
        estado = Estado.CUMPLIDA;
    }

    public void cancelar() {
        if (estado != Estado.PENDIENTE) 
            throw new IllegalStateException("Reserva no está pendiente");
        estado = Estado.CANCELADA;
    }

    @Override
    public String toString() {
        return String.format(
            "[%s] Reserva %s: %s x%d por %s → %s",
            fechaHora,
            id,
            producto.getTitulo(),
            cantidad,
            usuario.getNombre(),
            estado
        );
    }
}