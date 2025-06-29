package com.duoc.exp2_s6.modelo;

import java.util.Objects;

public class Coleccionable extends Producto {
    private final String franquicia; // Ej: Marvel, Star Wars, o "Material original"
    private final String edicion;    // Ej: "25 Aniversario", "Comic-Con Exclusive", etc.

    public Coleccionable(String id, String titulo, double precio, int stock, String franquicia, String edicion) {
        super(id, titulo, precio, stock);
        this.franquicia = Objects.requireNonNullElse(franquicia, "Material original").trim();
        this.edicion    = Objects.requireNonNull(edicion).trim();
    }

    @Override
    public String getTipo() {
        return "Coleccionable";
    }

    public String getFranquicia() {
        return franquicia.isBlank() ? "Material original" : franquicia;
    }

    public String getEdicion() {
        return edicion;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Franquicia: %s | Edición: %s", getFranquicia(), edicion);
    }
}