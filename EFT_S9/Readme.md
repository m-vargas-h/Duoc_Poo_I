# Semana 9: Evaluación Final Transversal

# DriveQuest Rentals 

Proyecto desarrollado en Java para la asignatura **POO I**, centrado en gestión de vehículos, generación de boletas y validaciones específicas. Se aplica programación orientada a objetos con énfasis en modularidad, experiencia de usuario y mantenibilidad.

---

## 🎯 Descripción general

El sistema permite registrar vehículos por tipo, calcular boletas de arriendo con desglose visual y aplicar validaciones específicas como formato de RUT y duplicidad de patentes. Se diseñó priorizando claridad en consola, trazabilidad en registros y separación lógica de responsabilidades.

---

## ⚙️ Características principales

- **Registro inteligente de vehículos** con validación de patente única
- **Generación de boletas claras**, alineadas y segmentadas para mayor legibilidad
- **Filtrado contextual** por tipo de vehículo y días de arriendo
- **Validación de RUT chileno** mediante expresión regular y verificación de dígito
- **Separación modular** de captura, validación y presentación

---

## 🔧 Arquitectura del sistema

| Módulo                 | Descripción                                                                 |
|------------------------|-----------------------------------------------------------------------------|
| `Main`                 | Punto de entrada, orquesta flujos y muestra menús                          |
| `Vehiculo`             | Clase base con atributos comunes                                           |
| `VehiculoCarga` / `VehiculoPasajero` | Clases hijas con lógica específica según tipo de arriendo             |
| `GestorVehiculos`      | Administra colección, búsqueda y filtrado de vehículos                     |
| `Boleta`               | Genera detalle de arriendo con IVA, descuentos y totales                   |
| `ValidadorRut`         | Valida formato y dígito verificador del RUT ingresado por el cliente       |

---

## 🧩 Métodos destacados

| Método                         | Rol principal                                                                 |
|-------------------------------|-------------------------------------------------------------------------------|
| `mostrarMenu()`               | Despliega las opciones disponibles en consola y coordina la navegación. |
| `ingresarVehiculo()`          | Captura datos del vehículo desde consola, valida formato de patente y evita duplicados. |
| `filtrarVehiculosPorTipo()`   | Muestra vehículos por tipo específico (`Carga`, `Pasajero`), mejorando exploración. |
| `generarBoleta()`             | Calcula y presenta el desglose monetario, aplicando IVA y descuentos según el tipo y días. |
| `validarRUT()`                | Comprueba el formato del RUT chileno con regex y cálculo de dígito verificador, evitando entradas inválidas. |

---

## 🧠 Flujo de uso

1. **Ingreso de vehículo** → usuario selecciona tipo y proporciona datos
2. **Validación automática** → verificación de patente única y RUT válido
3. **Generación de boleta** → sistema calcula totales y muestra desglose en consola
4. **Filtrado de vehículos** → según días de arriendo o tipo específico

---

## 🛠️ Requisitos

- Java 8+
- IDE como NetBeans o Eclipse (opcional)
- Consola con soporte para UTF-8 (recomendado para visualización clara)

---

## ⚠️ Nota sobre datos ficticios

Este proyecto fue desarrollado con fines exclusivamente académicos.  
Las **patentes**, **RUTs**, y **valores monetarios** utilizados en las boletas son **totalmente ficticios** y no representan situaciones reales ni datos personales existentes.

Se recomienda no utilizar este sistema en contextos productivos sin las debidas adaptaciones y validaciones profesionales.

---