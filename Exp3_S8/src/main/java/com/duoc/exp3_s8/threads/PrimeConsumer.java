package com.duoc.exp3_s8.threads;

import com.duoc.exp3_s8.model.PrimeList;
import java.util.concurrent.BlockingQueue;

public class PrimeConsumer implements Runnable {
    private static final int POISON_PILL = -1;
    private final BlockingQueue<Integer> queue;
    private final PrimeList primeList;

    public PrimeConsumer(BlockingQueue<Integer> queue,
                         PrimeList primeList) {
        this.queue     = queue;
        this.primeList = primeList;
    }

    public static int getPoisonPill() {
        return POISON_PILL;
    }


    @Override
    public void run() {
        try {
            while (true) {
                int n = queue.take();
                if (n == POISON_PILL) break;
                primeList.add(n);
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}