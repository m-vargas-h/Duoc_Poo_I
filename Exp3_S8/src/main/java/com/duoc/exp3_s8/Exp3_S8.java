package com.duoc.exp3_s8;

import com.duoc.exp3_s8.model.PrimeList;
import com.duoc.exp3_s8.threads.RangePrimeProducer;
import com.duoc.exp3_s8.threads.PrimeConsumer;
import com.duoc.exp3_s8.io.FileHandler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Exp3_S8 {
    private static final int TOTAL_RANGE = 50_000;
    private static final int PRODUCERS   = 4;
    private static final int CONSUMERS   = 2;
    private static final String CSV_PATH = "primos.csv";
    private static final String OUT_PATH = "mensajes_enc.txt";

    public static void main(String[] args) throws Exception {
        // 1. Preparamos lista y cola
        PrimeList primeList = new PrimeList();
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // 2. Cargamos primos desde CSV
        FileHandler.loadPrimesFromCsv(CSV_PATH, primeList);

        // 3. Arrancamos productores y consumidores
        ExecutorService exec = Executors.newFixedThreadPool(PRODUCERS + CONSUMERS);
        int chunk = TOTAL_RANGE / PRODUCERS;
        for (int i = 0; i < PRODUCERS; i++) {
            int start = i * chunk + 1;
            int end   = (i == PRODUCERS - 1)
                        ? TOTAL_RANGE
                        : start + chunk - 1;
            exec.submit(new RangePrimeProducer(start, end, queue));
        }
        for (int i = 0; i < CONSUMERS; i++) {
            exec.submit(new PrimeConsumer(queue, primeList));
        }

        // 4. Esperamos a que terminen los productores
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);

        // 5. Enviamos “poison pills” para cerrar consumidores
        for (int i = 0; i < CONSUMERS; i++) {
            queue.put( PrimeConsumer.getPoisonPill() );
        }

        // 6. Mostramos resultados
        System.out.println("Total de primos encontrados: " 
                           + primeList.getPrimesCount());

        // 7. Encriptamos y guardamos mensajes
        List<String> messages = Arrays.asList(
            "Voto A: candidato1", 
            "Voto B: candidato2",
            "Voto C: candidato3"
        );
        FileHandler.writeEncryptedMessages(OUT_PATH, messages, primeList);

        System.out.println("Mensajes encriptados guardados en: " + OUT_PATH);
    }
}
