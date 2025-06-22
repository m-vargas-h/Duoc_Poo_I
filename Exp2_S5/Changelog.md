# Historial de cambios

## 22/06/2025 - Creación sistema para administrador
- Se implemento el sub paquete `libro` perteneciente al paquete `ui`, en el se gestionan los libros de acuerdo al rol del funcionario:
    - **AgregarLibroMenu.java**: accesible para admin y asistente, permite agregar nuevos libros a la biblioteca.
    - **EliminarLibroMenu.java**: accesible solo para admin, permite eliminar libros de la base de datos siempre y cuando no hayan copias en préstamo activo.
    - **ModificarCantidadLibroMenu.java**: permite a los asistentes modificar el stock de un libro ya existente, siempre y cuando no hayan copias en préstamo para evitar descuadres de stock.
    - **ModificarLibroMen.java**: permite al admin modificar todos los campos de un libro, siempre y cuando no haya prestamos activos para evitar posibles errores por nombres que coincidan o cantidades inexistentes.
- Se implementaron las clases `MenuAdmin` y `MenuAsistente` las cuales dan acceso a los menus y opciones correspondientes para cada rol.

### 22/06/2025 - Creación clase Admin
- Se creo la nueva clase `Admin` para definir a los usuarios que podrán tener control sobre el sistema, de momento son 2 tipos de usuarios:
    - Admin: tendrá control total, pudiendo agregar, modificar y eliminar libros, también podrá crear nuevas credenciales para ayudantes y bloquear usuarios (alumnos).
    - Ayudante: su control es parcial, no puede eliminar libros existentes, pero si agregar nuevos y modificar las existencias de los libros, ademas podrá bloquear usuarios en el sistema.
- Se modifico la clase `PersistenciaInfo` para poder leer y escribir el archivo con los admin existentes.

### 22/06/2025 - Carga inicial proyecto semana 5
- Carga de la estructura inicial del proyecto en base al proyecto anterior.