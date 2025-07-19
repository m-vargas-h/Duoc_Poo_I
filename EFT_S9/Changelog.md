# Historial de cambios

## 19/07/2025 - Refactorizacion Clase MenuPrincipal 
- Eliminada duplicidad entre `agregarVehiculoCarga()` y `agregarVehiculoPasajeros()`
- Extraída lógica compartida en un único método `solicitarDatosBaseVehiculo()`:
  - Validación de patente
  - Captura de marca, año, precio base y tipo
- Simplificada implementación sin clases auxiliares
- Garantizada compatibilidad con lógica de persistencia CSV
- Agregado método `filtrarPorTipo(TipoVehiculo)` dentro de **GestorVehiculos**
- MenuPrincipal ahora delega la lógica de filtrado directamente al gestor
- Modularización del flujo `generarBoleta()`, se han separado pasos internos en métodos auxiliares:
  - capturarDatosCliente()
  - validarVehiculoParaArriendo(...)
  - crearYGenerarBoleta(...)
  - registrarArriendo(...)
- Mejora visual y semántica de la boleta en `BoletaSimple`:
  - Información del vehículo dividida por línea
  - Capacidad con unidad abreviada (t o p)
  - Valores monetarios alineados en columnas usando printf(...)
  - Aplicado formato de moneda local (NumberFormat con Locale("es", "CL"))
  - Restauradas líneas de debug originales:

---

## 18/07/2025 - Refactorizacion y sincronización sistema
- Concurrencia y sincronización
  - Implementación de clase `CargaVehiculosConcurrente` para carga paralela de vehículos por tipo (Runnable)
  - Integración de Thread y .join() en main() para ejecutar la carga al iniciar el sistema
  - Uso de Collections.synchronizedMap para acceso seguro al mapa de vehículos
  - División de método `cargarVehiculosDesdeArchivos()` en GestorVehiculos:
    - `cargarVehiculosCarga()`
    - `cargarVehiculosPasajeros()`
  - Constructor de MenuPrincipal modificado para recibir el GestorVehiculos precargado
- Refactorización de generarBoleta():
  - Uso de excepciones propias: **VehiculoNoDisponibleException**, **DiasArriendoInvalidosException**
  - Separación de la validación en `obtenerDiasDeArriendo()`
- Refactorización de registrarArriendo(...):
  - Creación de **ArriendoPersistenciaException**
  - Lanzamiento de error si falla el guardado
- Refactorización de cargarHistorial():
  - Creación de **HistorialLecturaException**
  - Ignora líneas mal formateadas con trazas
  - Informa con claridad si hay fallos de lectura
- Reorganización del main() para que todo el sistema inicie con carga concurrente

### 16/07/2025 - Mejoras del sistema de boletas y registro
- Implementación de `ValidadorFormato.validarPatente()` para admitir formatos AB-1234 y ABCD-12.
- Centralización de la validación en clase auxiliar.
- Actualización de métodos `agregarVehiculoCarga()` y `agregarVehiculoPasajeros()` para utilizar esta validación.
- Estandarización de claves con `toUpperCase().trim()` al guardar y buscar patentes.
- Ajuste de formato decimal en `guardarEnArchivo(...)` usando `Locale.US`.
- Inclusión de columna `FechaEmisión` en archivo.

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
