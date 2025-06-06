
package com.duoc.exp1_s1;

public class Cuenta {
    private String numeroCuenta;
    private int saldo;
        
    /* El código de sucursal (COD_SUCURSAL) 00 representa la sucursal principal, a futuro sera posible agregar nuevas sucursales
     * con el fin de identificar donde fue abierta la cuenta, del mismo modo el tipo de cuenta (COD_TIPO_CUENTA) 01 representa la
     * cuenta corriente, después sera posible abrir otros tipos de cuenta como vista o ahorro, esta asignación facilita identificar
     * las cuentas. Por ultimo los otros 5 dígitos de la cuenta (contadorCuenta) aumentaran de forma secuencial cada vez que un nuevo
     * usuario abra una cuenta corriente.
     */
    private static int contadorCuenta = 00001; // La primera cuenta sera registrada con este numero
    private static final String COD_SUCURSAL = "00";
    private static final String COD_TIPO_CUENTA = "01";

        // Método para generar el número de cuenta siguiendo el patrón
    private String generarNumeroCuenta() {
        return COD_SUCURSAL + COD_TIPO_CUENTA + String.format("%05d", contadorCuenta++);
    }

    public Cuenta() {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0; // Saldo inicia en 0
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso. Saldo actual: $" + saldo);
        } else {
            System.out.println("El monto debe ser mayor a cero.");
        }
    }

    public void girar(int monto) {
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            System.out.println("Giro exitoso. Saldo actual: $" + saldo);
        } else {
            System.out.println("Monto inválido o saldo insuficiente.");
        }
    }

    public void mostrarCuenta() {
        System.out.println("Cuenta Nº: " + numeroCuenta);
        System.out.println("Saldo: $" + saldo);
    }


}

