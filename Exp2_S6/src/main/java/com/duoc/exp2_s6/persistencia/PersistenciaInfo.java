package com.duoc.exp2_s6.persistencia;

import com.duoc.exp2_s6.modelo.*;
import com.duoc.exp2_s6.servicio.Biblioteca;

import java.io.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;

public class PersistenciaInfo {

    // Rutas de los archivos CSV
    private static final Path rutaBase       = Paths.get(System.getProperty("user.dir"), "Exp2_S6", "Data");
    private static final Path ARCHIVO_LIBROS   = rutaBase.resolve("libros.csv");
    private static final Path ARCHIVO_USUARIOS = rutaBase.resolve("usuarios.csv");
    private static final Path ARCHIVO_PRESTAMOS= rutaBase.resolve("prestamos.csv");
    private static final Path ARCHIVO_ADMIN    = rutaBase.resolve("admin.csv");

    // Cabeceras de los archivos CSV
    private static final String LIBROS_HEADER    = "nombre,autor,clasificacion,editorial,totalCopias,copiasDisponibles";
    private static final String USUARIOS_HEADER  = "id,nombre,carrera,sede";
    private static final String PRESTAMOS_HEADER = "usuarioId,nombreLibro,fechaPrestamo";
    private static final String ADMIN_HEADER     = "rut,nombre,password,rol";

    // ---------------------------------- INICIALIZACIÓN ESTÁTICA ----------------------------------

