# Historial de cambios

## 16/07/2025 - Mejoras del sistema de boletas y registro
- Implementación de `ValidadorFormato.validarPatente()` para admitir formatos AB-1234 y ABCD-12.
- Centralización de la validación en clase auxiliar.
- Actualización de métodos `agregarVehiculoCarga()` y `agregarVehiculoPasajeros()` para utilizar esta validación.
- Estandarización de claves con `toUpperCase().trim()` al guardar y buscar patentes.
- Ajuste de formato decimal en `guardarEnArchivo(...)` usando `Locale.US`.
- Inclusión de columna `FechaEmisión` en archivo.

---

### 16/07/2025 - Creación métodos y clases de persistencia
- Se creo la clase `ArchivoVehiculoManager` con los metodos correspondientes para poder ejecutar operaciones de lectura/escritura de archivos externos.
  - Se definió la ruta para los archivos mediante la clase auxiliar `RutaArchivo`, de esta forma es posible indicar la ruta especifica de cara archivo necesario para el funcionamiento del sistema.
  - Se definieron métodos para la creación de vehículos de carga y pasajeros en el sistema.
- Cambio de tipos de atributo en modelo base `Vehiculo`: `precioBase` ahora es `int`.
- Ajuste de retorno en `getPrecioBase()` y `getCapacidadToneladas()` para alinearlos con tipos enteros.
- Redefinición del método `calcularPrecioFinal()` para retornar `int`, con ajuste también en clase base.
- Confirmación de lectura y escritura de vehículos desde archivo con campos enteros sin conflictos.
- Revisión de `filtrarPorTipo()` asegurando que funcione correctamente con los vehículos cargados desde archivo.
- Integración de método `gestor.cargarVehiculosDesdeArchivos()` al inicio del sistema para poblar la memoria automáticamente.
- Ajuste de ingreso de datos para evitar conflictos al agregar vehículos en tiempo real.

### 15/07/2025 - Implementación menu principal
- Creación de la clase `MenuPrincipal` en el paquete `ui` como punto de entrada al sistema
- Diseño de ciclo de menú con opciones interactivas:
  - Agregar vehículo de carga
  - Agregar vehículo de pasajeros
  - Listar todos los vehículos
  - Filtrar vehículos por tipo
  - Generar boleta de arriendo
  - Ver cantidad de vehículos en arriendo
  - Salir del sistema
- Incorporación del método `contarVehiculosEnArriendo()` en `GestorVehiculos`
- Implementación del método `obtenerVehiculosEnArriendo()` para recuperar detalles completos desde las patentes
- Mejora de encapsulación y preparación para filtros por duración en sesiones futuras

### 14/07/2025 - Implementación clase Vehículo y subclases
- Definición de clase abstracta `Vehiculo` con encapsulamiento completo.
    - Actualización de `VehiculoCarga`, atributo **capacidadToneladas** con validación semántica de tipo de vehículo por categoría.
    - Actualización de `VehiculoPasajeros`, atributo **capacidadMaxima** con validación según categoría PASAJEROS.
- Creación del enum `TipoVehiculo` en paquete dedicado.
- Definición de GeneradorBoleta con constantes IVA, DESCUENTO_CARGA y DESCUENTO_PASAJEROS. Método generarBoleta(Vehiculo, dias).

### 13/07/2025 - Carga inicial proyecto semana 9
- Creación de la estructura base del proyecto con los siguientes paquetes:
    - `model`: Representan las entidades del dominio.
    - `interfaz`: Definiciones para comportamientos comunes o específicos
    - `service`: Gestión de vehículos, validación, filtros, y lógica adicional
    - `utils`: Manejo de archivos, formateo, helpers generales.
    - `concurrence`: Clases encargadas del procesamiento paralelo y seguro.
    - `ui`: Entrada/salida desde consola o GUI.
