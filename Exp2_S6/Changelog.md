# Historial de cambios

## 28/06/2025 - Carga inicial proyecto
Se crea la primera base del proyecto **ComicCollectorSystem** con las siguientes clases:
- `Producto`: Clase abstracta principal que define los atributos base de un producto
    - `Comic`: incorpora los campos editorial y volumen (campo opcional con constructor para cada caso)
    - `NovelaGrafica`: incluye autor y fechaPublicacion. 
    - `Coleccionable`: incluye la franquicia y edición del material coleccionable.
    - `JuegoMesa`: incluye el creador, las cantidades de jugadores y edad recomendada.
    - `TCG`: incluye nombre del juego, edad recomendada y el tipo de producto (como un ENUM)   
        - `TipoTCG`: este enum contiene los tipos de producto disponibles en la subclase TCG para evitar errores de escritura.
