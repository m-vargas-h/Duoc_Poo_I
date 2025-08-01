
package com.duoc.exp2_s5.servicio;

import com.duoc.exp2_s5.modelo.*;
import com.duoc.exp2_s5.excepcion.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private List<Libro> catalogo;
    private Map<String, Usuario> usuarios;

    public Biblioteca() {
        this.catalogo = new ArrayList<>();
        this.usuarios = new HashMap<>();
    }

    // ——— Gestión de usuarios ———

    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }

    public Usuario buscarUsuario(String id) throws UsuarioNoEncontradoException {
        Usuario u = usuarios.get(id);
        if (u == null) {
            throw new UsuarioNoEncontradoException("Usuario con ID '" + id + "' no encontrado.");
        }
        return u;
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        usuarios.values().forEach(System.out::println);
    }

    //Comprueba si ya existe un usuario con ese ID.
    public boolean existeUsuario(String id) {
        return usuarios.containsKey(id);
    }

    // Devuelve un mapa de usuarios, útil para pruebas o para acceder a los usuarios directamente.
    public Map<String,Usuario> getUsuarios() {
        return usuarios;
    }

    // ——— Gestión de libros ———

    // Agrega un libro al catálogo. Si el libro ya existe, no lo agrega y devuelve false.
    public boolean agregarLibro(Libro libro) {
        // Verificar si el libro ya existe en el catálogo
        if (catalogo.contains(libro)) {
            return false; // El libro ya existe
        }
        catalogo.add(libro);
        return true;
    }

    // Busca un libro por su nombre. Si no lo encuentra, lanza una excepción.
    public Libro buscarLibro(String nombre) throws LibroNoEncontradoException {
        for (Libro l : catalogo) {
            if (l.getNombre().equalsIgnoreCase(nombre)) {
                return l;
            }
        }
        throw new LibroNoEncontradoException("No se encontró el libro: " + nombre);
    }

    // Elimina un libro del catálogo. Si el libro no está en el catálogo, devuelve false.
    public boolean eliminarLibro(Libro libro) {
        return catalogo.remove(libro);
    }

    // Lista todos los libros en el catálogo. Si el catálogo está vacío, informa al usuario.
    public void listarCatalogo() {
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío.");
            return;
        }
        catalogo.forEach(System.out::println);
    }

    //todo revisar cuando se usa getCatalogo() y getLibros()
    public List<Libro> getCatalogo() {
        return catalogo;
    }

    public List<Libro> getLibros() {
        return catalogo;
    }



    // ——— Préstamo / Devolución ———

    // Presta un libro a un usuario. Verifica si el usuario existe, si el libro existe y si está disponible.
    public void prestarLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   LibroNoDisponibleException,
                   MaximoPrestamoException {
        Usuario u = buscarUsuario(usuarioId);

        // Verificar si el usuario ya tiene 3 libros prestados
        if (u.getLibrosPrestados().size() >= 3) {
            throw new MaximoPrestamoException("Ha alcanzado el máximo de 3 préstamos. Devuelva uno primero.");
        }

        Libro l = buscarLibro(titulo);
        l.prestar();           // puede lanzar LibroNoDisponibleException
        u.agregarPrestamo(l);
    }

    // Devuelve un libro prestado por un usuario. Verifica si el usuario existe, si el libro existe y si está prestado.
    public void devolverLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   CopiaInvalidaException,
                   LibroNoPrestadoException {
        Usuario  u = buscarUsuario(usuarioId);
        Libro    l = buscarLibro(titulo);

        //? [DEBUG] Verificar el estado del libro antes de devolver
        //System.out.println("Antes de devolver, copias disponibles de '" + l.getNombre() + "': " + l.getCopiasDisponibles());
        l.devolver();          // puede lanzar CopiaInvalidaException
        //? [DEBUG] Verificar el estado del libro después de devolver
        //System.out.println("Después de devolver, copias disponibles de '" + l.getNombre() + "': " + l.getCopiasDisponibles());
        u.devolverPrestamo(l);
    }
}