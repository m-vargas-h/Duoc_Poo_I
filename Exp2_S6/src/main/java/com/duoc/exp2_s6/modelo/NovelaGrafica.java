package com.duoc.exp2_s6.modelo;

import java.util.Objects;

public class NovelaGrafica extends Producto {
    private final String autor;
    private final int fechaPublicacion;

    public NovelaGrafica(String id, String titulo, double precio, int stock, String autor, int fechaPublicacion) {
        super(id, titulo, precio, stock);
        this.autor = Objects.requireNonNull(autor);
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String getTipo() {
        return "NovelaGrafica";
    }

    public String getAutor() {
        return autor;
    }

    public int getFechaPublicacion() {
        return fechaPublicacion;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Autor: %s | Año: %d", autor, fechaPublicacion);
    }
}