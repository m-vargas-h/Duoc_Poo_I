# Historial de cambios

## 02/07/2025 - Carga inicial proyecto semana 7
- Creación del proyecto `Exp3_S7` con las siguientes funciones
    - Implementación de la clase `PrimeList`:
        - Extiende **ArrayList<Integer>** y utiliza el metodo **isPrime(int)** para verificar si un numero entregado es primo.
        - Sobrescribe **add(Integer)** y **remove(Object)** lanzando la excepción **IllegalArgumentException** si un numero entregado no es primo. Ademas incluye **getPrimesCount()** que devuelve el tamaño de la lista de primos.
    - Desarrollo de `PrimeWorker`:
        - Extiende Thread.
        - Recorre un rango de números, invoca **isPrime**, y agrega primos a la lista compartida.
    - Creación de Main (Exp3_S7):
        - Divide el rango hasta 100 000 entre 4 hilos.
        - Inicia y espera (join) cada PrimeWorker.
        - Muestra en consola el total de primos detectados (9592).
