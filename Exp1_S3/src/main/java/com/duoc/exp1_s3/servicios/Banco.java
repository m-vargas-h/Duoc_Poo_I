
package com.duoc.exp1_s3.servicios;

import com.duoc.exp1_s3.utilidades.Auxiliares;
import com.duoc.exp1_s3.persistencia.PersistenciaInfo;
import com.duoc.exp1_s3.interfaces.Operaciones;
import com.duoc.exp1_s3.modelo.Cliente;
import java.util.ArrayList;
import java.util.Scanner;

public class Banco {
    private ArrayList<Cliente> clientes;
    private PersistenciaInfo persistencia;

    public Banco() {
        this.clientes = new ArrayList<>();
        this.persistencia = new PersistenciaInfo();
    }

    // Para sincronizar la lista de clientes, se usa el método setClientes
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void registrarCliente(Scanner sc) {
        ServiciosCliente servicioCliente = new ServiciosCliente();
    
        // Usamos la lista interna de clientes, en vez de cargarla de persistencia cada vez
        Cliente nuevoCliente = servicioCliente.registrarCliente(sc, this.clientes);
        if (nuevoCliente != null) {
            // Se agrega a la lista interna y se guarda en CSV.
            this.clientes.add(nuevoCliente);
            persistencia.guardarCliente(nuevoCliente);
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
        System.out.print("Ingrese RUT (sin puntos y con guion ej. 7133123-6): ");
        String rutSinFormato = sc.nextLine();

        // Utiliza el método de utilidades para agregar los puntos.
        String rutFormateado = Auxiliares.formatearRut(rutSinFormato);

        Cliente cliente = buscarCliente(rutFormateado);

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

    // Método para ver datos de un cliente
    public void verDatosCliente(Scanner sc) {
        Cliente cliente = buscarClientePorRut(sc);
        if (cliente != null) {
            cliente.mostrarDatos();
        }
    }

    // Método para realizar un depósito
    public void realizarDeposito(Scanner sc) {
        Cliente clienteDep = buscarClientePorRut(sc);
        if (clienteDep != null) {
            System.out.print("\nIngrese monto a depositar: ");
            int monto = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea pendiente
            ((Operaciones) clienteDep.getCuenta()).depositar(monto);
            persistencia.guardarSaldoCuenta(clienteDep.getCuenta());
        }
    }

    // Método para realizar un giro
    public void realizarGiro(Scanner sc) {
        Cliente clienteGir = buscarClientePorRut(sc);
        if (clienteGir != null) {
            //? Depuración para confirmar el tipo de cuenta
            ////System.out.println("La cuenta del cliente es: " + clienteGir.getCuenta().getClass().getSimpleName());

            System.out.print("\nIngrese monto a girar: ");
            int monto = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea pendiente
            ((Operaciones) clienteGir.getCuenta()).girar(monto);
            persistencia.guardarSaldoCuenta(clienteGir.getCuenta());
        }
    }

    // Método para consultar el saldo
    public void consultarSaldo(Scanner sc) {
        Cliente clienteSal = buscarClientePorRut(sc);
        if (clienteSal != null) {
            clienteSal.getCuenta().mostrarCuenta();
        }
    }

}
