package com.duoc.exp3_s7.model;

import java.util.ArrayList;

public class ListaPrimos extends ArrayList<Integer> {
    private static final long serialVersionUID = 1L;

    /**
     * Verifica si un número es primo.
     * @param numero valor a verificar
     * @return true si es primo, false si no
     */
    public boolean esPrimo(int numero) {
        if (numero < 2) return false;
        int limite = (int) Math.sqrt(numero);
        for (int i = 2; i <= limite; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    @Override
    public synchronized boolean add(Integer numero) {
        if (!esPrimo(numero)) {
            throw new IllegalArgumentException(numero + " no es un número primo.");
        }
        return super.add(numero);
    }

    @Override
    public synchronized boolean remove(Object objeto) {
        if (!(objeto instanceof Integer) || !esPrimo((Integer) objeto)) {
            throw new IllegalArgumentException(objeto + " no es primo o no es válido.");
        }
        return super.remove(objeto);
    }

    /**
     * @return Cantidad de primos almacenados actualmente
     */
    public int contarPrimos() {
        return this.size();
    }
}