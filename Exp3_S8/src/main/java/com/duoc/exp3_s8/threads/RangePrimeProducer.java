package com.duoc.exp3_s8.threads;

import com.duoc.exp3_s8.model.PrimeList;
import java.util.concurrent.BlockingQueue;

public class RangePrimeProducer implements Runnable {
    private final int start, end;
    private final BlockingQueue<Integer> queue;

    public RangePrimeProducer(int start, int end,
                              BlockingQueue<Integer> queue) {
        this.start = start;
        this.end   = end;
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int n = start; n <= end; n++) {
            if (PrimeList.isPrime(n)) {
                try {
                    queue.put(n);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}