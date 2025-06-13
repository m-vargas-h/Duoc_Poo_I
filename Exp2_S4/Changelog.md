# Historial de cambios

## 13/06/2025 - Puesta en marcha proyecto
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