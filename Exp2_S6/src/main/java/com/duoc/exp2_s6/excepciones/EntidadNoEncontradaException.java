package com.duoc.exp2_s6.excepciones;

/**
 * Se lanza cuando no se encuentra una entidad (usuario o producto)
 * con el ID proporcionado.
 */
public class EntidadNoEncontradaException extends RuntimeException {
    public EntidadNoEncontradaException(String entidad, String id) {
        super(String.format("%s no encontrado con ID: %s", entidad, id));
    }

    public EntidadNoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
}