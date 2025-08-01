
package com.duoc.exp2_s5.modelo;

import com.duoc.exp2_s5.excepcion.LibroNoDisponibleException;
import com.duoc.exp2_s5.excepcion.CopiaInvalidaException;

public class Libro {

    private String nombre;
    private String autor;
    private String clasificacion;
    private String editorial;
    private int totalCopias;
    private int copiasDisponibles;

    // Constructor para agregar un nuevo libro
    public Libro(String nombre, String autor, String clasificacion, String editorial, int totalCopias) {

        this.nombre            = nombre;
        this.autor             = autor;
        this.clasificacion     = clasificacion;
        this.editorial         = editorial;
        this.totalCopias       = Math.max(0, totalCopias);
        this.copiasDisponibles = this.totalCopias;
        this.copiasDisponibles = this.totalCopias; // Inicialmente todas las copias están disponibles

    }

    // Constructor para cargar un libro desde persistencia, ya con su estado actual
    public Libro(String nombre, String autor, String clasificacion, String editorial, int totalCopias, int copiasDisponibles) {
        
        this.nombre = nombre;
        this.autor = autor;
        this.clasificacion = clasificacion;
        this.editorial = editorial;
        this.totalCopias = Math.max(0, totalCopias);
        // Aseguramos que las copiasDisponibles no excedan el total
        this.copiasDisponibles = (copiasDisponibles <= this.totalCopias) ? copiasDisponibles : this.totalCopias;
    
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getTotalCopias() {
        return totalCopias;
    }

    public void setTotalCopias(int totalCopias) {
        this.totalCopias = Math.max(0, totalCopias);
        // Ajustamos también las disponibles para no exceder el total
        this.copiasDisponibles = Math.min(copiasDisponibles, this.totalCopias);
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        if (copiasDisponibles < 0 || copiasDisponibles > this.totalCopias) {
            throw new IllegalArgumentException("El número de copias disponibles no puede ser negativo ni mayor que el total de copias.");
        }
        this.copiasDisponibles = copiasDisponibles;
    }

    //Indica si al menos 1 copia está disponible.
    public boolean isDisponible() {
        return copiasDisponibles > 0;
    }

    
    //Presta una copia del libro. Si no hay copias disponibles, lanza excepción.
    public void prestar() throws LibroNoDisponibleException {
        if (copiasDisponibles == 0) {
            throw new LibroNoDisponibleException(
                "No hay copias disponibles de '" + nombre + "'.");
        }
        copiasDisponibles--;
    }

    //Devuelve una copia. No puede superar el total de copias originales.
    public void devolver() throws CopiaInvalidaException {
        if (copiasDisponibles >= totalCopias) {
            throw new CopiaInvalidaException(
                "Se intenta devolver más copias de las registradas para '" + nombre + "'.");
        }
        copiasDisponibles++;
    }

    @Override
    public String toString() {
        return String.format(
            "Libro[nombre='%s', autor='%s', clasificación='%s', editorial='%s', totalCopias=%d, disponibles=%d]",
            nombre, autor, clasificacion, editorial, totalCopias, copiasDisponibles);
    }

}