package com.duoc.exp3_s7.threads;

import com.duoc.exp3_s7.model.PrimeList;

public class PrimeWorker extends Thread {
    private final int from;
    private final int to;
    private final PrimeList sharedList;

    public PrimeWorker(int from, int to, PrimeList sharedList) {
        this.from = from;
        this.to = to;
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        for (int n = from; n <= to; n++) {
            if (sharedList.isPrime(n)) {
                sharedList.add(n);
            }
        }
    }
}