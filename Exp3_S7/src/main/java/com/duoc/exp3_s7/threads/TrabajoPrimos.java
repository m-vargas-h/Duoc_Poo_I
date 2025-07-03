package com.duoc.exp3_s7.threads;

import com.duoc.exp3_s7.model.ListaPrimos;

public class TrabajoPrimos extends Thread {
    private final int inicio;
    private final int fin;
    private final ListaPrimos listaCompartida;

    public TrabajoPrimos(int inicio, int fin, ListaPrimos listaCompartida) {
        this.inicio = inicio;
        this.fin = fin;
        this.listaCompartida = listaCompartida;
    }

    @Override
    public void run() {
        for (int numero = inicio; numero <= fin; numero++) {
            if (listaCompartida.esPrimo(numero)) {
                listaCompartida.add(numero);
            }
        }
    }
}