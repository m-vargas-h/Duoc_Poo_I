# Historial de cambios

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
