package com.duoc.exp3_s8.model;

import java.util.ArrayList;

public class PrimeList extends ArrayList<Integer> {
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        int limit = (int) Math.sqrt(n);
        for (int i = 3; i <= limit; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public synchronized boolean add(Integer n) {
      if (!isPrime(n)) 
          throw new IllegalArgumentException("Solo números primos son válidos");
      return super.add(n);
    }

    @Override
    public synchronized boolean remove(Object o) {
      if (!(o instanceof Integer) || !isPrime((Integer) o)) 
          throw new IllegalArgumentException("Solo números primos son válidos");
      return super.remove(o);
    }

    public int getPrimesCount() {
      return size();
    }
}