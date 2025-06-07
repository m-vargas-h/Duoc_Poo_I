
package com.duoc.exp1_s3.modelo;

public class CuentaDigital extends CuentaBancaria {

    public CuentaDigital() {
        super();
    }
    
    public CuentaDigital(int saldoInicial) {
        super(saldoInicial);
    }

    // Constructor de restauración: se recibe el número de cuenta guardado.
    public CuentaDigital(String numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }

    // Se asigna el código "01" para identificar a la Cuenta Vista.
    @Override
    protected String getTipoCuenta() {
        return "01";
    }

    @Override
    public String getNombreTipoCuenta() {
        return "Cuenta Vista";
    }

    
    @Override
    public void depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso en Cuenta Vista. Saldo actual: $" + saldo);
        } else {
            System.out.println("El monto debe ser mayor a cero.");
        }
    }
    
    @Override
    public void girar(int monto) {
        //? Depuracion para verificar funcionamiento
        ////System.out.println("Ejecutando giro en (1): " + this.getClass().getSimpleName());
        
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            System.out.println("Giro exitoso en Cuenta Vista. Saldo actual: $" + saldo);
        } else {
            System.out.println("Monto inválido o fondos insuficientes en Cuenta Vista.");
        }
    }
}
