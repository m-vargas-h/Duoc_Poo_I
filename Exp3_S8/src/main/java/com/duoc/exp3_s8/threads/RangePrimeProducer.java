package com.duoc.exp3_s8.threads;

import java.util.concurrent.BlockingQueue;
import com.duoc.exp3_s8.model.PrimeList;

public class RangePrimeProducer implements Runnable {
    private final int start;
    private final int end;
    private final BlockingQueue<Integer> queue;
    private final boolean mostrarProgreso;

    public RangePrimeProducer(int start, int end,
                              BlockingQueue<Integer> queue,
                              boolean mostrarProgreso) {
        this.start = start;
        this.end = end;
        this.queue = queue;
        this.mostrarProgreso = mostrarProgreso;
    }

    @Override
    public void run() {
        int total = end - start + 1;
        String threadName = Thread.currentThread().getName();

        for (int i = start; i <= end; i++) {
            if (PrimeList.isPrime(i)) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    System.out.println("[" + threadName + "] Interrumpido al añadir " + i);
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            if (mostrarProgreso && ((i - start) % 10_000 == 0 || i == end)) {
                int progreso = (i - start + 1) * 100 / total;
                System.out.println("[" + threadName + "] Progreso: " + progreso + "%");
            }
        }
    }
}