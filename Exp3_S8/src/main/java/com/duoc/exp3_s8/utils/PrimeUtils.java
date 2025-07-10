package com.duoc.exp3_s8.utils;

import com.duoc.exp3_s8.model.PrimeList;
import com.duoc.exp3_s8.threads.RangePrimeProducer;
import com.duoc.exp3_s8.threads.PrimeConsumer;

import java.util.concurrent.*;

/**
 * Helper para operaciones con números primos.
 */
public class PrimeUtils {

    /**
     * Genera concurrentemente una PrimeList para el rango [start–end]
     * usando numThreads productores y numConsumers consumidores.
     */
    public static PrimeList generatePrimeList(int start,
                                              int end,
                                              int numProducers,
                                              int numConsumers)
            throws InterruptedException {
        PrimeList primeList = new PrimeList();
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // Pool para productores + consumidores
        ExecutorService exec = Executors.newFixedThreadPool(
            numProducers + numConsumers
        );

        // Dividir el rango entre productores
        int total = end - start + 1;
        int chunk = total / numProducers;
        for (int i = 0; i < numProducers; i++) {
            int subStart = start + i * chunk;
            int subEnd   = (i == numProducers - 1)
                           ? end
                           : subStart + chunk - 1;
            exec.submit(
                new RangePrimeProducer(subStart, subEnd, queue)
            );
        }

        // Iniciar consumidores
        for (int i = 0; i < numConsumers; i++) {
            exec.submit(
                new PrimeConsumer(queue, primeList)
            );
        }

        // Esperar a que terminen productores/consumidores
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);

        // Señal de fin para cada consumidor
        for (int i = 0; i < numConsumers; i++) {
            queue.put( PrimeConsumer.getPoisonPill() );
        }

        return primeList;
    }
}