package com.duoc.exp2_s6.modelo;

import java.util.Objects;

public class TCG extends Producto {
    private final String nombreJuego;
    private final int edadRecomendada;
    private final TipoTCG tipoProducto;

    public TCG(String id, String titulo, double precio, int stock, String nombreJuego, int edadRecomendada,
               TipoTCG tipoProducto) {
        super(id, titulo, precio, stock);
        this.nombreJuego = Objects.requireNonNull(nombreJuego).trim();
        this.edadRecomendada = edadRecomendada;
        this.tipoProducto = Objects.requireNonNull(tipoProducto);
    }

    @Override
    public String getTipo() {
        return "TCG";
    }

    public String getNombreJuego() { 
        return nombreJuego; 
    }

    public int getEdadRecomendada() { 
        return edadRecomendada;
    }

    public TipoTCG getTipoProducto() { 
        return tipoProducto; 
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            " | Juego: %s | Tipo: %s | Edad: %d+",
            nombreJuego, tipoProducto, edadRecomendada
        );
    }
}