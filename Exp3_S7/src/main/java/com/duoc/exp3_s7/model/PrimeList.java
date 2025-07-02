package com.duoc.exp3_s7.model;

import java.util.ArrayList;

public class PrimeList extends ArrayList<Integer> {
    private static final long serialVersionUID = 1L;

    public boolean isPrime(int n) {
        if (n < 2) return false;
        int limit = (int) Math.sqrt(n);
        for (int i = 2; i <= limit; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public synchronized boolean add(Integer n) {
        if (!isPrime(n)) {
            throw new IllegalArgumentException(n + " no es primo");
        }
        return super.add(n);
    }

    @Override
    public synchronized boolean remove(Object o) {
        if (!(o instanceof Integer) || !isPrime((Integer) o)) {
            throw new IllegalArgumentException(o + " no es primo o inválido");
        }
        return super.remove(o);
    }

    public int getPrimesCount() {
        return this.size();
    }
}