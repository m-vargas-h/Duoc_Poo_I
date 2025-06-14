package com.mycompany.exp2_s4.persistencia;

import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.modelo.Usuario;
import com.mycompany.exp2_s4.servicio.Biblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class PersistenciaInfo {

    // Ajusta aquí el nombre de tu módulo
    private static final Path rutaBase = Paths.get(System.getProperty("user.dir"), "Exp2_S4", "Data");
    private static final Path ARCHIVO_LIBROS   = rutaBase.resolve("libros.csv");
    private static final Path ARCHIVO_USUARIOS = rutaBase.resolve("usuarios.csv");
    private static final Path ARCHIVO_PRESTAMOS = rutaBase.resolve("prestamos.csv");


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

    private PersistenciaInfo() { /* no instanciable */ }

    public static void cargarLibros(Biblioteca bib) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_LIBROS.toFile()))) {
            String linea = br.readLine(); // salto cabecera
            if (linea == null) throw new IOException("CSV vacío: " + ARCHIVO_LIBROS);

            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                if (st.countTokens() != 5) continue;
                String nombre        = st.nextToken().trim();
                String autor         = st.nextToken().trim();
                String clasificacion = st.nextToken().trim();
                String editorial     = st.nextToken().trim();
                int totalCopias;
                try {
                    totalCopias = Integer.parseInt(st.nextToken().trim());
                } catch (NumberFormatException ex) {
                    continue;
                }
                bib.agregarLibro(new Libro(
                    nombre, autor, clasificacion, editorial, totalCopias
                ));
            }
        }
    }

    public static void cargarUsuarios(Biblioteca bib) throws IOException {

        //? Linea para verificar funcionamiento
        System.out.println("[DEBUG] Leyendo usuarios en: " + ARCHIVO_USUARIOS.toAbsolutePath());

        // 1) Si no existe el CSV de usuarios, crearlo con la línea de cabecera
        if (Files.notExists(ARCHIVO_USUARIOS)) {
            Files.createDirectories(rutaBase);                   // aseguro carpeta Data
            try (PrintWriter pw = new PrintWriter(ARCHIVO_USUARIOS.toFile())) {
                pw.println("id,nombre,carrera,sede");
            }
            // no hay usuarios que cargar → retorno
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS.toFile()))) {
            // 2) Leer cabecera
            String linea = br.readLine();
            if (linea == null) {
                // CSV existe pero está vacío → retorno sin excepción
                return;
            }

            // 3) Procesar cada registro
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                if (st.countTokens() != 4) continue;
                String id       = st.nextToken().trim();
                String nombre   = st.nextToken().trim();
                String carrera  = st.nextToken().trim();
                String sede     = st.nextToken().trim();
                bib.agregarUsuario(new Usuario(id, nombre, carrera, sede));
            }
        }
    }

    public static void guardarUsuario(Usuario u) throws IOException {

        //? Linea para verificar funcionamiento
        System.out.println("[DEBUG] Escribiendo usuario en: " + ARCHIVO_USUARIOS.toAbsolutePath());

        // Asegura carpeta y cabecera la primera vez
        if (Files.notExists(ARCHIVO_USUARIOS)) {
            Files.createDirectories(rutaBase);
            try (PrintWriter pw = new PrintWriter(ARCHIVO_USUARIOS.toFile())) {
                pw.println("id,nombre,carrera,sede");
            }
        }
        // Escribe una línea al final del CSV
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS.toFile(), true))) {
            pw.printf("%s,%s,%s,%s%n",
                      u.getId(),
                      u.getNombre(),
                      u.getCarrera(),
                      u.getSede());
        }
    }

    // PersistenciaInfo.java (al final de la clase)
    public static void guardarLibros(List<Libro> catalogo) throws IOException {
        // Asegura carpeta y cabecera
        if (Files.notExists(rutaBase)) {
            Files.createDirectories(rutaBase);
        }
        try (PrintWriter pw = new PrintWriter(ARCHIVO_LIBROS.toFile())) {
            // Cabecera igual que en cargarLibros
            pw.println("nombre,autor,clasificacion,editorial,totalCopias");
            // Por cada libro, grabo la copia disponible actual
            for (Libro l : catalogo) {
                pw.printf("%s,%s,%s,%s,%d%n",
                        l.getNombre(),
                        l.getAutor(),
                        l.getClasificacion(),
                        l.getEditorial(),
                        l.getCopiasDisponibles());
            }
        }
    }

    /** Volca todo el estado de préstamos a CSV */
    public static void guardarPrestamos(Map<String, Usuario> usuarios) throws IOException {
        if (Files.notExists(rutaBase)) Files.createDirectories(rutaBase);
        try (PrintWriter pw = new PrintWriter(ARCHIVO_PRESTAMOS.toFile())) {
            pw.println("usuarioId,nombreLibro,fechaPrestamo");
            for (Usuario u : usuarios.values()) {
                for (Libro l : u.getLibrosPrestados()) {
                    String fecha = Instant.now().toString(); // o la que guardes al prestar
                    pw.printf("%s,%s,%s%n", u.getId(), l.getNombre(), fecha);
                }
            }
        }
    }

    /** Al iniciar, lee los préstamos y los aplica en memoria */
    public static void cargarPrestamos(Biblioteca bib) throws IOException {
        if (Files.notExists(ARCHIVO_PRESTAMOS)) return;
        try (BufferedReader br = Files.newBufferedReader(ARCHIVO_PRESTAMOS)) {
            String line = br.readLine(); // cabecera
            while ((line = br.readLine()) != null) {
                String[] f = line.split(",", 3);
                String usuarioId  = f[0].trim();
                String libroNombre= f[1].trim();
                try {
                    Usuario u = bib.buscarUsuario(usuarioId);
                    Libro l   = bib.buscarLibro(libroNombre);
                    // Reducir stock sin duplicar la línea en el CSV de libros
                    l.prestar();
                    u.agregarPrestamo(l);
                } catch (Exception e) {
                    // Si algo falla (usuario o libro no existe), lo ignoramos o lo registramos
                    System.err.println("WARN al cargar préstamo: " + e.getMessage());
                }
            }
        }
    }


}