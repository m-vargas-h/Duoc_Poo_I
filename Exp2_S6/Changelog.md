# Historial de cambios

## 28/06/2025 - Diseño menu navegación
Se diseñaron 3 menus para poder interactuar con el sistema:
- `MenuPrincipal`: En este menu se define si quien esta ingresando es un administrador o un usuario.
    - `MenuAdministrador`: Contiene las opciones disponibles para administradores, como lo es agregar productos, modificar su stock o cambiar su disponibilidad entre otros.
    - `MenuUsuario`: contiene las opciones de registro, compra y búsqueda de productos para usuarios nuevos y ya registrados.

## 28/06/2025 - Carga inicial proyecto semana 6
Se crea la primera base del proyecto **ComicCollectorSystem** con las siguientes clases:
- `Producto`: Clase abstracta principal que define los atributos base de un producto
    - `Comic`: incorpora los campos editorial y volumen (campo opcional con constructor para cada caso)
    - `NovelaGrafica`: incluye autor y fechaPublicacion. 
    - `Coleccionable`: incluye la franquicia y edición del material coleccionable.
    - `JuegoMesa`: incluye el creador, las cantidades de jugadores y edad recomendada.
    - `TCG`: incluye nombre del juego, edad recomendada y el tipo de producto (como un ENUM)   
        - `TipoTCG`: este enum contiene los tipos de producto disponibles en la subclase TCG para evitar errores de escritura.