    // Crea la carpeta "Data" si no existe al iniciar la clase.
    static {
        try {
            if (Files.notExists(rutaBase)) {
                Files.createDirectories(rutaBase);
                System.out.println("Carpeta 'Data' creada en: " + rutaBase);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("No pude crear Data/: " + e);
        }
    }

    // ----------------------------------- MÉTODOS AUXILIARES --------------------------------------

    // Asegura que el archivo CSV exista, creando su directorio y escribiendo la cabecera si no existe.
    private static void asegurarCsv(Path file, String header) throws IOException {
        Path parent = file.getParent();
        if (Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
        if (Files.notExists(file)) {
            try (PrintWriter pw = new PrintWriter(file.toFile())) {
                pw.println(header);
            }
        }
    }

    // Lee el CSV completo, saltando la cabecera y filtrando filas con menos de minColumns columnas.
    private static List<String[]> leerTodo(Path file, int minColumns) throws IOException {
        if (Files.notExists(file)) {
            return Collections.emptyList();
        }
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line = br.readLine();            // salto cabecera
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",", -1);
                if (cols.length >= minColumns) {
                    rows.add(cols);
                }
            }
        }
        return rows;
    }

    // Recibe un archivo CSV y una cabecera, y escribe todas las filas de items en el CSV.
    // Si el archivo no existe, lo crea con la cabecera.
    private static <T> void escribirTodo(Path file, String header, List<T> items,
                                     BiConsumer<PrintWriter,T> rowWriter) throws IOException {
        asegurarCsv(file, header);
        try (PrintWriter pw = new PrintWriter(file.toFile())) {
            pw.println(header);
            for (T item : items) {
                rowWriter.accept(pw, item);
            }
        }
    }

    // Recibe un archivo CSV, una cabecera y un único item, y lo agrega al final del CSV.
    // Si el archivo no existe, lo crea con la cabecera.
    private static <T> void agregar(Path file, String header, T item,
                                      BiConsumer<PrintWriter,T> rowWriter) throws IOException {
        asegurarCsv(file, header);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file.toFile(), true))) {
            rowWriter.accept(pw, item);
        }
    }

    // ------------------------------------------- LIBROS ------------------------------------------

    // Carga los libros desde el archivo CSV y los agrega a la biblioteca.
    public static void cargarLibros(Biblioteca bib) throws IOException {
        List<String[]> rows = leerTodo(ARCHIVO_LIBROS, 6);
        for (String[] c : rows) {
            try {
                String nombre        = c[0].trim();
                String autor         = c[1].trim();
                String clasificacion = c[2].trim();
                String editorial     = c[3].trim();
                int totalCopias       = Integer.parseInt(c[4].trim());
                int copiasDisponibles = Integer.parseInt(c[5].trim());
                bib.agregarLibro(new Libro(
                    nombre, autor, clasificacion, editorial,
                    totalCopias, copiasDisponibles));
            } catch (NumberFormatException ex) {
                System.err.println("Error conversión numérica en línea: " + Arrays.toString(c));
            }
        }
    }

    // Guarda una lista de libros en el archivo CSV, sobrescribiendo el contenido.
    public static void guardarLibros(List<Libro> catalogo) throws IOException {
        escribirTodo(ARCHIVO_LIBROS, LIBROS_HEADER, catalogo, (pw, l) ->
            pw.printf("%s,%s,%s,%s,%d,%d%n",
                l.getNombre(),
                l.getAutor(),
                l.getClasificacion(),
                l.getEditorial(),
                l.getTotalCopias(),
                l.getCopiasDisponibles()
            )
        );
    }

    // ------------------------------------------ USUARIOS -----------------------------------------

    // Carga los usuarios desde el archivo CSV y los agrega a la biblioteca.
    public static void cargarUsuarios(Biblioteca bib) throws IOException {
        List<String[]> rows = leerTodo(ARCHIVO_USUARIOS, 4);
        for (String[] c : rows) {
            Usuario u = new Usuario(
                c[0].trim(),
                c[1].trim(),
                c[2].trim(),
                c[3].trim()
            );
            bib.agregarUsuario(u);
        }
    }

    // Guarda un único usuario en el archivo CSV, agregándolo al final.
    public static void guardarUsuario(Usuario u) throws IOException {
        agregar(ARCHIVO_USUARIOS, USUARIOS_HEADER, u, (pw, usr) ->
            pw.printf("%s,%s,%s,%s%n",
                usr.getId(),
                usr.getNombre(),
                usr.getCarrera(),
                usr.getSede()
            )
        );
    }

    // ---------------------------------------- PRÉSTAMOS ------------------------------------------

    // Carga los préstamos desde el archivo CSV y los asigna a los usuarios correspondientes.
    public static void cargarPrestamos(Biblioteca bib) throws IOException {
        List<String[]> rows = leerTodo(ARCHIVO_PRESTAMOS, 3);
        for (String[] c : rows) {
            String usuarioId  = c[0].trim();
            String libroNombre= c[1].trim();
            String fechaStr   = c[2].trim();
            try {
                Usuario u = bib.buscarUsuario(usuarioId);
                Libro   l = bib.buscarLibro(libroNombre);
                u.agregarPrestamo(l);
            } catch (Exception ex) {
                System.err.println("Error al cargar préstamo: " + Arrays.toString(c) + " → " + ex.getMessage());
            }
        }
    }

    // Guarda los préstamos actuales de todos los usuarios en el archivo CSV, sobrescribiendo el contenido.
    public static void guardarPrestamos(Map<String,Usuario> usuarios) throws IOException {
        asegurarCsv(ARCHIVO_PRESTAMOS, PRESTAMOS_HEADER);
        try (PrintWriter pw = new PrintWriter(ARCHIVO_PRESTAMOS.toFile())) {
            pw.println(PRESTAMOS_HEADER);
            for (Usuario u : usuarios.values()) {
                for (Libro l : u.getLibrosPrestados()) {
                    String fecha = Instant.now().toString();
                    pw.printf("%s,%s,%s%n",
                        u.getId(),
                        l.getNombre(),
                        fecha
                    );
                }
            }
        }
    }

    // -------------------------------------- ADMINISTRADORES --------------------------------------

    // Carga los administradores desde el archivo CSV y los devuelve en un mapa.
    public static Map<String,Admin> cargarAdmin() throws IOException {
        Map<String,Admin> mapa = new HashMap<>();
        List<String[]> rows = leerTodo(ARCHIVO_ADMIN, 4);
        for (String[] c : rows) {
            String rut      = c[0].trim();
            String nombre   = c[1].trim();
            String pwd      = c[2].trim();
            Admin.Role rol  = Admin.Role.valueOf(c[3].trim().toUpperCase());
            mapa.put(rut, new Admin(rut, nombre, pwd, rol));
        }
        return mapa;
    }

    // Guarda un único administrador en el archivo CSV, agregándolo al final.
    public static void guardarAdmin(Admin a) throws IOException {
        agregar(ARCHIVO_ADMIN, ADMIN_HEADER, a, (pw, adm) ->
            pw.printf("%s,%s,%s,%s%n",
                adm.getRut(),
                adm.getNombre(),
                adm.getPassword(),
                adm.getRole().name()
            )
        );
    }

    // Guarda una colección de administradores en el archivo CSV, sobrescribiendo el contenido.
    public static void guardarAdmins(Collection<Admin> admins) throws IOException {
        escribirTodo(ARCHIVO_ADMIN, ADMIN_HEADER, new ArrayList<>(admins), (pw, adm) ->
            pw.printf("%s,%s,%s,%s%n",
                adm.getRut(),
                adm.getNombre(),
                adm.getPassword(),
                adm.getRole().name()
            )
        );
    }
}