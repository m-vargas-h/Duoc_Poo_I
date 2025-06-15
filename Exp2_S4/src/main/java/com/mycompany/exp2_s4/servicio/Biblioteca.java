
package com.mycompany.exp2_s4.servicio;

import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.modelo.Usuario;
import com.mycompany.exp2_s4.excepcion.*;

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

    public Map<String,Usuario> getUsuarios() {
        return usuarios;
    }

    // ——— Gestión de libros ———

    public void agregarLibro(Libro libro) {
        catalogo.add(libro);
    }

    public Libro buscarLibro(String nombre) throws LibroNoEncontradoException {
        for (Libro l : catalogo) {
            if (l.getNombre().equalsIgnoreCase(nombre)) {
                return l;
            }
        }
        throw new LibroNoEncontradoException("No se encontró el libro: " + nombre);
    }

    public void listarCatalogo() {
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío.");
            return;
        }
        catalogo.forEach(System.out::println);
    }

    public List<Libro> getCatalogo() {
        return catalogo;
    }


    // ——— Préstamo / Devolución ———

    public void prestarLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   LibroNoDisponibleException,
                   MaximoPrestamoException {
        Usuario u = buscarUsuario(usuarioId);

        // <— nueva validación de tope 3
        if (u.getLibrosPrestados().size() >= 3) {
            throw new MaximoPrestamoException("Ha alcanzado el máximo de 3 préstamos. Devuelva uno primero.");
        }

        Libro l = buscarLibro(titulo);
        l.prestar();           // puede lanzar LibroNoDisponibleException
        u.agregarPrestamo(l);
    }


    public void devolverLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   CopiaInvalidaException,
                   LibroNoPrestadoException {
        Usuario  u = buscarUsuario(usuarioId);
        Libro    l = buscarLibro(titulo);

        System.out.println("Antes de devolver, copias disponibles de '" + l.getNombre() + "': " + l.getCopiasDisponibles());
        l.devolver();          // puede lanzar CopiaInvalidaException
        System.out.println("Después de devolver, copias disponibles de '" + l.getNombre() + "': " + l.getCopiasDisponibles());
        u.devolverPrestamo(l);
    }
}