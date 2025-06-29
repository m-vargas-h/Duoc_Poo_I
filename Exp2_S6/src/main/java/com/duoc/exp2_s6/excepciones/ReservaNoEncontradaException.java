package com.duoc.exp2_s6.excepciones;

/**
 * Se lanza cuando no existe una reserva con el ID dado
 * o ya fue cancelada/cumplida.
 */
public class ReservaNoEncontradaException extends RuntimeException {
    public ReservaNoEncontradaException(String reservaId) {
        super("Reserva no encontrada con ID: " + reservaId);
    }
}