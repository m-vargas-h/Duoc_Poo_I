package com.duoc.exp2_s5.ui.libro;

import com.duoc.exp2_s5.modelo.Libro;
import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import java.io.IOException;
import java.util.Scanner;

public class AgregarLibroMenu {

    private ServiciosBiblioteca svc;
    private Scanner sc;

    public AgregarLibroMenu(ServiciosBiblioteca svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    public void mostrar() {
        System.out.println("\n----- AGREGAR LIBRO -----");
        
        // Recolectar datos del libro
        System.out.print("Ingrese el título: ");
        String titulo = sc.nextLine().trim();
        
        System.out.print("Ingrese el autor: ");
        String autor = sc.nextLine().trim();
        
        System.out.print("Ingrese la clasificación: ");
        String clasificacion = sc.nextLine().trim();
        
        System.out.print("Ingrese la editorial: ");
        String editorial = sc.nextLine().trim();
        
        // Leer el total de copias y las disponibles
        System.out.print("Ingrese el total de copias: ");
        int totalCopias = Integer.parseInt(sc.nextLine().trim());
        
        System.out.print("Ingrese las copias disponibles: ");
        int copiasDisponibles = Integer.parseInt(sc.nextLine().trim());
        
        // (Opcional) Verificar que las copias disponibles no excedan el total
        if (copiasDisponibles > totalCopias) {
            System.out.println("Error: Las copias disponibles no pueden exceder el total de copias.");
            return;
        }
        
        // Crear el objeto libro
        Libro libro = new Libro(titulo, autor, clasificacion, editorial, totalCopias, copiasDisponibles);
        
        // Agregar el libro mediante el servicio. 
        // Se asume que svc.agregarLibro(libro) retorna un boolean indicando éxito
        boolean resultado = svc.agregarLibro(libro);
        if (resultado) {
            System.out.println("Libro agregado exitosamente.");
            // Intentar persistir los cambios (guardar el catálogo actualizado en el CSV)
            try {
                svc.guardarLibros();
            } catch (IOException e) {
                System.err.println("Error al guardar libros: " + e.getMessage());
            }
        } else {
            System.out.println("Error: El libro ya existe o no se pudo agregar.");
        }
    }
}