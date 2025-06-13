
package com.mycompany.exp2_s4.modelo;

import com.mycompany.exp2_s4.excepcion.LibroNoPrestadoException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;                   // Identificador único
    private String nombre;               // Nombre completo
    private String carrera;              // Carrera del usuario
    private String sede;                 // Sede asignada
    private List<Libro> librosPrestados; // Libros que tiene prestados

    // Constructor
    public Usuario(String id, String nombre, String carrera, String sede) {
        this.id              = id;
        this.nombre          = nombre;
        this.carrera         = carrera;
        this.sede            = sede;
        this.librosPrestados = new ArrayList<>();
    }

    // Getters / Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSede() {
        return sede;
    }
    public void setSede(String sede) {
        this.sede = sede;
    }

    public List<Libro> getLibrosPrestados() {
        return new ArrayList<>(librosPrestados);
    }

    // Registra un préstamo (Biblioteca ya llamó a libro.prestar())
    public void agregarPrestamo(Libro libro) {
        librosPrestados.add(libro);
    }

    // Registra una devolución; lanza excepción si no lo tenía prestado
    public void devolverPrestamo(Libro libro) throws LibroNoPrestadoException {
        boolean removed = librosPrestados.remove(libro);
        if (!removed) {
            throw new LibroNoPrestadoException(
                "Usuario '" + nombre + "' no tiene prestado '" + libro.getNombre() + "'."
            );
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Usuario[id=%s, nombre='%s', carrera='%s', sede='%s', prestados=%d]",
            id, nombre, carrera, sede, librosPrestados.size()
        );
    }
}