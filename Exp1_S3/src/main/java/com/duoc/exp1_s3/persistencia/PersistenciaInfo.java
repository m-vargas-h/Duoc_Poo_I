
package com.duoc.exp1_s3.persistencia;

import com.duoc.exp1_s3.modelo.CuentaBancaria;
import com.duoc.exp1_s3.modelo.CuentaCorriente;
import com.duoc.exp1_s3.modelo.CuentaDigital;
import com.duoc.exp1_s3.modelo.Cliente;
import com.duoc.exp1_s3.modelo.CuentaAhorro;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;

public class PersistenciaInfo {

    // Se define la ruta donde debe estar la carpeta que almacenara las bases de datos
    private static final Path rutaBase = Paths.get(System.getProperty("user.dir"), "Exp1_S3", "Data");
    private static final String ARCHIVO_CLIENTES = rutaBase.resolve("clientes.csv").toString();
    private static final String ARCHIVO_SALDOS = rutaBase.resolve("saldos.csv").toString();

    // Revisa si la carpeta existe, si no existiera la creara en la ruta indicada
    static {
        if (!Files.exists(rutaBase)) {
            try {
                Files.createDirectories(rutaBase);
                System.out.println("Carpeta 'Data' creada exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al crear la carpeta 'Data': " + e.getMessage());
            }
        }
    }

    // Guarda un cliente en el archivo CSV
    public void guardarCliente(Cliente cliente) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_CLIENTES, true))) {

            pw.println(cliente.getRut() + "," +
                       cliente.getNombre() + "," +
                       cliente.getApellidoPaterno() + "," +
                       cliente.getApellidoMaterno() + "," +
                       cliente.getCalle() + "," +
                       cliente.getNumCalle() + "," +
                       cliente.getComuna() + "," +
                       cliente.getTelefono() + "," +
                       cliente.getCuenta().getNumeroCuenta() + "," +
                       cliente.getCuenta().obtenerTipoCuenta());

        } catch (IOException e) {

            System.out.println("Error al guardar el cliente en el archivo CSV.");
        }

    }

    // Carga la lista de clientes desde el archivo CSV usando el constructor de restauración.
    public ArrayList<Cliente> cargarClientes() {

        ArrayList<Cliente> clientes = new ArrayList<>();
        int maxCuenta = 0; // Para almacenar el número máximo de los últimos 5 dígitos.
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length >= 10) {
                    String rut = datos[0];
                    String nombre = datos[1];
                    String apellidoPaterno = datos[2];
                    String apellidoMaterno = datos[3];
                    String calle = datos[4];
                    String numCalle = datos [5];
                    String comuna = datos[6];
                    String telefono = datos[7];
                    String numCuentaCSV = datos[8];
                    String tipoCuenta = datos[9];

                    // Actualizamos el contador global extrayendo los últimos 5 dígitos del número de cuenta.
                    if (numCuentaCSV.length() >= 9) {
                        String ultimos5 = numCuentaCSV.substring(4);
                        try {
                            int numero = Integer.parseInt(ultimos5);
                            if (numero > maxCuenta) {
                                maxCuenta = numero;
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Error al parsear los dígitos de la cuenta: " + numCuentaCSV);
                        }
                    }

                    // Instancia la cuenta según el tipo, usando el campo 'tipoCuenta'
                    CuentaBancaria cuenta;
                    switch (tipoCuenta) {

                        case "02":
                            // Se deja por defecto 3 giros, los que se reiniciaran en cada ejecución.
                            cuenta = new CuentaAhorro(numCuentaCSV, 0, 3);
                            break;
                        case "03":
                            cuenta = new CuentaCorriente(numCuentaCSV, 0);
                            break;
                        case "01":
                            cuenta = new CuentaDigital(numCuentaCSV, 0);
                        default:
                            cuenta = new CuentaDigital(numCuentaCSV, 0);
                        break;

                    }

                    // Se crea el cliente usando el constructor que recibe la cuenta.
                    Cliente cliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno,
                                                  calle, numCalle, comuna, telefono, cuenta);
                    clientes.add(cliente);

                }

            }

        } catch (IOException e) {
            System.out.println("Error al cargar clientes desde el archivo CSV.");
        }
        // Actualizamos el contador global a maxCuenta + 1
        CuentaBancaria.setContadorCuenta(maxCuenta + 1);
        return clientes;

    }

    // Guarda el saldo de una cuenta en el archivo CSV
    public void guardarSaldoCuenta(CuentaBancaria cuenta) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_SALDOS, true))) {
            pw.println(cuenta.getNumeroCuenta() + "," + cuenta.getSaldo());
        } catch (IOException e) {
            System.out.println("Error al guardar el saldo en el archivo CSV.");
        }

    }

    // Carga saldos desde el archivo CSV y los asigna a las cuentas correspondientes de una lista de clientes.
    public void cargarSaldos(ArrayList<Cliente> clientes) {

        java.util.HashMap<String, Integer> saldos = new java.util.HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_SALDOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    saldos.put(datos[0], Integer.parseInt(datos[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar saldos desde el archivo CSV.");
        }
        
        // Asigna los saldos a las cuentas de los clientes, según el número de cuenta.
        for (Cliente cliente : clientes) {
            String numCuenta = cliente.getCuenta().getNumeroCuenta();
            if (saldos.containsKey(numCuenta)) {
                cliente.getCuenta().setSaldo(saldos.get(numCuenta));
            }

        }

    }
    
}