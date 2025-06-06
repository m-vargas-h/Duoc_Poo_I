
package com.duoc.exp1_s1;

import java.util.Scanner;

public class Exp1_S1 {

    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.cargarClientesCSV(); // Cargamos la base de datos de clientes registrados
        banco.cargarSaldosCSV(); // Cargamos la base con el saldo actualizado de las cuentas
        Scanner sc = new Scanner(System.in);
        int opcion;

        System.out.println("--- BANK BOSTON ---");
        System.out.println("Bienvenido a nuestro sistema, para navegar en nuestro sistema");
        System.out.println("seleccione alguna de las opciones disponibles en el menu");

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: //Registrar cliente
                    banco.registrarCliente(sc);
                    break;

                case 2: //Ver datos de cliente
                    Cliente cliente = banco.buscarClientePorRut(sc);

                    if (cliente != null) {
                        cliente.mostrarDatos();
                    }
                    break;

                case 3: //Realizar un deposito
                    Cliente clienteDep = banco.buscarClientePorRut(sc);

                    if (clienteDep != null) {
                        System.out.print("Ingrese monto a depositar: ");
                        clienteDep.getCuenta().depositar(sc.nextInt());

                        // Guardar saldo actualizado en el archivo CSV
                        banco.guardarSaldoCuenta(clienteDep.getCuenta());

                    }
                    break;

                case 4: //Realizar un giro
                    Cliente clienteGir = banco.buscarClientePorRut(sc);

                    if (clienteGir != null) {
                        System.out.print("Ingrese monto a girar: ");
                        clienteGir.getCuenta().girar(sc.nextInt());

                        // Guardar saldo actualizado en el archivo CSV
                        banco.guardarSaldoCuenta(clienteGir.getCuenta());

                    }
                    break;

                case 5: //Consulta de saldo
                    Cliente clienteSal = banco.buscarClientePorRut(sc);

                    if (clienteSal != null) {
                        clienteSal.getCuenta().mostrarCuenta();
                    }
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

    public static void mostrarMenu() {
        System.out.println("\n---- Menú Principal ----");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Ver Datos de Cliente");
        System.out.println("3. Depositar");
        System.out.println("4. Girar");
        System.out.println("5. Consultar Saldo");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }

}
