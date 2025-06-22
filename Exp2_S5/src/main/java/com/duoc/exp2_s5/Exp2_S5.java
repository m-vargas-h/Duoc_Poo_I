
package com.duoc.exp2_s5;

import com.duoc.exp2_s5.servicio.ServiciosBiblioteca;
import com.duoc.exp2_s5.servicio.Biblioteca;
import com.duoc.exp2_s5.ui.*;

import java.io.IOException;
import java.util.Scanner;


public class Exp2_S5 {
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