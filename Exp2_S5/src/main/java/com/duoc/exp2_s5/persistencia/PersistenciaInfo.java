package com.duoc.exp2_s5.persistencia;

import com.duoc.exp2_s5.modelo.*;
import com.duoc.exp2_s5.servicio.Biblioteca;

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

    // Rutas de los archivos CSV
    private static final Path rutaBase = Paths.get(System.getProperty("user.dir"), "Exp2_S4", "Data");
    private static final Path ARCHIVO_LIBROS   = rutaBase.resolve("libros.csv");
    private static final Path ARCHIVO_USUARIOS = rutaBase.resolve("usuarios.csv");
    private static final Path ARCHIVO_PRESTAMOS = rutaBase.resolve("prestamos.csv");

    //todo: revisar el funcionamiento de la ruta base
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

    public static void cargarLibros(Biblioteca bib) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_LIBROS.toFile()))) {
            String linea = br.readLine(); // Se salta la cabecera
            if (linea == null) {
                throw new IOException("CSV vacío: " + ARCHIVO_LIBROS);
            }
        
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                // Se espera exactamente 6 campos, de lo contrario lanza la excepción
                if (st.countTokens() != 6) {
                    System.out.println("Formato de línea inválido: " + linea);
                    continue;
                }
            
                String nombre        = st.nextToken().trim();
                String autor         = st.nextToken().trim();
                String clasificacion = st.nextToken().trim();
                String editorial     = st.nextToken().trim();
                int totalCopias;
                int copiasDisponibles;
            
                try {
                    totalCopias = Integer.parseInt(st.nextToken().trim());
                    copiasDisponibles = Integer.parseInt(st.nextToken().trim());
                } catch (NumberFormatException ex) {
                    System.out.println("Error: no se pudo convertir el valor numérico de la línea: \"" + linea + "\".");
                    continue;
                }
            
                // Agrega el libro reconstruido con la información completa a la biblioteca
                bib.agregarLibro(new Libro(nombre, autor, clasificacion, editorial, totalCopias, copiasDisponibles));
            }
        }
    }

    public static void cargarUsuarios(Biblioteca bib) throws IOException {

        //? [DEBUG] Linea para verificar funcionamiento
        //System.out.println("[DEBUG] Leyendo usuarios en: " + ARCHIVO_USUARIOS.toAbsolutePath());

        // Si no existe el archivo, lo crea con la cabecera
        if (Files.notExists(ARCHIVO_USUARIOS)) {
            Files.createDirectories(rutaBase);
            try (PrintWriter pw = new PrintWriter(ARCHIVO_USUARIOS.toFile())) {
                pw.println("id,nombre,carrera,sede");
            }
            
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS.toFile()))) {
            // Leer cabecera
            String linea = br.readLine();
            if (linea == null) {
                // CSV existe pero está vacío → retorno sin excepción
                return;
            }

            // Procesar cada registro
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

        //? [DEBUG] Linea para verificar funcionamiento
        //System.out.println("[DEBUG] Escribiendo usuario en: " + ARCHIVO_USUARIOS.toAbsolutePath());

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

    public static void guardarLibros(List<Libro> catalogo) throws IOException {
        if (Files.notExists(rutaBase)) {
            Files.createDirectories(rutaBase);
        }
        try (PrintWriter pw = new PrintWriter(ARCHIVO_LIBROS.toFile())) {
            // cabecera del CSV
            pw.println("nombre,autor,clasificacion,editorial,totalCopias,copiasDisponibles");

            // Por cada libro, grabamos los datos completos
            for (Libro l : catalogo) {
                pw.printf("%s,%s,%s,%s,%d,%d%n",
                        l.getNombre(),
                        l.getAutor(),
                        l.getClasificacion(),
                        l.getEditorial(),
                        l.getTotalCopias(),
                        l.getCopiasDisponibles());
            }
        }
    }

    // Guarda los préstamos activos de los usuarios en un CSV
    public static void guardarPrestamos(Map<String, Usuario> usuarios) throws IOException {
        if (Files.notExists(rutaBase)) Files.createDirectories(rutaBase);
        try (PrintWriter pw = new PrintWriter(ARCHIVO_PRESTAMOS.toFile())) {
            pw.println("usuarioId,nombreLibro,fechaPrestamo");
            for (Usuario u : usuarios.values()) {
                
                for (Libro l : u.getLibrosPrestados()) {
                    String fecha = Instant.now().toString(); // o la fecha original del préstamo
                    pw.printf("%s,%s,%s%n", u.getId(), l.getNombre(), fecha);
                }
            }
        }
    }

    // Carga los préstamos desde el CSV y los asigna a los usuarios
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

                    u.agregarPrestamo(l);
                } catch (Exception e) {
                    // Si algo falla (usuario o libro no existe), lo ignoramos o lo registramos
                    System.err.println("Error al cargar préstamo: " + e.getMessage());
                }
            }
        }
    }


}