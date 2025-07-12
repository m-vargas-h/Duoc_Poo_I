package com.duoc.exp3_s8.threads;

import com.duoc.exp3_s8.model.PrimeList;

import java.util.concurrent.BlockingQueue;

public class RangePrimeProducer implements Runnable {
    private final int start;
    private final int end;
    private final BlockingQueue<Integer> queue;
    private final boolean mostrarProgreso;

    public RangePrimeProducer(int start, int end, BlockingQueue<Integer> queue, boolean mostrarProgreso) {
        this.start = start;
        this.end = end;
        this.queue = queue;
        this.mostrarProgreso = mostrarProgreso;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        int total = end - start + 1;
        int generados = 0;
        int prevPorcentaje = -1;

        for (int i = start; i <= end; i++) {
            if (PrimeList.isPrime(i)) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            generados++;
            if (mostrarProgreso) {
                int porcentaje = (generados * 100) / total;
                if (porcentaje != prevPorcentaje) {
                    System.out.print("\r[" + threadName + "] Progreso: " + porcentaje + "%");
                    System.out.flush();
                    prevPorcentaje = porcentaje;
                }
            }
        }

        // Finalización opcional
        if (mostrarProgreso) {
            System.out.println();
        }

        // Insertar señal de fin si corresponde
        try {
            queue.put(-1); // poison pill
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}