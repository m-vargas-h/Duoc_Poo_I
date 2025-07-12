# Semana 8: Colecciones sincronizadas, Queue y Topic

# SafeVoteSystem

## 🎯 Descripción general

Este proyecto implementa un sistema orientado a objetos que genera, guarda y carga listas de números primos en un rango definido por el usuario. Utiliza concurrencia para maximizar eficiencia y modularidad para garantizar extensibilidad. La experiencia del usuario se potencia mediante una interfaz de consola fluida, persistencia transparente y retroalimentación contextual.

---

## ⚙️ Características principales

- **Generación concurrente de primos** usando múltiples hilos y `BlockingQueue`
- **Medición de tiempos** en tareas clave para evaluar rendimiento en consola
- **Progreso visual en segundo plano**, con control condicional por tarea activa
- **Carga inteligente de archivos** desde la carpeta `/Data`, con selección por menú
- **Guardado con marca de tiempo (`ddMMMyy_HHmm`)**, evitando sobrescritura y facilitando trazabilidad
- **Persistencia CSV con encabezado informativo compatible** para recarga automática

---

## 🔧 Arquitectura del sistema

| Módulo                    | Descripción                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `ConsoleMenu`            | Controlador principal que gestiona interacción, ejecución y navegación     |
| `PrimeList`              | Lista personalizada que admite solo números primos, con validaciones       |
| `PrimeUtils`             | Utilidades de generación concurrente y control de flujo multihilo          |
| `RangePrimeProducer`     | Productor de primos con progreso opcional y señal de terminación           |
| `PrimeConsumer`          | Consumidor que toma datos desde la cola compartida y los almacena          |
| `FileHandler`            | Módulo de persistencia para carga/guardar CSV dentro de `/Exp3_S8/Data`    |

---

## 🧠 Flujo de uso

1. **Ingresar rango de generación** → Usuario define inicio y fin.
2. **Ejecutar generación** → Se lanza tarea concurrente en segundo plano, con progreso opcional.
3. **Guardar primos generados** → Se guarda automáticamente en CSV con nombre versionado por hora.
4. **Cargar desde archivo existente** → Sistema muestra archivos disponibles en `/Data`.

---

## 📄 Formato esperado de archivos CSV para carga manual

El sistema permite cargar listas de números primos desde archivos `.csv` ubicados en la carpeta `/Exp3_S8/Data`. Para que un archivo sea interpretado correctamente, debe seguir este formato:

- ✔️ **Extensión**: `.csv`
- ✔️ **Contenido**:
  - Puede contener una **línea de encabezado informativa** (opcional), como:  
    `"Se han encontrado 100 números primos."`
  - Los números primos pueden estar:
    - Separados por comas: `2,3,5,7,11,...`
    - O en líneas individuales:  
      ```
      2  
      3  
      5  
      7  
      ```
- ✔️ **Solo números primos**: el sistema validará que cada número sea primo; entradas inválidas se omitirán.
- ✔️ **Se toleran líneas vacías o texto auxiliar**: no afectarán el proceso de carga.

🎯 Si el usuario quiere preparar su propia lista, puede usar cualquier editor de texto o planilla (como Excel) y guardar el archivo como `.csv`, asegurándose de que los números sean reales primos.

---

## 🛠️ Requisitos

- Java 8+
- Consola interactiva
- Estructura de carpetas esperada: `/Exp3_S8/Data`

---