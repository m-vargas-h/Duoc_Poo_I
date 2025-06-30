package com.duoc.exp2_s6.modelo.base;

import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import java.util.Objects;

// Representa un usuario del sistema con ID, nombre y email.
public class Usuario implements ConvertirCsv {
    private final String id;    // identificador único
    private String nombre;      // nombre completo o alias
    private String email;       // email del usuario

    // Constructor que inicializa todos los campos.
    public Usuario(String id, String nombre, String email) {
        this.id     = Objects.requireNonNull(id,     "ID no puede ser null");
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser null");
        setEmail(email);
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser null");
    }

    public String getEmail() {
        return email;
    }

    // Establece el email del usuario, validando que no sea null ni vacío.
    public void setEmail(String email) {
        Objects.requireNonNull(email, "Email no puede ser null");
        String correo = email.trim();
        if (correo.isEmpty()) {
            throw new IllegalArgumentException("Email no puede estar vacío");
        }
        this.email = correo;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", nombre, id, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario u = (Usuario) o;
        return id.equals(u.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --------------------------------- Métodos CSV -------------------------------------------------

    // Convierte el objeto Usuario a una línea CSV:
    @Override
    public String toCsvLine() {
        // Escapa comas en el nombre o email si fuera necesario
        return String.join(",",
            escape(id),
            escape(nombre),
            escape(email)
        );
    }

    // Convierte un array de tokens CSV a un objeto Usuario:
    public static Usuario fromCsvTokens(String[] tokens) {
        String id     = unescape(tokens[0]);
        String nombre = unescape(tokens[1]);
        String email  = unescape(tokens[2]);
        return new Usuario(id, nombre, email);
    }

    private static String escape(String campo) {
        String limpio = campo.replace("\"", "\"\"");
        if (limpio.contains(",") || limpio.contains("\"") || limpio.contains("\n")) {
            return "\"" + limpio + "\"";
        }
        return limpio;
    }

    private static String unescape(String token) {
        if (token.startsWith("\"") && token.endsWith("\"")) {
            String interior = token.substring(1, token.length() - 1);
            return interior.replace("\"\"", "\"");
        }
        return token;
    }
}