
package com.duoc.exp1_s2.modelo;

public abstract class CuentaBancaria {
    protected String numeroCuenta;
    protected int saldo;
    
    private static int contadorCuenta = 00001;
    private static final String COD_SUCURSAL = "25";
    
    // Constructor que recibe saldo inicial
    protected CuentaBancaria(int saldoInicial) {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = saldoInicial;
    }
    
    // Constructor por defecto, saldo inicial en 0
    protected CuentaBancaria() {
        this(0);
    }

    // Constructor de restauración: asigna el número y el saldo sin generar uno nuevo.
    protected CuentaBancaria(String numeroCuenta, int saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }
    
    // Método privado que genera el número de cuenta
    private String generarNumeroCuenta() {
        // getTipoCuenta() será implementado por cada subclase para retornar "01", "02" o "03"
        String generacion = COD_SUCURSAL + getTipoCuenta() + String.format("%05d", contadorCuenta);
        contadorCuenta++; // Incrementa una sola vez aquí.
        return generacion;
    }
    
    // Método abstracto para que cada subclase defina su código de tipo de cuenta.
    protected abstract String getTipoCuenta();
    
    // Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public int getSaldo() {
        return saldo;
    }
    
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public static void setContadorCuenta(int nuevoContador) {
        contadorCuenta = nuevoContador;
    }
    
    // Métodos abstractos para operaciones de depósito y giro.
    public abstract void depositar(int monto);
    public abstract void girar(int monto);
    
    // Método común para mostrar la información de la cuenta.
    public void mostrarCuenta() {
        System.out.println("Cuenta Nº: " + numeroCuenta);
        System.out.println("Saldo: $" + saldo);
    }

    public String obtenerTipoCuenta() {
        return getTipoCuenta();
    }
    
}
