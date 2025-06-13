package com.mycompany.exp2_s4.persistencia;

import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.modelo.Usuario;
import com.mycompany.exp2_s4.servicio.Biblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class PersistenciaInfo {

    // Ajusta aquí el nombre de tu módulo
    private static final Path rutaBase =
        Paths.get(System.getProperty("user.dir"), "Exp2_S4", "Data");
    private static final Path ARCHIVO_LIBROS   = rutaBase.resolve("libros.csv");
    private static final Path ARCHIVO_USUARIOS = rutaBase.resolve("usuarios.csv");

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
}