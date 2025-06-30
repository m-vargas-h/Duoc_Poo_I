# Historial de cambios

## 30/06/2025 - Actualización Readme
- Se actualiza el fichero Readme del proyecto.
- Cambios menores en textos y comentarios para facilitar lectura del código.

### 30/06/2025 - Puesta en marcha sistema final
- Implementación de un carrito de compras que permite:
    - Agregar múltiples productos en una misma sesión.
    - Revisión previa y confirmación de pago.
    - Validación de stock antes de confirmar cada línea de compra.
- Flujo de navegación más intuitivo para usuarios:
    - Se permite buscar productos por categoría utilizando subclases.
    - Se reutiliza el método de listado con formato completo `listarPorCategoria()`.
- Nuevo formato en tabla al listar por categoría:
    - Muestra ID, título, volumen (si aplica), estado, stock y precio.
    - Compatible con instancias de Comic y otras clases hijas de Producto.
- Consolidación de lógica de filtrado y visualización de productos en un solo método reutilizable.
- Reutilización de la búsqueda por categoría en el proceso de compra, manteniendo consistencia visual y funcional.
- Reestructuración propuesta para el paquete `modelo`, separando clases en subpaquetes según su rol:
    - `productos` para clases que heredan de `Producto`
    - `ventas` para `Venta` y `ResumenVenta`
    - `base`, `reserva` y `enums` para mejorar mantenibilidad

### 29/06/2025 - Persistencia información
- Se creo la clase `PersistenciaReservas` y `PersistenciaVentas` para poder guardar la información de ventas y reservas de productos, estas clases permiten la lectura y escritura de todas las transacciones hechas en el sistema por los usuarios.
- Se creo la clase `PersistenciaUsuarios` para guardar la información de cada cliente registrado en la tienda.


### 29/06/2025 - Persistencia de productos
- Se creo la clase `PersistenciaProductos` que se encarga de la lectura y escritura de información de todos los productos del sistema.
- Ahora la Clase `ComicCollectorService` carga el archivo de persistencia al iniciar el programa, y lo escribe tras cada cambios detectado.
- Se creo la interface `CovertirCsv` con sus métodos respectivos para que cada subclase pueda definir los datos que debe guardar en el fichero **productos.csv**


### 29/06/2025 - Implementación menu
- Se implementaron métodos a los menus para poder efectuar las operaciones correspondientes:
    - `MenuAdministrador`: Ahora es posible agregar productos, asi como modificar stock
    - `MenuUsuario`: Ahora es posible registrarse y comprar productos
- Se creo un método para asignar ID a los productos de forma automática según su categoría.
- Se definió el método abstracto `getTipoCodigo` para que cada subclase indique el código correspondiente y poder construir el ID de producto.
- Se crearon excepciones personalizadas para manejar posible problemas al ingresar información.

### 28/06/2025 - Diseño menu navegación
Se diseñaron 3 menus para poder interactuar con el sistema:
- `MenuPrincipal`: En este menu se define si quien esta ingresando es un administrador o un usuario.
    - `MenuAdministrador`: Contiene las opciones disponibles para administradores, como lo es agregar productos, modificar su stock o cambiar su disponibilidad entre otros.
    - `MenuUsuario`: contiene las opciones de registro, compra y búsqueda de productos para usuarios nuevos y ya registrados.

### 28/06/2025 - Carga inicial proyecto semana 6
Se crea la primera base del proyecto **ComicCollectorSystem** con las siguientes clases:
- `Producto`: Clase abstracta principal que define los atributos base de un producto
    - `Comic`: incorpora los campos editorial y volumen (campo opcional con constructor para cada caso)
    - `NovelaGrafica`: incluye autor y fechaPublicacion. 
    - `Coleccionable`: incluye la franquicia y edición del material coleccionable.
    - `JuegoMesa`: incluye el creador, las cantidades de jugadores y edad recomendada.
    - `TCG`: incluye nombre del juego, edad recomendada y el tipo de producto (como un ENUM)   
        - `TipoTCG`: este enum contiene los tipos de producto disponibles en la subclase TCG para evitar errores de escritura.
