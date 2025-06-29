package com.duoc.exp2_s6.modelo;

import java.util.Objects;
import java.util.Optional;

public class JuegoMesa extends Producto {
    public static final String COD_TIPO = "005";
    private final String creador; // Puede ser empresa, autor, colectivo...
    private final int jugadoresMinimo;
    private final Optional<Integer> jugadoresMaximo;
    private final int edadRecomendada;

    // Constructor sin jugadores máximos
    public JuegoMesa(String id, String titulo, double precio, int stock, String creador, int jugadoresMinimo,
                     int edadRecomendada) {
        super(id, titulo, precio, stock);
        this.creador = Objects.requireNonNull(creador);
        this.jugadoresMinimo = jugadoresMinimo;
        this.jugadoresMaximo = Optional.empty();
        this.edadRecomendada = edadRecomendada;
    }

    // Constructor con jugadores máximos
    public JuegoMesa(String id, String titulo, double precio, int stock, String creador, int jugadoresMinimo, 
                     int jugadoresMaximo, int edadRecomendada) {
        super(id, titulo, precio, stock);
        this.creador = Objects.requireNonNull(creador);
        this.jugadoresMinimo = jugadoresMinimo;
        this.jugadoresMaximo = Optional.of(jugadoresMaximo);
        this.edadRecomendada = edadRecomendada;
    }

    @Override
    public String getTipo() {
        return "JuegoMesa";
    }

    @Override
    public String getCodigoTipo() {
        return COD_TIPO;
    }

    public String getCreador() {
        return creador;
    }

    public int getJugadoresMinimo() {
        return jugadoresMinimo;
    }

    public Optional<Integer> getJugadoresMaximo() {
        return jugadoresMaximo;
    }

    public int getEdadRecomendada() {
        return edadRecomendada;
    }

    @Override
    public String toString() {
        String jugadores = jugadoresMaximo
            .map(max -> jugadoresMinimo + "–" + max)
            .orElse(String.valueOf(jugadoresMinimo));

        return super.toString() +
               String.format(" | Creador: %s | Jugadores: %s | Edad: %d+",
                             creador, jugadores, edadRecomendada);
    }
}