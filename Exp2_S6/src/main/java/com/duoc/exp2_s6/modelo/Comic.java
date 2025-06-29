package com.duoc.exp2_s6.modelo;

import java.util.Objects;
import java.util.Optional;

public class Comic extends Producto {
    private String editorial;
    private Optional<Integer> volumen; // Volumen opcional, vacío si no es parte de una serie

    // Constructor para cómics sueltos (sin volumen)
    public Comic(String id, String titulo, double precio, int stock, String editorial) {
        super(id, titulo, precio, stock);
        this.editorial = Objects.requireNonNull(editorial);
        this.volumen   = Optional.empty();
    }

    // Constructor para cómics de una serie (con volumen)
    public Comic(String id, String titulo, double precio, int stock, String editorial, int volumen) {
        super(id, titulo, precio, stock);
        this.editorial = Objects.requireNonNull(editorial);
        this.volumen   = Optional.of(volumen);
    }

    @Override
    public String getTipo() {
        return "Comic";
    }

    public String getEditorial() {
        return editorial;
    }

    /** Devuelve Optional.empty() si no es parte de serie */
    public Optional<Integer> getVolumen() {
        return volumen;
    }

    @Override
    public String toString() {
        String base = super.toString() + " | Editorial: " + editorial;
        // Muestra el volumen sólo si está presente
        return volumen
                .map(v -> base + " | Vol. " + v)
                .orElse(base);
    }
}