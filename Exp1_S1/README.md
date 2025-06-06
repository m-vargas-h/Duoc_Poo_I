# Semana 1: Iniciando mi proyecto en Java

Este proyecto es una implementación sencilla de un sistema bancario desarrollado en Java. El sistema permite registrar clientes, gestionar cuentas (con depósito, giro y consulta de saldo) y utilizar archivos CSV para la persistencia de datos (clientes y saldos) entre ejecuciones.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Funcionalidades](#funcionalidades)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos](#requisitos)

## Descripción

El proyecto **Bank Boston** es una aplicación de consola orientada a objetos que simula las operaciones básicas de un banco. Se utiliza un sistema de menú para interactuar con el usuario, permitiendo:
- Registrar clientes, generando automáticamente una cuenta bancaria con números secuenciales.
- Consultar los datos del cliente y el estado de su cuenta.
- Realizar transacciones de depósito y giro en las cuentas.
- Persistir la información en archivos CSV:
  - **clientes.csv**: almacena los datos de los clientes (RUT, nombres, apellidos, domicilio, comuna, teléfono y número de cuenta).
  - **saldos.csv**: almacena el saldo actualizado de cada cuenta.

## Funcionalidades

- **Registro de Cliente:**  
  Se solicitan datos personales y se valida el formato de entrada (por ejemplo, que el RUT tenga la cantidad correcta de caracteres y que el teléfono contenga 8 dígitos).  
  Se genera automáticamente un número de cuenta siguiendo un patrón predefinido.

- **Consulta de Datos del Cliente:**  
  Permite buscar y mostrar la información de un cliente a partir de su RUT.

- **Transacciones Bancarias:**  
  Se pueden realizar depósitos y giros en la cuenta del cliente. Cada transacción actualiza el saldo y se guarda en el archivo `saldos.csv`.

- **Persistencia de Datos:**  
  - Los clientes se guardan en `clientes.csv` justo después de su registro.
  - Los saldos se actualizan en `saldos.csv` tras cada depósito o giro.
  - Al iniciar el sistema, se carga la información de clientes y se actualizan los saldos correspondientes.

## Estructura del Proyecto

El proyecto está organizado en el siguiente paquete y clases:

- **com.duoc.exp1_s1_miguel_vargas_g16.Exp1_S1_Miguel_Vargas_G16**  
  La clase principal que contiene el método `main()`. Administra la interfaz de usuario y las opciones del menú.

- **com.duoc.exp1_s1_miguel_vargas_g11.Banco**  
  Administra la lista de clientes y es responsable de acciones como:  
  - Registrar un cliente (llamando a `Cliente.registrarCliente()`).  
  - Buscar clientes por RUT.  
  - Guardar y cargar clientes desde `clientes.csv`.  
  - Guardar y cargar saldos desde `saldos.csv`.

- **com.duoc.exp1_s1_miguel_vargas_g11.Cliente**  
  Representa la entidad cliente, contiene todos los atributos personales (RUT, nombre, apellidos, domicilio, comuna, teléfono) y posee una instancia de `Cuenta`.

- **com.duoc.exp1_s1_miguel_vargas_g11.Cuenta**  
  Define la cuenta bancaria, generando un número de cuenta único y gestionando el saldo mediante métodos para depositar, girar y mostrar la cuenta.

## Requisitos

- **Java JDK:** Versión 8 o superior.
- **Maven:** Para compilar y ejecutar el proyecto.
- **NetBeans / IDE:** Opcionalmente puedes usar NetBeans, Eclipse u otro IDE compatible.