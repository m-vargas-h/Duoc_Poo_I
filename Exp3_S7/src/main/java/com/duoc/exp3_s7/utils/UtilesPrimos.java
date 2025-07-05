package com.duoc.exp3_s7.utils;

import com.duoc.exp3_s7.model.ListaPrimos;
import com.duoc.exp3_s7.threads.TrabajoPrimos;

import java.util.ArrayList;
import java.util.List;

public class UtilesPrimos {

    /**
     * Genera una lista de números primos en el rango indicado usando múltiples hilos.
     *
     * @param inicio        Número desde donde comenzar (inclusive)
     * @param fin           Número hasta donde terminar (inclusive)
     * @param cantidadHilos Número de hilos a utilizar
     * @return ListaPrimos con todos los primos encontrados
     * @throws InterruptedException si algún hilo es interrumpido
     */
    public static ListaPrimos generarListaPrimos(int inicio, int fin, int cantidadHilos) throws InterruptedException {
        ListaPrimos lista = new ListaPrimos();
        int rango = fin - inicio + 1;
        int bloque = rango / cantidadHilos;

        List<TrabajoPrimos> trabajos = new ArrayList<>();

        // Validar que el rango sea válido, es decir, que inicio sea menor o igual a fin
        for (int i = 0; i < cantidadHilos; i++) {
            int inicioBloque = inicio + i * bloque;
            int finBloque = (i == cantidadHilos - 1) ? fin : (inicioBloque + bloque - 1);

            // Asegurar que el bloque no se salga del rango
            trabajos.add(new TrabajoPrimos(inicioBloque, finBloque, lista));
        }

        // Iniciar todos los hilos
        for (TrabajoPrimos t : trabajos) t.start();
        for (TrabajoPrimos t : trabajos) t.join();

        return lista;
    }
}