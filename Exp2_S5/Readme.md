# Semana 4: Excepciones try/catch

# Sistema de Gestión de Biblioteca

Este proyecto es una **implementación orientada a objetos en Java** diseñada para administrar **usuarios, préstamos de libros y consultas de estado** dentro de una biblioteca. Se enfoca en la **persistencia de datos**, validaciones de usuario y una **interfaz de consola intuitiva**.

---

## Características Principales

### 1. Gestión de Usuarios  
- Validación de **RUT**, asegurando el formato correcto antes de registrar al usuario.  
- Almacena información relevante: ID, nombre, carrera y sede.  
- Permite consultar el estado de un usuario, mostrando sus datos y libros en préstamo.  

### 2. Gestión de Préstamos  
- **Registro de préstamos**, asociando usuarios con libros mediante identificadores únicos.  
- Consulta de préstamos con **información simplificada**, mostrando solo el título del libro en lugar de toda su metadata.  
- **Devolución optimizada**, permitiendo al usuario elegir un libro desde su lista de préstamos en vez de escribir el título manualmente.  

### 3. Persistencia y Sincronización  
- Carga y almacenamiento de datos desde archivos, asegurando que los préstamos y usuarios sean restaurados correctamente.  
- Sincronización entre memoria y disco para evitar inconsistencias en los registros.  
- Validación de rutas de archivo para garantizar que la persistencia funcione en distintos entornos.  

### 4. Interfaz de Consola  
- Menús organizados que permiten consultar el estado del usuario, registrar préstamos y devolver libros de manera intuitiva.  
- **Opciones predefinidas** para carreras y sedes, facilitando la selección sin necesidad de ingresar datos manualmente.  

---

## Estructura del Proyecto

### `excepcion`  
Define excepciones específicas para el sistema:  
- **CopiaInvalidaException.java**  
- **LibroNoDisponibleException.java**  
- **LibroNoEncontradoException.java**  
- **LibroNoPrestadoException.java**  
- **MaximoPrestamoException.java**  
- **UsuarioNoEncontradoException.java**  
- **UsuarioYaExisteException.java**  

### `modelo`  
Define las entidades clave del sistema:  
- **Usuario.java**: Almacena los datos personales y la lista de libros en préstamo.  
- **Libro.java**: Representa los libros disponibles en la biblioteca.  

### `persistencia`  
Administra la carga y almacenamiento de datos:  
- **PersistenciaInfo.java**: Gestiona la lectura y escritura de archivos, asegurando la persistencia correcta.  

### `servicio`  
Contiene la lógica de negocio y validaciones de datos:  
- **Biblioteca.java**: Administra la gestión de préstamos y libros en la biblioteca.  
- **ServiciosBiblioteca.java**: Maneja usuarios, préstamos y devoluciones con control de persistencia.  

### `ui`  
Define la interfaz de usuario en consola:  
- **ConsultarEstadoUsuarioMenu.java**: Muestra la información del usuario y sus libros en préstamo con una estructura optimizada.  
- **ConsultarLibroMenu.java**: Permite buscar información sobre libros dentro del sistema.  
- **DevolverLibroMenu.java**: Lista los libros en préstamo y permite devolver uno mediante selección numérica.  
- **MenuPrincipal.java**: Orquesta la navegación entre las diferentes opciones del sistema.  
- **RegistrarUsuarioMenu.java**: Facilita el registro de nuevos usuarios con validaciones de datos.  
- **SolicitarLibroMenu.java**: Gestiona el proceso de solicitud de libros por parte de los usuarios.  

---

## Mejoras Futuras  
- **Validación de préstamos** para evitar duplicaciones y gestionar fechas de vencimiento.  
- **Reportes y estadísticas** sobre préstamos y actividad de usuarios.  
- **Migración a Base de Datos** para una gestión más eficiente de la información.  
- **Interfaz gráfica** que permita mejorar la experiencia de usuario mediante una UI más intuitiva.  

---

## Requisitos  
- **Java JDK 8 o superior**  
- **IDE compatible** (NetBeans, Eclipse, IntelliJ)  

---