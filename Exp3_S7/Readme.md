# Semana 7: Programando por hilos: Thread

# Sistema de verificación PrimeSecure

## 🧠 Descripción del proyecto
Aplicación modular en Java orientada a la generación de números primos dentro de un rango definido por el usuario. Utiliza multithreading para acelerar el procesamiento y mejorar la experiencia mediante una interfaz no bloqueante con retroalimentación dinámica.

---

## 🚀 Características principales

- Conteo concurrente de primos distribuidos por bloques usando múltiples hilos.
- Validación robusta de entrada, incluyendo límite máximo configurable.
- Menú interactivo que permite navegar mientras se ejecutan tareas en segundo plano.
- Guardado asincrónico del resultado en archivos `.txt`.
- Nombres de archivo generados dinámicamente según el rango elegido.
- Sistema de mensajes pendientes para mostrar resultados de procesos automáticos sin mezclar salida del menú.

---

## 🧩 Estructura del proyecto

com.duoc.exp3_s7
- `model`: Representaciones internas como ListaPrimos
    - **ListaPrimos.java**
- `threads`: Clases concurrentes para trabajo con hilos
    - **HiloConteoPrimos.java**
    - **TrabajoPrimos**
- `ui`: Interfaz de usuario principal
    - **MenuPrincipal**
- `utils`: Utilidades para persistencia y validaciones
    - **PersistenciaInfo.java**
    - **UtilesPrimos.java**
- **Exp3_S7.java**: Main para lanzar el sistema

---

## 🎮 Opciones del menú principal

1. Contar números primos en un rango
2. Verificar número primo
3. Guardar conteo de números primos
4. Salir

Los mensajes generados automáticamente (como finalización de conteo o guardado) se identifican con etiquetas claras:
- `[SISTEMA]` para identificar mensajes automáticos del sistema, de mensajes propios del menu.
- `[VALIDACIÓN]` para informar sobre errores al manejar datos.

---

## ⚙️ Flujo de ejecución concurrente

- Cada tarea de conteo se delega a varias instancias de `TrabajoPrimos`.
- Coordinadas por `HiloConteoPrimos` con callback integrado.
- El menú principal se mantiene activo mientras los procesos corren en segundo plano.
- La salida de los hilos se recolecta y muestra ordenadamente gracias a una cola de mensajes pendientes.

---

## 📁 Formato de archivos generados

Los archivos se almacenan en `Data/` y adoptan el formato:
Primos_inicio-fin.txt

Incluyen:
- Total de primos encontrados
- Listado completo, uno por línea

---

## 🛡️ Requisitos técnicos

- Java 17 o superior
- Sistema operativo con soporte multithreading
- Permisos de escritura para crear archivos en `Data/`
