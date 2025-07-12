# Historial de cambios

## 11/07/2025 - Refinamiento flujo de trabajo
- Se agregó cálculo y presentación del tiempo de ejecución en:
    - handleGenerateRange() para mostrar cuánto tarda generar los primos.
    - handleSavePrimeList() para mostrar cuánto tarda guardar el archivo CSV.
- En handleGenerateRange() se añadió validación para evitar lanzar una nueva tarea si ya hay una en curso.
- El sistema ahora solicita confirmación al usuario para abortar la tarea activa y reemplazarla.
- Visualización compacta de progreso
    - En RangePrimeProducer se cambió println(...) por print("\r...") para que el progreso se actualice en la misma línea de consola.
    - Se agregó flush() y salto de línea final para mantener la salida limpia y no interrumpir mensajes posteriores.
- En FileHandler.savePrimeListRange(...), el nombre del archivo generado incluye una marca de tiempo con formato ddMMMyy_HHmm, permitiendo identificar fácilmente cuándo se creó y evitar sobrescrituras.
- Se corrigieron textos en pantalla para facilitar la comprensión de los mensajes.

### 11/07/2025 - Mejoras en generación de listas y carga/lectura de archivos
- Lógica de generación de primos
    - Se elimino la limitación de tiempo al esperar el cierre de productores y consumidores con awaitTermination(...), permitiendo tareas indefinidas en rangos enormes.
    - Se agregó barra de progreso local por hilo en RangePrimeProducer, mostrando porcentajes solo si se activa manualmente.
    - Se incorporó el parámetro mostrarProgreso en PrimeUtils y RangePrimeProducer, para controlar cuándo se imprime el estado de la tarea.
    - El menú ConsoleMenu fue actualizado para lanzar tareas con o sin progreso visible, según contexto (guardar o iniciar nueva generación).
- Gestión de archivos CSV
    - El método loadPrimesFromCsv(...) fue mejorado para aceptar números separados por coma o por línea, encabezados como "Se han encontrado...", líneas vacías y entradas inválidas sin interrumpir la carga.
    - El método savePrimeListRange(...) ahora genera archivos CSV con encabezado compatible para ser cargado nuevamente por el sistema.
    - La ruta base para leer y guardar fue unificada, usando carpeta Exp3_S8/Data directamente definida dentro de FileHandler.java
- Interfaz de usuario y menú de carga
    - El método handleLoadCsv() ahora muestra archivos disponibles automáticamente, permitiendo al usuario seleccionar desde los .csv encontrados en la carpeta Data.




### 09/07/2025 - Creación proyecto semana 8
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

