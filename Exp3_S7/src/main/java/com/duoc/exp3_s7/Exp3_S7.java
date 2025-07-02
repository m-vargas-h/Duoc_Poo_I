package com.duoc.exp3_s7;

import com.duoc.exp3_s7.model.PrimeList;
import com.duoc.exp3_s7.threads.PrimeWorker;

import java.util.ArrayList;
import java.util.List;

public class Exp3_S7 {
    public static void main(String[] args) {
        // 1. Instancia compartida
        PrimeList primes = new PrimeList();

        // 2. Configuración de rangos y hilos
        int maxNumber = 100_000;
        int threadCount = 4;
        int chunk = maxNumber / threadCount;

        List<PrimeWorker> workers = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            int start = i * chunk + 1;
            int end   = (i == threadCount - 1) ? maxNumber : (start + chunk - 1);
            workers.add(new PrimeWorker(start, end, primes));
        }

        // 3. Iniciar hilos
        workers.forEach(Thread::start);

        // 4. Esperar terminación
        for (PrimeWorker w : workers) {
            try {
                w.join();
            } catch (InterruptedException e) {
                System.err.println("Hilo interrumpido: " + e.getMessage());
            }
        }

        // 5. Resultado
        System.out.println("Primos encontrados hasta " 
            + maxNumber + ": " + primes.getPrimesCount());
    }
}
