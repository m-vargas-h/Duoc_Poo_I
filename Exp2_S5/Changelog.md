# Historial de cambios

## 22/06/2025 - Creación clase Admin
- Se creo la nueva clase `Admin` para definir a los usuarios que podrán tener control sobre el sistema, de momento son 2 tipos de usuarios:
    - Admin: tendrá control total, pudiendo agregar, modificar y eliminar libros, también podrá crear nuevas credenciales para ayudantes y bloquear usuarios (alumnos).
    - Ayudante: su control es parcial, no puede eliminar libros existentes, pero si agregar nuevos y modificar las existencias de los libros, ademas podrá bloquear usuarios en el sistema.
- Se modifico la clase `PersistenciaInfo` para poder leer y escribir el archivo con los admin existentes.

### 22/06/2025 - Carga inicial proyecto semana 5
- Carga de la estructura inicial del proyecto en base al proyecto anterior.