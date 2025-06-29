# Historial de cambios

## 28/06/2025 - Optimización persistencia
- Se elimino código duplicado en los métodos de persistencia mediante la creación de métodos auxiliares que encapsulan el código reutilizable por los distintos métodos de lectura/escritura:
    - **asegurarCsv:** verifica la existencia del fichero y lo crea si fuese necesario.
    - **leerTodo:** lee un csv por completo ignorando la cabecera, ademas puede ignorar registros que no contengan el mínimo de columnas necesarias.
    - **escribirTodo:** escribe el csv completo, en caso de no existir lo creara con la cabecera correspondiente.
    - **agregar:** recibe el csv que se esta escribiendo y agrega un registro al final del mismo, en caso no existir, creara el archivo con la cabecera correspondiente y agregara el registro.

### 28/06/2025 - Carga inicial proyecto
- Carga sistema original basado en el proyecto de la semana anterior