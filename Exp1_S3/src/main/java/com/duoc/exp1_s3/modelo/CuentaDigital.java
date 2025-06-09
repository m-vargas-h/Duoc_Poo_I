
package com.duoc.exp1_s3.modelo;

import com.duoc.exp1_s3.interfaces.Operaciones;

public class CuentaDigital extends CuentaBancaria implements Operaciones {

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

    // Se asigna el código "01" para identificar a la Cuenta Digital.
    @Override
    protected String getTipoCuenta() {

        return "01";

    }

    @Override
    public String getNombreTipoCuenta() {

        return "Cuenta Digital";

    }

    
    @Override
    public void depositar(int monto) {

        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso en Cuenta Digital. Saldo actual: $" + saldo);
        } else {
            System.out.println("El monto debe ser mayor a cero.");
        }

    }
    
    @Override
    public void girar(int monto) {

        //? Depuración para verificar funcionamiento
        ////System.out.println("Ejecutando giro en (1): " + this.getClass().getSimpleName());
        
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            System.out.println("Giro exitoso en Cuenta Digital. Saldo actual: $" + saldo);
        } else {
            System.out.println("Monto inválido o fondos insuficientes en Cuenta Digital.");
        }

    }
    
}
