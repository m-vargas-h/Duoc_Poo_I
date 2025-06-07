
package com.duoc.exp1_s3.modelo;

public class CuentaCorriente extends CuentaBancaria {
    private static final int SOBREGIRO_MAXIMO = 500000; // Linea de sobregiro por defecto
    
    public CuentaCorriente() {
        super();
    }
    
    public CuentaCorriente(int saldoInicial) {
        super(saldoInicial);
    }

    // Constructor de restauración: se recibe el número de cuenta guardado.
    public CuentaCorriente(String numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }
    
    @Override
    protected String getTipoCuenta() {
        return "03";
    }

    @Override
    public String getNombreTipoCuenta() {
        return "Cuenta Corriente";
    }
    
    @Override
    public void depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso en Cuenta Corriente. Saldo actual: $" + saldo);
        } else {
            System.out.println("El monto debe ser mayor a cero.");
        }
    }
    
    @Override
    public void girar(int monto) {
        //? Depuracion para verificar el correcto funcionamiento
        ////System.out.println("imprime 03");
        
        if (monto > 0 && (saldo - monto) >= -SOBREGIRO_MAXIMO) {
            saldo -= monto;
            System.out.println("Giro exitoso en Cuenta Corriente. Saldo actual: $" + saldo);
        } else {
            System.out.println("Monto inválido o excede el límite de sobregiro de $" + SOBREGIRO_MAXIMO);
        }
    }
}
