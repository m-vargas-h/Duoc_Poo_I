# Semana 3: Paquetes y directorios para el uso de clases

Este proyecto es una **implementación orientada a objetos de un sistema bancario** desarrollado en **Java**, que permite **registrar clientes, gestionar cuentas y realizar transacciones bancarias** con persistencia de datos en **archivos CSV**. Se ha aplicado una arquitectura modular basada en **herencia y polimorfismo**, optimizando la mantenibilidad y escalabilidad del sistema.

---

## Características Principales

- **Registro de Clientes**:  
  - Valida el RUT y evita la creación de múltiples cuentas para un mismo cliente, garantizando que cada usuario tenga única una cuenta bancaria.  
  - Se elimina la posibilidad de crear un cliente sin cuenta; todos deben estar asociados a una cuenta válida.
  - Permite seleccionar el tipo de cuenta a asignar (ya sea `CuentaDigital`, `CuentaAhorro` o `CuentaCorriente`).
  - Al almacenar los datos, se guarda además el campo `tipoCuenta` para asegurar que, al restaurar la información, se recree la instancia correcta de la cuenta.

- **Gestión de Cuentas Bancarias**:  
  Las cuentas están estructuradas mediante herencia, utilizando una clase abstracta `CuentaBancaria` con subclases especializadas:
  - `CuentaDigital`
  - `CuentaAhorro`
  - `CuentaCorriente`
- Cada tipo de cuenta tiene reglas específicas como por ejemplo limite de giros o intereses.

- **Transacciones Bancarias**:  
  - Permite operaciones de depósito y giro, actualizando el saldo en tiempo real.
  - Calcula los intereses generados por cada deposito en la cuenta de ahorro, asi como el interés a pagar por utilizar la linea de sobregiro de la cuenta corriente.
  - Se ha corregido la sincronización del listado interno de clientes; al registrar un nuevo cliente, éste se añade inmediatamente a la lista, de modo que las operaciones (depósitos, giros) pueden realizarse sin necesidad de reiniciar el sistema.

- **Numeración Secuencial de Cuentas**:  
  - El número de cuenta se genera de forma secuencial con el formato `25XXYYYYY`, donde:
    - `25` es el código de sucursal.
    - `XX` es el código del tipo de cuenta (por ejemplo, "01" para CuentaDigital, "02" para CuentaAhorro, "03" para CuentaCorriente).
    - `YYYYY` son dígitos secuenciales.
- Al iniciar el sistema, se carga el último número asignado desde el CSV para continuar la secuencia sin interrupciones.

- **Persistencia en Archivos CSV**:  
  - **clientes.csv**: Almacena los datos personales de cada cliente y su número de cuenta.  
  - **saldos.csv**: Guarda y restaura el saldo de cada cuenta en cada ejecución del sistema.
-El proceso de carga y guardado se ha optimizado para que la lista interna de clientes se sincronice de forma inmediata, evitando reinicios para operar con cuentas recién registradas.

- **Interfaz de Consola**:  
  Un menú interactivo permite la ejecución de las operaciones bancarias desde la consola.

---

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes y clases:

### `modelo`
Define las entidades clave del sistema:
- **Cliente.java**: Representa a un cliente bancario, con sus datos personales y una cuenta asociada.
- **CuentaBancaria.java**: Clase abstracta que define el comportamiento común de las cuentas.
- **CuentaDigital.java**, **CuentaAhorro.java**, **CuentaCorriente.java**: Subclases que implementan tipos específicos de cuentas.

### `servicios`
Contiene la lógica de negocio:
- **Banco.java**: Administra la lista de clientes y orquesta las operaciones bancarias.
- **ServiciosCliente.java**: Gestiona el proceso de registro de clientes e interacción con el usuario.

### `persistencia`
Maneja la carga y almacenamiento de datos:
- **PersistenciaInfo.java**: Administra la lectura y escritura en los archivos CSV.

### `utilidades`
Clases auxiliares:
- **Auxiliares.java**: Provee funciones de utilidad, como el formateo del RUT y validaciones de datos.

---

## Funcionalidades Detalladas

### 1. Registro de Cliente
- Solicita los datos personales (RUT, nombre, apellidos, domicilio, comuna y teléfono).
- Valida que el formato de RUT y teléfono sean correctos.
- Evita duplicados, asegurando que un cliente (identificado por su RUT) pueda tener solo una cuenta.
- Permite seleccionar el tipo de cuenta (`CuentaDigital`, `CuentaAhorro`, `CuentaCorriente`).

## 2. Numeración Secuencial de Cuentas
- El número de cuenta se forma combinando el código de sucursal, el código del tipo (extraído de la cuenta) y un contador secuencial de 5 dígitos.
- Al reiniciar el sistema, se lee el CSV para continuar la secuencia sin reinicios o duplicaciones.

### 3. Gestión de Transacciones
- Las operaciones de depósito y giro actualizan el saldo en tiempo real.
- Se calculan intereses para cuentas de ahorro y corriente en tiempo real.
- La lista interna de clientes se actualiza de forma inmediata tras el registro, permitiendo realizar transacciones sin necesidad de reiniciar la aplicación.
- Se implementan restricciones particulares por cuenta, como el límite de giros en `CuentaAhorro`.

### 4. Persistencia de Datos
- **clientes.csv**: Almacena los datos del cliente junto con el número de cuenta y el campo adicional `tipoCuenta`, lo que facilita la restauración a la subclase correcta.
- **saldos.csv**: Guarda el saldo actualizado de cada cuenta para su recuperación en futuras ejecuciones.
- El proceso de carga y guardado se ha ajustado para sincronizar el listado interno de clientes inmediatamente, permitiendo operaciones en tiempo real.

---

## Requisitos

- **Java JDK 8 o superior.**
- **Maven** (opcional para manejo de dependencias y compilación).
- **IDE Compatible**: Opcionalmente puedes utilizar NetBeans, Eclipse, IntelliJ u otro IDE de tu preferencia.

---

## Mejoras Futuras

- **Migración a Base de Datos**: Mejorar la persistencia y manejo de grandes volúmenes de datos.
- **Autenticación y Seguridad**: Implementar mecanismos de autenticación para que los clientes accedan a sus cuentas de forma segura.
- **Nuevas Funcionalidades**:  
  - Ampliar las restricciones específicas de cada tipo de cuenta (por ejemplo, mayores controles en `CuentaAhorro` o líneas de sobregiro en `CuentaCorriente`).
  - Desarrollar reportes y estadísticas sobre las transacciones.
  - Crear un método que ademas de calcular el interés generado al deposito, lo sume a la cuenta al pasar determinado tiempo.
- **Interfaz Gráfica**: Desarrollar una interfaz visual para mejorar la experiencia del usuario.
---