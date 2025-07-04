package com.duoc.exp3_s7.threads;

import com.duoc.exp3_s7.model.ListaPrimos;
import com.duoc.exp3_s7.utils.UtilesPrimos;

public class HiloConteoPrimos extends Thread {
    private final int inicio;
    private final int fin;
    private final int hilos;
    private final Runnable alFinalizar;
    private ListaPrimos resultado;
    private boolean completado = false;

    public HiloConteoPrimos(int inicio, int fin, int hilos, Runnable alFinalizar) {
        this.inicio = inicio;
        this.fin = fin;
        this.hilos = hilos;
        this.alFinalizar = alFinalizar;
    }

    @Override
    public void run() {
        try {
            resultado = UtilesPrimos.generarListaPrimos(inicio, fin, hilos);
        } catch (InterruptedException e) {
            System.err.println("Error en conteo de primos: " + e.getMessage());
        } finally {
            completado = true;
            if (alFinalizar != null) {
                alFinalizar.run();
            }
        }
    }

    public boolean estaListo() {
        return completado;
    }

    public ListaPrimos getResultado() {
        return resultado;
    }
}