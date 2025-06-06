
package com.duoc.exp1_s1;

import java.util.Scanner;

public class Cliente {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private Cuenta cuenta;

    // Constructor
    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String domicilio, String comuna, String telefono) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuenta = new Cuenta();
    }

    // Método para registrar un cliente
    public static Cliente registrarCliente(Scanner sc) {
        System.out.println("\n---- Registro de Cliente ----");
        System.out.print("Ingrese RUT: ");
        String rut = sc.nextLine();

        // Validación del RUT
        if (rut.length() < 11 || rut.length() > 12) {
            System.out.println("El RUT debe tener entre 11 y 12 caracteres incluyendo puntos y guion.");
            return null;
        }

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

        // Solicitar y validar el teléfono
        String telefono;
        do {
            System.out.print("Ingrese teléfono (solo los 8 dígitos): ");
            telefono = Cliente.formatearTelefono(sc.nextLine());
        } while (telefono == null);


        Cliente nuevoCliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);

        System.out.println("Cuenta generada: " + nuevoCliente.getCuenta().getNumeroCuenta());
        System.out.println("\n¡Cliente registrado exitosamente!\n");

        return nuevoCliente;

    }

    public static String formatearTelefono(String numero) {
        numero = numero.replaceAll("[^0-9]", ""); // Eliminamos caracteres no numéricos

        if (!numero.matches("\\d{8}")) {
            System.out.println("Error: El número debe contener exactamente 8 dígitos.");
            return null; // Devuelve `null` si el formato es incorrecto
        }

        return "+569" + numero; // Agrega el prefijo
    }

    public void mostrarDatos() {
        System.out.println("\nCliente: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);
        System.out.println("RUT: " + rut);
        System.out.println("Domicilio: " + domicilio + ", " + comuna);
        System.out.println("Teléfono: " + telefono);
        cuenta.mostrarCuenta();
    }

    // Otros métodos
    public String getRut() { 
        return rut; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public String getTelefono() { 
        return telefono; 
    }

    public Cuenta getCuenta() { 
        return cuenta; 
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getComuna() {
        return comuna;
    }



}

