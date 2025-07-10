package com.duoc.exp3_s8.threads;

import com.duoc.exp3_s8.model.PrimeList;

public class PrimeCountThread implements Runnable {
    private final int startRange;
    private final int endRange;
    private final PrimeList primeList;
    private final Runnable onComplete;

    public PrimeCountThread(int startRange,
                            int endRange,
                            PrimeList primeList,
                            Runnable onComplete) {
        this.startRange = startRange;
        this.endRange   = endRange;
        this.primeList  = primeList;
        this.onComplete = onComplete;
    }

    @Override
    public void run() {
        for (int n = startRange; n <= endRange; n++) {
            try {
                primeList.add(n);
            } catch (IllegalArgumentException ignored) {
                // no es primo: descartado
            }
        }
        if (onComplete != null) {
            onComplete.run();
        }
    }
}