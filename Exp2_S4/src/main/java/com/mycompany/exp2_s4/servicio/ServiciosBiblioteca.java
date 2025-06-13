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

    // Ejemplo de método para cargar datos
    public void cargarDatos() throws IOException {
        PersistenciaInfo.cargarLibros(biblioteca);
        PersistenciaInfo.cargarUsuarios(biblioteca);
    }

    // Delegas en Biblioteca las operaciones, lanzando/gestionando excepciones
    public void registrarUsuario(String id, String nombre,
                                 String carrera, String sede)
            throws UsuarioYaExisteException {
        if (biblioteca.existeUsuario(id)) {
            throw new UsuarioYaExisteException("Usuario con ID " + id + " ya existe.");
        }
        biblioteca.agregarUsuario(new Usuario(id, nombre, carrera, sede));
    }

    public void solicitarLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   LibroNoDisponibleException {
        biblioteca.prestarLibro(usuarioId, titulo);
    }

    public void devolverLibro(String usuarioId, String titulo)
            throws UsuarioNoEncontradoException,
                   LibroNoEncontradoException,
                   CopiaInvalidaException,
                   LibroNoPrestadoException {
        biblioteca.devolverLibro(usuarioId, titulo);
    }

    public String consultarLibro(String titulo) throws LibroNoEncontradoException {
        Libro l = biblioteca.buscarLibro(titulo);
        return l.isDisponible()
             ? "Disponible (" + l.getCopiasDisponibles() + " copias)"
             : "No disponible";
    }

    // Si en algún punto necesitas pasar la instancia de Biblioteca a PersistenciaInfo...
    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    // Métodos para devolver listas o mapas al UI si quieres mostrar todos
    public List<Libro> listarLibros() {
        return biblioteca.getCatalogo();
    }
    public Map<String,Usuario> listarUsuarios() {
        return biblioteca.getUsuarios();
    }
}
