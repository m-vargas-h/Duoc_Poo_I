package com.duoc.exp2_s6.excepciones;

/**
 * Se lanza cuando se intenta vender o reservar más unidades
 * de las que hay en stock.
 */
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String productoId, int disponible, int solicitado) {
        super(String.format(
            "Stock insuficiente para producto %s: disponible=%d, solicitado=%d",
            productoId, disponible, solicitado
        ));
    }

    public StockInsuficienteException(String productoId) {
        super("Stock insuficiente para producto: " + productoId);
    }
}