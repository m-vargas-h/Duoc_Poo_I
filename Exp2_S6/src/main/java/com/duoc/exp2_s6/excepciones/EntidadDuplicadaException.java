package com.duoc.exp2_s6.excepciones;

/**
 * Se lanza cuando se intenta agregar una entidad (usuario o producto)
 * cuyo ID ya existe en el sistema.
 */
public class EntidadDuplicadaException extends RuntimeException {
    public EntidadDuplicadaException(String entidad, String id) {
        super(String.format("%s ya existe con ID: %s", entidad, id));
    }

    public EntidadDuplicadaException(String message) {
        super(message);
    }
}
