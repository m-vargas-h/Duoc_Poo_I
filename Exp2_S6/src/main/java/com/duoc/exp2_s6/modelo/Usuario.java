package com.duoc.exp2_s6.modelo;

import java.util.Objects;

public class Usuario {
    private final String id;       // identificador único
    private String nombre;         // nombre completo o alias
    private String email;          // email del usuario

    /**
     * Crea un usuario con ID, nombre y email.
     * 
     * @param id     identificador único, no nulo
     * @param nombre nombre del usuario, no nulo
     * @param email  dirección de correo electrónico, no nula ni vacía
     */
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

    /**
     * Actualiza el email. Valida que no sea null ni vacío.
     */
    public void setEmail(String email) {
        Objects.requireNonNull(email, "Email no puede ser null");
        String correo = email.trim();
        if (correo.isEmpty()) {
            throw new IllegalArgumentException("Email no puede estar vacío");
        }
        // Si quieres validación más estricta, podrías usar un regex aquí.
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
        // La igualdad se basa únicamente en el ID
        return id.equals(u.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}