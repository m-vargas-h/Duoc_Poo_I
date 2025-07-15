# Historial de cambios

## 15/07/2025 - Implementación menu principal
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

## 14/07/2025 - Implementación clase Vehículo y subclases
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
