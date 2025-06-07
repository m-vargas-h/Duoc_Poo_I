
package com.duoc.exp1_s3.modelo;

import com.duoc.exp1_s3.interfaces.Interes;
import com.duoc.exp1_s3.interfaces.Operaciones;

public class CuentaCorriente extends CuentaBancaria implements Operaciones, Interes{

    private static final int SOBREGIRO_MAXIMO = 500000; // Linea de sobregiro por defecto
    private static final double TASA_SOBREGIRO = 0.07;  // Tasa de interés para el sobregiro (7%)

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

        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a cero.");
            return;
        }
    
        // Giro normal si el monto solicitado es menor o igual que el saldo disponible:
        if (monto <= saldo) {
            saldo -= monto;
            System.out.println("Giro exitoso en Cuenta Corriente. Saldo actual: $" + saldo);
        } else {
            // Se requiere utilizar el sobregiro:
            int montoSobregiro = monto - saldo;
        
            // Verificar que el monto en sobregiro no exceda el límite permitido
            if (montoSobregiro > SOBREGIRO_MAXIMO) {
                System.out.println("El monto en sobregiro excede el límite permitido de $" + SOBREGIRO_MAXIMO);
                return;
            }
        
            // Se llama al método de la interfaz para calcular el interés sobre el monto de sobregiro
            double interes = calcularInteres(montoSobregiro);
        
            /*
             * Se actualiza el saldo para reflejar el giro total, incluyendo el interés aplicado, por ejemplo
             * si el saldo el $100 y se solicita $150, entonces el sobregiro sera $50. Entonces se calcula el
             * interés del 7%, quedando 50 * 0,07 = 3,5
             * En este caso el saldo seria negativo, y el monto seria $53 (el sobregiro mas el interés)
             */
      
            saldo = saldo - monto - (int) Math.round(interes);
        
            System.out.println("Se ha utilizado la línea de sobregiro en la Cuenta Corriente.");
            System.out.println("Monto de sobregiro: $" + montoSobregiro);
            System.out.println("Interés aplicado sobre el sobregiro: $" + String.format("%.2f", interes));
            System.out.println("Nuevo saldo (incluyendo sobregiro e interés): $" + saldo);
        }
    }

    @Override
    public double calcularInteres(int monto) {
        if (monto > 0) {
            double interes = monto * TASA_SOBREGIRO;
            return interes;
        }
        return 0;
    }




}
