package com.duoc.exp3_s7.ui;

import com.duoc.exp3_s7.model.ListaPrimos;
import com.duoc.exp3_s7.utils.PersistenciaInfo;
import com.duoc.exp3_s7.utils.UtilesPrimos;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner entrada = new Scanner(System.in);
    private ListaPrimos listaActual = null;
    private int inicioRango = 0, finRango = 0;
    private final int cantidadHilos = 4;

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Contar números primos en un rango");
            System.out.println("2. Verificar número primo");
            System.out.println("3. Guardar conteo de números primos");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1 -> contarPrimos();
                case 2 -> verificarNumero();
                case 3 -> guardarLista();
                case 4 -> System.out.println("¡Hasta pronto!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private void contarPrimos() {
        System.out.print("Desde: ");
        inicioRango = entrada.nextInt();
        System.out.print("Hasta: ");
        finRango = entrada.nextInt();

        try {
            listaActual = UtilesPrimos.generarListaPrimos(inicioRango, finRango, cantidadHilos);
            System.out.printf("Se encontraron %d números primos entre %d y %d.\n",
                    listaActual.contarPrimos(), inicioRango, finRango);
        } catch (InterruptedException e) {
            System.err.println("Error al contar primos: " + e.getMessage());
        }
    }

    private void verificarNumero() {
        System.out.print("Número a verificar: ");
        int numero = entrada.nextInt();
        boolean esPrimo = new ListaPrimos().esPrimo(numero);
        System.out.println(numero + (esPrimo ? " es primo." : " no es primo."));
    }

    private void guardarLista() {
        if (listaActual == null || listaActual.isEmpty()) {
            System.out.println("No hay lista generada aún. Usa primero la opción 1.");
            return;
        }

        try {
            PersistenciaInfo.guardarListaRango(listaActual, inicioRango, finRango);
        } catch (Exception e) {
            System.err.println("No se pudo guardar el archivo: " + e.getMessage());
        }
    }
}