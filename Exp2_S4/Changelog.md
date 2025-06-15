# Historial de cambios

## 15/06/2025 - Corrección de persistencia y mejoras en UI
- Se corrige error que descontaba los libros cada vez que se iniciaba la aplicación duplicando el préstamo. Ahora el sistema no actualiza al cargar los prestamos por lo que no duplica información, las existencias se escriben al momento de realizar un préstamo/devolución.
- Se paso de usar un ID genérico para los usuarios a utilizar el rut, de esta forma no habrá id duplicados y es mas fácil asociar cualquier préstamo/devolución al usuario correspondiente. Ademas el rut es validado para asegurar que cumpla con el formato establecido.
- Ahora la carrera y sede son elegidas de una lista de opciones, eliminando posibles errores al dejar que el usuario ingrese texto de forma libre.
- A partir de ahora es posible listar el compendio completo de libros disponibles con sus stock respectivos.
- Se implemento una nueva opción de menu que permite ver la información del usuario, asi como la lista de libros que tiene en prestamos vigente.


### 14/06/2025 - Mejoras en persistencia de datos
- Se mejoro la persistencia de datos:
    - El registro `usuarios.csv` es leído/escrito de manera correcta.
    - El archivo  `libros.csv` ahora se crea en blanco en caso de no existir (evita caídas del sistema por falta de una base de datos con libros).
    - Ahora el stock de libros se actualiza de manera correcta.
    - Se creo el archivo `prestamos.csv` que guarda la información del usuario y libros pedidos.
- Se mejoro la lógica del sistema:
    - Ahora se aplica un limite de 3 libros pedidos por usuario.\
    - el limite y stock son verificados antes de registrar cualquier operación.
- Los métodos `solicitarLibro()` y `devolverLibro()` ahora gestionan de forma correcta la escritura de los archivos que necesitan para su funcionamiento.
- Inclusion de **Debug logs** para verificar las rutas de guardado y carga de archivos.

### 13/06/2025 - Puesta en marcha proyecto
- Se creo una capa de UI con los siguientes menus:
    - Menu Principal    
    - Registro de usuario   
    - Solicitud de libro   
    - Devolución de libro  
    - Consulta de libro   
- Se crearon una serie de excepciones personalizadas según los requerimientos del proyecto.
- La lógica de operación se separo en una clase independiente `ServiciosBiblioteca`.
- Se comenzó el trabajo en `PersistenciaInfo`, de momento el programa puede leer la lista de libros disponibles, sin embargo aun no es capaz de guardar información. 

### 10/06/2025 - Carga inicial proyecto semana 4
- Carga de la estructura inicial del proyecto, de momento se cuenta con los siguientes paquetes:
- `modelo`: contiene las clases `Libro` y `Usuario`
- `persistencia`: contiene la clase `PersistenciaInfo` y se encarga de la escritura y lectura de los archivos necesarios para la ejecución del programa.
- `utilidades`: contiene la clase `Auxiliares` la cual brinda métodos que ayudan con el formateo y presentación de información.