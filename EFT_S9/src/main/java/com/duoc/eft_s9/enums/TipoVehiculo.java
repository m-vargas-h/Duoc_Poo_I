package com.duoc.eft_s9.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TipoVehiculo {
    // Carga
    TOLVA(Categoria.CARGA),
    JAULA(Categoria.CARGA),
    PLATAFORMA(Categoria.CARGA),
    CAMA_BAJA(Categoria.CARGA),
    CAMION(Categoria.CARGA),

    // Pasajeros
    SUV(Categoria.PASAJEROS),
    MINIBUS(Categoria.PASAJEROS),
    SEDAN(Categoria.PASAJEROS),
    VAN(Categoria.PASAJEROS);

    public enum Categoria {
        CARGA,
        PASAJEROS
    }

    private final Categoria categoria;

    TipoVehiculo(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    // Métodos utilitarios para filtros
    public static List<TipoVehiculo> tiposPorCategoria(Categoria categoria) {
        return Arrays.stream(values())
                     .filter(tipo -> tipo.getCategoria() == categoria)
                     .collect(Collectors.toList());
    }
}