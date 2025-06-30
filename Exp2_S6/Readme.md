# Semana 6: Entradas y salidas (I/O) y manejo de archivos.

# 🦸‍♂️ Comic Collector System

Sistema orientado a objetos en Java para la **gestión de productos coleccionables y procesos de compra**, utilizando un enfoque modular, seguro y escalable. Incluye una **interfaz de consola interactiva**, persistencia de datos en archivos y separación de responsabilidades por roles (administrador / usuario).

---

## 🧩 Características Principales

### 1. Gestión de Productos
- Manejo de productos mediante subclases específicas:
  - `Comic`, `NovelaGráfica`, `Coleccionable`, `JuegoMesa`, `TCG`
- Visualización estructurada en tabla: ID, título, volumen (si aplica), estado, stock y precio.
- Filtrado dinámico por categoría desde interfaz de usuario.
- Validación de stock y estado de productos antes de la compra.

### 2. Carrito de Compras Interactivo
- El usuario puede explorar categorías y **agregar múltiples productos** a un carrito.
- Validación automática de stock antes de aceptar cada línea.
- Opción para revisar el resumen completo de compra antes de confirmar.
- Proceso de pago simulado (modo débito por defecto).

### 3. Roles y Accesos
- **Administrador**:
  - Autenticación por credenciales.
  - Alta de productos.
  - Cambio de estado (`DISPONIBLE`, `NO_DISPONIBLE`, `PRE_VENTA`).
  - Acceso a resumen de ventas por producto.
- **Usuario**:
  - Navegación por categorías.
  - Compra mediante carrito.
  - Validación de RUT antes de operar.

### 4. Persistencia Modular
- Almacenamiento persistente de productos, usuarios y ventas en archivos CSV.
- Generación de ID automática y sin colisiones.
- Separación entre lógica de negocio (`servicio`) y persistencia (`persistencia`).

---

## 📂 Estructura del Proyecto

### `modelo/`
Entidades del sistema:
- `base/` → `Producto`, `Usuario`
- `productos/` → subclases específicas por categoría
- `ventas/` → `Venta`, `ResumenVenta`
- `reserva/` → clases reservadas para futuras implementaciones
- `enums/` → `EstadoProducto`, etc.

### `servicio/`
Lógica de negocio:
- `ComicCollectorService.java`

### `persistencia/`
Lógica de almacenamiento:
- Lectura/escritura modular en CSV para diferentes entidades.

### `ui/`
Interfaz de consola para ambos roles:
- `MenuPrincipal`, `MenuAdministrador`, `MenuUsuario`

---

## 🎮 Flujo de Usuario
- Ingreso como usuario o administrador
- Navegación por categorías (por tipo de producto)
- Visualización detallada (tabla con stock, estado, volumen…)
- Carrito de compras interactivo
- Validación → Confirmación → Simulación de pag

---

## 🚀 Mejoras Futuras

- Búsqueda por nombre parcial o palabra clave.
- Filtrado por estado (ej. solo productos en stock).
- Soporte para múltiples medios de pago.
- Exportación de ventas y reportes estadísticos.

---

## ⚙️ Requisitos Técnicos

- **Java JDK 17 o superior**
- IDE compatible (IntelliJ, Eclipse, VSCode)
- Acceso a sistema de archivos para lectura/escritura local

---
