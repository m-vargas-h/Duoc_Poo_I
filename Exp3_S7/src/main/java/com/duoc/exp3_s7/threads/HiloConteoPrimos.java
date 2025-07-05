package com.duoc.exp3_s7.threads;

import com.duoc.exp3_s7.model.ListaPrimos;
import com.duoc.exp3_s7.utils.UtilesPrimos;

public class HiloConteoPrimos extends Thread {
    private final int inicio;               // Número desde donde comenzar (inclusive)
    private final int fin;                  // Número hasta donde terminar (inclusive)
    private final int hilos;                // Cantidad de hilos a utilizar
    private final Runnable alFinalizar;     // Acción a ejecutar al finalizar el conteo
    private ListaPrimos resultado;          // Lista de números primos encontrados
    private boolean completado = false;     // Indica si el conteo ha finalizado

    // Constructor que recibe el rango de números a evaluar, la cantidad de hilos y una acción a ejecutar al finalizar
    public HiloConteoPrimos(int inicio, int fin, int hilos, Runnable alFinalizar) {
        this.inicio = inicio;
        this.fin = fin;
        this.hilos = hilos;
        this.alFinalizar = alFinalizar;
    }

    @Override
    public void run() {
        // Inicializar la lista de números primos
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