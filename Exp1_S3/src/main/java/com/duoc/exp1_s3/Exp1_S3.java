
package com.duoc.exp1_s3;

import com.duoc.exp1_s3.persistencia.PersistenciaInfo;
import com.duoc.exp1_s3.utilidades.Auxiliares;
import com.duoc.exp1_s3.servicios.Banco;
import com.duoc.exp1_s3.modelo.Cliente;
import java.util.ArrayList;
import java.util.Scanner;

public class Exp1_S3 {
    public static void main(String[] args) {
        Banco banco = new Banco();

        PersistenciaInfo persistencia = new PersistenciaInfo();  // Instancia de clase
        ArrayList<Cliente> clientes = persistencia.cargarClientes();  // Llamada al método de instancia
        persistencia.cargarSaldos(clientes);

        // Sincroniza la lista de clientes cargados con el objeto banco
        banco.setClientes(clientes); // Considera agregar este método en Banco

        Scanner sc = new Scanner(System.in);
        int opcion;

        System.out.println("--- BANK BOSTON ---");
        System.out.println("Bienvenido a nuestro sistema, para navegar en nuestro sistema");
        System.out.println("seleccione alguna de las opciones disponibles en el menu");

        do {
            Auxiliares.mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: //Registrar cliente
                    banco.registrarCliente(sc);
                    break;

                case 2: //Ver datos de cliente
                    banco.verDatosCliente(sc);
                    break;

                case 3: // Realizar un depósito
                    banco.realizarDeposito(sc);
                    break;

                case 4: // Realizar un giro
                    banco.realizarGiro(sc);
                    break;

                case 5: //Consulta de saldo
                    banco.consultarSaldo(sc);
                    break;

                case 6: //Salir
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 6);

        sc.close();
    }

}
