# Historial de cambios

## 09/06/2025 - Actualización fichero Readme
- Se actualiza el fichero para representar la nueva estructura del proyecto.

### 08/06/2025 - Correcciones menores 
- Se separó la la dirección en calle y numero para mejorar la uniformidad al almacenar la información. Ademas toda la información guardada de tipo texto ahora se almacena en mayúsculas, esto para implementar funciones de búsqueda de forma mas efectiva en posibles actualizaciones.
- En la carpeta `Data` se proporciona una pequeña base de usuarios y transacciones ya listas para poder hacer pruebas.

### 07/06/2025 - Integración de interfaces y ampliación de funciones
- Se implementaron las siguientes interfaces:
    - `Interés`: Declara el método `CalcularInteres` para que puedan ser sobrescrito por las subclases que lo necesiten.
    - `Operaciones`: Declara los métodos `Girar` y `Depositar` para que las subclases puedan utilizarlos.
- Ahora los depósitos simulan la generación de interés (solo para efectos de calculo, aun no se integran como parte del saldo).
- Ahora los sobregiro generan un interés en base al monto utilizado el cual es cargado como saldo negativo en la cuenta corriente. 

### 07/06/2025 - Mejoras en la persistencia e información  
- Se mejoro la lógica de la clase `PersistenciaInfo` generando una carpeta Data dentro del proyecto para almacenar las bases de datos, si la carpeta no existiese el sistema la creara de forma automática.
- Se mejoro la forma en que se muestra la información en el menu ***2. Ver datos de cliente***, antes se mostraba el código correspondiente al tipo de cuenta, ahora se mostrara el nombre.

### 06/06/2025 - Carga inicial proyecto semana 3 
- Creación del proyecto inicial con los siguientes paquetes:
    - `modelo`: contiene la clase cliente, asi como la clase abstracta cuenta con sus subcuentas.
    - `persistencia`: almacena las clases encargadas de la persistencia de archivos, generando asi una base de datos de clientes, asi como los saldos de las cuentas.
    - `servicios`: aquí están las clases que gestionan la lógica del sistema.
    - `utilidades`: Almacena métodos auxiliares que ayudan a formatear la información para mantener la uniformidad.