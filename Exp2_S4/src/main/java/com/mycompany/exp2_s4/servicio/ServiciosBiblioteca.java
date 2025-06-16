package com.mycompany.exp2_s4.servicio;

import com.mycompany.exp2_s4.modelo.Libro;
import com.mycompany.exp2_s4.modelo.Usuario;
import com.mycompany.exp2_s4.excepcion.*;
import com.mycompany.exp2_s4.persistencia.PersistenciaInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ServiciosBiblioteca {

    private final Biblioteca biblioteca;

    public ServiciosBiblioteca(Biblioteca biblioteca) {

        this.biblioteca = biblioteca;

    }

    // Cargar datos iniciales desde archivos
    public void cargarDatos() throws IOException {
        PersistenciaInfo.cargarLibros(biblioteca);
        PersistenciaInfo.cargarUsuarios(biblioteca);
        PersistenciaInfo.cargarPrestamos(biblioteca);

    }

    // Registrar un nuevo usuario
    public void registrarUsuario(String id, String nombre, String carrera, String sede) 
            throws UsuarioYaExisteException, IOException {

        if (biblioteca.existeUsuario(id)) {
            throw new UsuarioYaExisteException("Usuario con ID " + id + " ya existe.");
        }
        Usuario nuevo = new Usuario(id, nombre, carrera, sede);
        biblioteca.agregarUsuario(nuevo);
        PersistenciaInfo.guardarUsuario(nuevo);
    }

    // Pedir un libro
    public void solicitarLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   LibroNoDisponibleException,
                   MaximoPrestamoException,
                   IOException {
                    
        biblioteca.prestarLibro(usuarioId, titulo);
        // reescribe libros.csv con las nuevas copias disponibles
        PersistenciaInfo.guardarLibros(biblioteca.getCatalogo());
        PersistenciaInfo.guardarPrestamos(biblioteca.getUsuarios());

    }

    // Devolver un libro
    public void devolverLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   CopiaInvalidaException,
                   LibroNoPrestadoException,
                   IOException {
        biblioteca.devolverLibro(usuarioId, titulo);
        PersistenciaInfo.guardarLibros(biblioteca.getCatalogo());
        PersistenciaInfo.guardarPrestamos(biblioteca.getUsuarios());

    }

    // Consultar el estado de un libro
    public String consultarLibro(String titulo) throws LibroNoEncontradoException {
        Libro l = biblioteca.buscarLibro(titulo);
        return l.isDisponible()
             ? "Disponible (" + l.getCopiasDisponibles() + " copias)"
             : "No disponible";
    }

    // Consultar el estado de un usuario
    public Usuario consultarUsuario(String id) throws UsuarioNoEncontradoException {
        return biblioteca.buscarUsuario(id);
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public List<Libro> listarLibros() {
        return biblioteca.getCatalogo();
    }

    public Map<String,Usuario> listarUsuarios() {
        return biblioteca.getUsuarios();
    }
    
}
