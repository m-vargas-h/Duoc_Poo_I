# Historial de cambios

## 05/07/2025 - Refinamiento visual y de experiencia

- Se integraron prefijos de origen en mensajes automáticos para evitar confusiones entre mensajes automáticos y mensajes propios de la utilización del menu:
    - `[SISTEMA]` para identificar mensajes automáticos del sistema, de mensajes propios del menu.
    - `[VALIDACIÓN]` para informar sobre errores al manejar datos.
- Se consolidó la salida en consola para mostrar sólo etiquetas en procesos automáticos, manteniendo legibilidad sin sobrecargar la interfaz.

### 04/07/2025 - Mejoras del flujo de trabajo
- Mejora en el flujo concurrente
    -**Conteo de primos ejecutado en segundo plano** usando `HiloConteoPrimos`, permitiendo navegación libre por el menú mientras se procesa.
    -**Notificación automática al finalizar conteo** mediante callback `Runnable`, sin bloquear la entrada del usuario.
- Guardado asíncrono optimizado
    - El guardado de archivos `.txt` se realiza en un **hilo dedicado**, sin bloquear el flujo principal.
    - Se centralizó la ruta de salida (`/Data`) y se generaron nombres dinámicos según el rango: `primos_inicio-fin.txt`.
- Experiencia de usuario mejorada
    - Se estableció un **límite máximo configurable** de 2.000.000.000 números para prevenir lentitud y errores.
    - Se eliminó `Scanner.nextLine()` dentro de hilos para evitar excepciones por lectura simultánea (`IndexOutOfBoundsException`).
    - **Se integró una cola de mensajes pendientes** para imprimir, de forma ordenada, cualquier salida generada por hilos en ejecución.
    - Se muestran mensajes como:
        - Conteo en curso…
        - Conteo finalizado: ...
        - Archivo guardado en: ...


### 03/07/2025 - Reestructuración general
- Se modularizó todo el sistema en componentes separados, organizados por responsabilidad funcional:
    - `ListaPrimos`: Subclase de **ArrayList<Integer>** que verifica y almacena solo números primos.
    - `TrabajoPrimos`: Hilo individual encargado de verificar y agregar primos dentro de un rango.
    - `MenuPrincipal`: Interfaz de usuario vía consola con 4 opciones claras y simplificadas.
    - `UtilesPrimos`: Clase utilitaria para ejecución de múltiples hilos que generan primos.
    - `PersistenciaInfo`: Clase encargada de guardar los resultados en archivos .txt formateados.
    - `Exp3_S7`: Clase principal del proyecto, que invoca el menú de forma modular.
- Se implemento sistema de persistencia que puede guardar un archivo .txt con el resultado de la revision de numero primos ingresada por el usuario.
- Se creo menu que permite una navegación mas fluida por el sistema, ademas de permitir trabajar con rangos personalizados de números a revisar.


### 02/07/2025 - Carga inicial proyecto semana 7
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
