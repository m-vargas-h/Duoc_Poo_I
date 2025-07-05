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

            System.out.println("\n------------- MENÚ PRINCIPAL -------------");

            // Verificar si hay un conteo en curso, y si no está listo, informar al usuario
            if (hiloActual != null && !hiloActual.estaListo()) {
                System.out.println("[SISTEMA] Conteo en curso, puedes seguir usando el sistema.");
            }

            System.out.println("1. Contar números primos en un rango");
            System.out.println("2. Verificar número primo");
            System.out.println("3. Guardar conteo de números primos");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    contarPrimos();
                    break;

                case 2:
                    verificarNumero();
                    break;

                case 3:
                    guardarLista();
                    break;

                case 4:
                    System.out.println("¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 4);
    }

    // Método para contar números primos en un rango dado
    // Utiliza un hilo para realizar el conteo en segundo plano
    private void contarPrimos() {
        System.out.print("Desde: ");
        inicioRango = entrada.nextInt();
        System.out.print("Hasta: ");
        finRango = entrada.nextInt();
        entrada.nextLine();

        // Validar que los valores de inicio y fin sean positivos y que el rango sea válido
        if (inicioRango < 0 || finRango < 0 || finRango < inicioRango) {
            System.out.println(" [VALIDACIÓN] Rango no válido. Asegúrate de que ambos valores sean positivos y el 'Hasta' sea mayor que el 'Desde'.");
            return;
        }

        // Validar que el rango no exceda el límite máximo
        long cantidadNumeros = (long) finRango - inicioRango + 1;
        if (cantidadNumeros > LIMITE_MAXIMO) {
            System.out.println("[VALIDACIÓN] El rango excede el límite máximo de " + LIMITE_MAXIMO + " números.");
            return;
        }

        // Si ya hay un hilo en ejecución, detenerlo antes de iniciar uno nuevo
        hiloActual = new HiloConteoPrimos(inicioRango, finRango, cantidadHilos, () -> {
            listaActual = hiloActual.getResultado();
            synchronized (mensajesPendientes) {
                mensajesPendientes.add("[SISTEMA] Conteo finalizado: " + listaActual.contarPrimos()
                        + " números primos entre " + inicioRango + " y " + finRango + ".");
            }
        });

        // Iniciar el hilo de conteo
        hiloActual.start();
        System.out.println("[SISTEMA] Conteo de primos en segundo plano iniciado.");
    }

    // Método para verificar si un número es primo
    private void verificarNumero() {
        System.out.print("Número a verificar: ");
        int numero = entrada.nextInt();
        entrada.nextLine();

        boolean esPrimo = new ListaPrimos().esPrimo(numero);
        System.out.println(numero + (esPrimo ? " es primo." : " no es primo."));
    }

    // Método para guardar la lista de números primos generada por el hilo actual
    private void guardarLista() {
        if (hiloActual == null) {
            System.out.println("No se ha iniciado ningún conteo aún.");
            return;
        }

        // Verificar si el hilo actual ha terminado su trabajo
        if (!hiloActual.estaListo()) {
            System.out.println("[SISTEMA] Aún se está generando la lista. Intenta nuevamente más tarde.");
            return;
        }

        // Verificar si la lista de números primos está vacía
        listaActual = hiloActual.getResultado();
        if (listaActual == null || listaActual.isEmpty()) {
            System.out.println("No se encontraron números primos en el rango dado.");
            return;
        }

        System.out.println("[SISTEMA] Guardando lista en segundo plano...");

        // Iniciar un hilo para guardar la lista de números primos en un archivo
        // Utiliza la clase PersistenciaInfo para manejar la persistencia
        Thread hiloGuardado = new Thread(() -> {
            try {
                String rutaFinal = PersistenciaInfo.guardarListaRango(listaActual, inicioRango, finRango);
                synchronized (mensajesPendientes) {
                    mensajesPendientes.add("[SISTEMA] Archivo guardado en: " + rutaFinal);
                }
            } catch (IOException e) {
                synchronized (mensajesPendientes) {
                    mensajesPendientes.add("[VALIDACIÓN] Error al guardar: " + e.getMessage());
                }
            }
        });

        // Iniciar el hilo de guardado
        hiloGuardado.start();
    }
}