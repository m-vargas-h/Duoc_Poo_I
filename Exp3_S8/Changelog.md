# Historial de cambios

## 09/07/2025 - Creación proyecto semana 8
- Migración de la base de proyecto anterior a nuevo sistema, las clases fueron nombradas en ingles y refactorizadas para ajustarse de mejor forma al proyecto actual.
- Modelo de datos:
    - Refactorización de ListaPrimos a `PrimeList`.
    - Implementación de `static isPrime(int)`, `synchronized add/remove`, `getPrimesCount()`.
- Concurrencia
    - Eliminación de HiloConteoPrimos y TrabajoPrimos.
    - Creación de `RangePrimeProducer` (productor) y `PrimeConsumer` (consumidor) usando BlockingQueue<Integer>.
- Desarrollo de Exp3_S8 (main) que:
    - Carga primos desde CSV (omite si no existe).
    - Dispara productores y consumidores concurrentes.
    - Envía “poison pills” para cerrar consumidores.
    - Imprime total de primos.
    - Guarda mensajes de contexto en archivo.

