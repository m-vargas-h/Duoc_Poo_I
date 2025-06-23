# Semana 5: Colecciones de objetos

# Sistema de Gestión de Biblioteca

Este proyecto es una **implementación orientada a objetos en Java** diseñada para administrar **usuarios, préstamos de libros y consultas de estado** dentro de una biblioteca. Se enfoca en la **persistencia de datos**, validaciones de usuario y una **interfaz de consola intuitiva**.

---

## Características Principales

### 1. Gestión de Usuarios  
- **Validación de RUT:** Se asegura que el formato sea correcto antes de registrar al usuario.  
- Registro de datos relevantes: ID, nombre, carrera y sede.  
- Consulta del estado de un usuario, mostrando sus datos y los libros en préstamo.  
- **Gestión de Asistentes:**  
  - El administrador puede añadir nuevos asistentes, pero **solo con rol de asistente** (no puede crear nuevos administradores).  
  - También se permite la eliminación de asistentes, siempre asegurando que se mantenga la integridad de los registros en el archivo de credenciales.

### 2. Gestión de Préstamos  
- **Registro de préstamos:** Se asocian usuarios con libros mediante identificadores únicos.  
- Consulta con **información simplificada,** mostrando solo el título del libro en lugar de toda su metadata.  
- **Devolución optimizada:** El usuario selecciona el libro a devolver desde una lista, evitando errores tipográficos.

### 3. Persistencia y Sincronización  
- Carga y almacenamiento de datos desde archivos, de forma que tanto los préstamos como los usuarios sean restaurados correctamente.  
- Sincronización entre memoria y disco para evitar inconsistencias en los registros.  
- Validación de rutas de archivo para garantizar que la persistencia funcione en distintos entornos.  
- **Administración de Credenciales:**  
  - Los datos de los administradores y asistentes se almacenan en un archivo `admin.csv` con la siguiente cabecera:  
    ```
    rut,nombre,password,rol
    ```  
  - Se usan métodos específicos para agregar mediante “append” y reescribir el archivo completo en caso de eliminación.

### 4. Interfaz de Consola  
- Menús organizados que permiten consultar el estado del usuario, registrar y devolver préstamos, y gestionar usuarios de forma intuitiva.  
- **Opciones predefinidas** para carreras y sedes, facilitando la selección sin necesidad de ingresar datos manualmente.  
- **Menús Administrativos:** Se han incorporado nuevos menús para que el administrador pueda agregar y eliminar asistentes.

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
- **Usuario.java:** Almacena los datos personales y la lista de libros en préstamo.  
- **Libro.java:** Representa los libros disponibles en la biblioteca.  
- **Admin.java (o implementación de credenciales):** Representa a los administradores y asistentes, diferenciados a través del atributo "rol".

### `persistencia`  
Administra la carga y almacenamiento de datos:  
- **PersistenciaInfo.java:**  
  - Gestiona la lectura y escritura de archivos, asegurando la persistencia correcta.
  - Incorpora métodos para cargar y guardar usuarios, así como la gestión de credenciales en `admin.csv`.

### `servicio`  
Contiene la lógica de negocio y validaciones de datos:  
- **Biblioteca.java:** Administra la gestión de préstamos y libros en la biblioteca.  
- **ServiciosBiblioteca.java:** Maneja usuarios, préstamos, devoluciones y ahora la creación/eliminación de asistentes, con control de persistencia.

### `ui`  
Define la interfaz de usuario en consola:  
- **ConsultarEstadoUsuarioMenu.java:** Muestra la información del usuario y sus libros en préstamo con una estructura optimizada.  
- **ConsultarLibroMenu.java:** Permite buscar información sobre libros dentro del sistema.  
- **DevolverLibroMenu.java:** Lista los libros en préstamo y permite devolver uno mediante selección numérica.  
- **MenuPrincipal.java:** Orquesta la navegación entre las diferentes opciones del sistema.  
- **RegistrarUsuarioMenu.java:** Facilita el registro de nuevos usuarios con validaciones de datos.  
- **SolicitarLibroMenu.java:** Gestiona el proceso de solicitud de libros por parte de los usuarios.  
- **AgregarAsistenteMenu.java y EliminarAsistenteMenu.java:** Nuevos menús para que los administradores puedan añadir o eliminar asistentes (usuarios con rol asistente).

---

## Mejoras Futuras  
- **Validación de préstamos:** Evitar duplicaciones y gestionar fechas de vencimiento.  
- **Reportes y estadísticas:** Información detallada sobre préstamos y actividad de usuarios.  
- **Migración a Base de Datos:** Para una gestión más eficiente de la información.  
- **Interfaz gráfica:** Una UI más intuitiva y amigable para mejorar la experiencia de usuario.  
- **Validaciones adicionales en credenciales:** Mejorar el formato de RUT y la fuerza de las contraseñas.
---

## Requisitos  
- **Java JDK 8 o superior**  
- **IDE compatible** (NetBeans, Eclipse, IntelliJ)  

---