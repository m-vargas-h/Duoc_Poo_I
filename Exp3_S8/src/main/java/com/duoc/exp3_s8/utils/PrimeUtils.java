package com.duoc.exp3_s8.utils;

import com.duoc.exp3_s8.model.PrimeList;
import com.duoc.exp3_s8.threads.RangePrimeProducer;
import com.duoc.exp3_s8.threads.PrimeConsumer;

import java.util.concurrent.*;

public class PrimeUtils {

    private static final int DEFAULT_PRODUCERS = 2;
    private static final int DEFAULT_CONSUMERS = 2;

    public static PrimeList generatePrimeList(int start, int end) throws InterruptedException {
        return generatePrimeList(start, end, DEFAULT_PRODUCERS, DEFAULT_CONSUMERS, false);
    }

    public static PrimeList generatePrimeList(int start, int end, int numProducers, int numConsumers,
                                             boolean mostrarProgreso) throws InterruptedException {
        PrimeList primeList = new PrimeList();
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        ExecutorService prodExec = Executors.newFixedThreadPool(numProducers);
        int total = end - start + 1;
        int chunk = total / numProducers;

        for (int i = 0; i < numProducers; i++) {
            int subStart = start + i * chunk;
            int subEnd   = (i == numProducers - 1) ? end : subStart + chunk - 1;
            prodExec.submit(new RangePrimeProducer(subStart, subEnd, queue, mostrarProgreso));
        }

        prodExec.shutdown();
        while (!prodExec.awaitTermination(5, TimeUnit.SECONDS)) {
            if (mostrarProgreso) {
                System.out.println("[SISTEMA] Esperando a que los productores terminen...");
            }
        }

        for (int i = 0; i < numConsumers; i++) {
            queue.put(PrimeConsumer.getPoisonPill());
        }

        ExecutorService consExec = Executors.newFixedThreadPool(numConsumers);
        for (int i = 0; i < numConsumers; i++) {
            consExec.submit(new PrimeConsumer(queue, primeList));
        }

        consExec.shutdown();
        while (!consExec.awaitTermination(5, TimeUnit.SECONDS)) {
            if (mostrarProgreso) {
                System.out.println("[SISTEMA] Esperando a que los consumidores terminen...");
            }
        }

        return primeList;
    }
}