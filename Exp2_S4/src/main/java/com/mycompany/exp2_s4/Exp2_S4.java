
package com.mycompany.exp2_s4;

import com.mycompany.exp2_s4.servicio.ServiciosBiblioteca;
import com.mycompany.exp2_s4.servicio.Biblioteca;
import com.mycompany.exp2_s4.ui.*;

import java.io.IOException;
import java.util.Scanner;


public class Exp2_S4 {
    public static void main(String[] args) {
        
        Biblioteca bibModel = new Biblioteca();
        ServiciosBiblioteca svc = new ServiciosBiblioteca(bibModel);
        
        // Cargar datos iniciales
        try {
            svc.cargarDatos();

        } catch (IOException e) {
            System.err.println("ERROR al cargar datos: " + e.getMessage());
            return;
        }

        // Iniciar el menú principal
        new MenuPrincipal(svc, new Scanner(System.in)).iniciar();
        
    }

}