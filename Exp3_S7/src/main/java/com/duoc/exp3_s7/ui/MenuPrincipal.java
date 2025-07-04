package com.duoc.exp3_s7.ui;

import com.duoc.exp3_s7.model.ListaPrimos;
import com.duoc.exp3_s7.threads.HiloConteoPrimos;
import com.duoc.exp3_s7.utils.PersistenciaInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner entrada = new Scanner(System.in);
    private ListaPrimos listaActual = null;
    private int inicioRango = 0, finRango = 0;
    private final int cantidadHilos = 4;
    private HiloConteoPrimos hiloActual = null;
    private final List<String> mensajesPendientes = new ArrayList<>();
    private static final int LIMITE_MAXIMO = 2_000_000_000;

    public void mostrar() {
        int opcion;

        do {
            // Mostrar mensajes pendientes
            synchronized (mensajesPendientes) {
                if (!mensajesPendientes.isEmpty()) {
                    for (String msg : mensajesPendientes) {
                        System.out.println("\n" + msg);
                    }
                    mensajesPendientes.clear();
                }
            }

            System.out.println("\n--- MENÚ PRINCIPAL ---");

            if (hiloActual != null && !hiloActual.estaListo()) {
                System.out.println("⚙️  Conteo en curso… puedes seguir usando el sistema.");
            }

            System.out.println("1. Contar números primos en un rango");
            System.out.println("2. Verificar número primo");
            System.out.println("3. Guardar conteo de números primos");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

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
        entrada.nextLine();

        if (inicioRango < 0 || finRango < 0 || finRango < inicioRango) {
            System.out.println("⚠️  Rango no válido. Asegúrate de que ambos valores sean positivos y el 'Hasta' sea mayor que el 'Desde'.");
            return;
        }

        long cantidadNumeros = (long) finRango - inicioRango + 1;
        if (cantidadNumeros > LIMITE_MAXIMO) {
            System.out.println("⚠️  El rango excede el límite máximo de " + LIMITE_MAXIMO + " números.");
            return;
        }

        hiloActual = new HiloConteoPrimos(inicioRango, finRango, cantidadHilos, () -> {
            listaActual = hiloActual.getResultado();
            synchronized (mensajesPendientes) {
                mensajesPendientes.add("✔️ Conteo finalizado: " + listaActual.contarPrimos()
                        + " números primos entre " + inicioRango + " y " + finRango + ".");
            }
        });

        hiloActual.start();
        System.out.println("Conteo de primos en segundo plano iniciado.");
    }

    private void verificarNumero() {
        System.out.print("Número a verificar: ");
        int numero = entrada.nextInt();
        entrada.nextLine();

        boolean esPrimo = new ListaPrimos().esPrimo(numero);
        System.out.println(numero + (esPrimo ? " es primo." : " no es primo."));
    }

    private void guardarLista() {
        if (hiloActual == null) {
            System.out.println("No se ha iniciado ningún conteo aún.");
            return;
        }

        if (!hiloActual.estaListo()) {
            System.out.println("Aún se está generando la lista. Intenta nuevamente más tarde.");
            return;
        }

        listaActual = hiloActual.getResultado();
        if (listaActual == null || listaActual.isEmpty()) {
            System.out.println("No se encontraron números primos en el rango dado.");
            return;
        }

        System.out.println("Guardando lista en segundo plano...");

        Thread hiloGuardado = new Thread(() -> {
            try {
                String rutaFinal = PersistenciaInfo.guardarListaRango(listaActual, inicioRango, finRango);
                synchronized (mensajesPendientes) {
                    mensajesPendientes.add("📁 Archivo guardado en: " + rutaFinal);
                }
            } catch (IOException e) {
                synchronized (mensajesPendientes) {
                    mensajesPendientes.add("❌ Error al guardar: " + e.getMessage());
                }
            }
        });

        hiloGuardado.start();
    }
}