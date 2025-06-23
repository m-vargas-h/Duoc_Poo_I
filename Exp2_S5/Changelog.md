# Historial de cambios

## 23/06/2025 - Actualización fichero Readme
- Se actualiza el fichero `Readme.md` para reflejar las nuevas funciones del sistema
- Se realizan correcciones menores a textos para mejorar compresión de instrucciones.

### 22/06/2025 - Creación nuevos asistentes
- Se habilitaron las opciones para que el admin pueda gestionar asistentes en el sistema, actualmente las opciones son:
    - 'AgregarAsistenteMenu': permite la creación de nuevos usuarios con el rol asistente, el sistema verifica que el usuario no este registrado previamente en base al rut.
    - 'EliminarAsistenteMenu': permite eliminar usuarios con el rol asistente mediante una lista con todos los asistentes que estén registrados.
- Se modifico la clase `PersistenciaInfo` para soportar la nueva escritura y lectura sobre el archivo **admin.csv**

### 22/06/2025 - Creación sistema para administrador
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