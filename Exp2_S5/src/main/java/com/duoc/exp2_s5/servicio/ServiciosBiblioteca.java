package com.duoc.exp2_s5.servicio;

import com.duoc.exp2_s5.modelo.*;
import com.duoc.exp2_s5.excepcion.*;
import com.duoc.exp2_s5.persistencia.PersistenciaInfo;

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

    /*
    * Crea un nuevo usuario con rol ASISTENTE y lo persiste.
    * @return false si el RUT ya existía; true si se agregó bien.
    */
    public boolean agregarAsistente(String rut, String nombre, String password) throws IOException {
        Map<String, Admin> creds = PersistenciaInfo.cargarAdmin();
        if (creds.containsKey(rut)) {
            return false;
        }
        Admin asis = new Admin(rut, nombre, password, Admin.Role.ASISTENTE);
        PersistenciaInfo.guardarAdmin(asis);
        return true;
    }

    /*
    * Elimina un asistente del sistema.
    * @param rut El RUT del asistente a eliminar.
    * @return true si se eliminó correctamente, false si el asistente no existía
    * o no era un asistente.
    */
    public boolean eliminarAsistente(String rut) throws IOException {
        Map<String, Admin> creds = PersistenciaInfo.cargarAdmin();

        Admin target = creds.get(rut);
        // sólo permitir borrar si existe y es asistente
        if (target == null || target.getRole() != Admin.Role.ASISTENTE) {
            return false;
        }

        creds.remove(rut);
        PersistenciaInfo.guardarAdmins(creds);
        return true;
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

    // Eliminar un libro
    public boolean eliminarLibro(Libro libro) {
        return biblioteca.eliminarLibro(libro);
    }

    // Consultar el estado de un libro
    public String consultarLibro(String titulo) throws LibroNoEncontradoException {
        Libro l = biblioteca.buscarLibro(titulo);
        return l.isDisponible()
             ? "Disponible (" + l.getCopiasDisponibles() + " copias)"
             : "No disponible";
    }

    public boolean agregarLibro(Libro libro) {
        return biblioteca.agregarLibro(libro);
    }

    public void guardarLibros() throws IOException {
        List<Libro> libros = biblioteca.getLibros();
        PersistenciaInfo.guardarLibros(libros);
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
