
package com.duoc.exp1_s2.servicios;

import com.duoc.exp1_s2.utilidades.Auxiliares;
import com.duoc.exp1_s2.modelo.CuentaVista;
import com.duoc.exp1_s2.modelo.CuentaBancaria;
import com.duoc.exp1_s2.modelo.CuentaCorriente;
import com.duoc.exp1_s2.modelo.Cliente;
import com.duoc.exp1_s2.modelo.CuentaAhorro;

import java.util.ArrayList;
import java.util.Scanner;

public class ServiciosCliente {

    public Cliente registrarCliente(Scanner sc, ArrayList<Cliente> clientesExistentes) {
        System.out.println("\n---- Registro de Cliente ----");
        
        // Lectura y validación del RUT
        System.out.print("Ingrese RUT (sin puntos y con guion verificador, ej. 7533234-7): ");
        String rutSinFormato = sc.nextLine();
        if (!rutSinFormato.matches("\\d{7,8}-[\\dkK]")) {
            System.out.println("El RUT debe ingresarse sin puntos siguiendo el formato, por ejemplo: 19133090-0.");
            return null;
        }
        String rut = Auxiliares.formatearRut(rutSinFormato);

        // **Verificar si el cliente ya existe**
        for (Cliente cliente : clientesExistentes) {
            if (cliente.getRut().equals(rut)) {
                System.out.println("Error: Este RUT ya está registrado. No se pueden crear múltiples cuentas.");
                return null;
            }
        }
        
        // Captura de otros datos personales
        System.out.print("Ingrese Nombre: ");
        String nombre = sc.nextLine();
        
        System.out.print("Ingrese Apellido Paterno: ");
        String apellidoPaterno = sc.nextLine();
        
        System.out.print("Ingrese Apellido Materno: ");
        String apellidoMaterno = sc.nextLine();
        
        System.out.print("Ingrese Domicilio: ");
        String domicilio = sc.nextLine();
        
        System.out.print("Ingrese Comuna: ");
        String comuna = sc.nextLine();
        
        // Solicitud y validación del teléfono
        String telefono;
        do {
            System.out.print("Ingrese teléfono (solo los 8 dígitos): ");
            telefono = Auxiliares.formatearTelefono(sc.nextLine());
        } while (telefono == null);
        
        // Seleccionar el tipo de cuenta a crear
        Auxiliares.menuCuentas();
        int opcionCuenta = sc.nextInt();
        sc.nextLine();
        
        CuentaBancaria cuenta;
        switch (opcionCuenta) {
            case 1:
                cuenta = new CuentaVista();
                break;
            case 2:
                // Se define un límite de giros, por ejemplo 3 giros permitidos.
                cuenta = new CuentaAhorro(3);
                break;
            case 3:
                cuenta = new CuentaCorriente();
                break;
            default:
                System.out.println("Opción inválida. Se asigna Cuenta Vista por defecto.");
                cuenta = new CuentaVista();
        }
        
        // Creación del objeto Cliente utilizando la cuenta construida
        Cliente nuevoCliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, 
                                           telefono, cuenta);
        
        System.out.println("Cuenta generada: " + nuevoCliente.getCuenta().getNumeroCuenta());
        System.out.println("\n¡Cliente registrado exitosamente!\n");
        
        return nuevoCliente;
    }
}
