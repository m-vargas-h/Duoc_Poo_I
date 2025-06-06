
package com.duoc.exp1_s1;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class Banco {
    private ArrayList<Cliente> clientes;
    //private Scanner sc;

    public Banco() {
        this.clientes = new ArrayList<>();
        //this.sc = new Scanner(System.in);
    }

    public void registrarCliente(Scanner sc) {
        Cliente nuevoCliente = Cliente.registrarCliente(sc);
        if (nuevoCliente != null) {
            clientes.add(nuevoCliente);
            guardarClienteCSV(nuevoCliente);  // Aquí se guarda en el CSV
        }
    }

    public Cliente buscarCliente(String rut) {
        for (Cliente c : clientes) {
            if (c.getRut().equals(rut)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarClientePorRut(Scanner sc) {
        System.out.print("Ingrese RUT: ");
        String rut = sc.nextLine();
        Cliente cliente = buscarCliente(rut);
    
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
        }
    
        return cliente;
    }

    public void mostrarClientes() {
        for (Cliente c : clientes) {
            c.mostrarDatos();
        }
    }

    public void guardarClienteCSV(Cliente cliente) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("clientes.csv", true))) {
            pw.println(cliente.getRut() + "," +
                        cliente.getNombre() + "," +
                        cliente.getApellidoPaterno() + "," +
                        cliente.getApellidoMaterno() + "," +
                        cliente.getDomicilio() + "," +
                        cliente.getComuna() + "," +
                        cliente.getTelefono() + "," +
                        cliente.getCuenta().getNumeroCuenta());
        } catch (IOException e) {
            System.out.println("Error al guardar el cliente en el archivo CSV.");
        }
    }

    public void cargarSaldosCSV() {
        HashMap<String, Integer> saldos = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("saldos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                saldos.put(datos[0], Integer.parseInt(datos[1]));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los saldos.");
        }

        // Asignar los saldos a las cuentas ya cargadas
        for (Cliente cliente : clientes) {
            String numCuenta = cliente.getCuenta().getNumeroCuenta();
            if (saldos.containsKey(numCuenta)) {
                cliente.getCuenta().setSaldo(saldos.get(numCuenta));
            }
        }
    }

    public void cargarClientesCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                // Crear un nuevo cliente con los datos del archivo CSV
                Cliente cliente = new Cliente(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);

                // Agregar el cliente a la lista
                clientes.add(cliente);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar clientes desde el archivo CSV.");
        }
    }

    public void guardarSaldoCuenta(Cuenta cuenta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("saldos.csv", true))) {
            pw.println(cuenta.getNumeroCuenta() + "," + cuenta.getSaldo());
        } catch (IOException e) {
            System.out.println("Error al guardar el saldo.");
        }
    }
}

