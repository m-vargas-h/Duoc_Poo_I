
package com.duoc.exp1_s3.modelo;

import com.duoc.exp1_s3.interfaces.Interes;
import com.duoc.exp1_s3.interfaces.Operaciones;

public class CuentaAhorro extends CuentaBancaria implements Operaciones, Interes {

    //private int girosRealizados;
    private int girosRestantes; 

    // Tasa de interés anual, fijada en 5% para el desarrollo de esta entrega
    private static final double TASA_ANUAL = 0.05;

    //! Constructor con limite de giros, por ahora el limite es 3 y se reinicia en cada ejecución
    public CuentaAhorro(int limiteGiros) {

        super();
        this.girosRestantes = limiteGiros;
        //this.girosRealizados = 0;

    }
    
    // Constructor de restauración, en caso de recuperar datos guardados
    public CuentaAhorro(String numeroCuenta, int saldo, int girosRestantes) {

        super(numeroCuenta, saldo);
        this.girosRestantes = girosRestantes;

    }
    
    @Override
    protected String getTipoCuenta() {

        //? Depuración para confirmar que se ejecuta este método.
        ////System.out.println("imprime 02 porfa");

        return "02";

    }

    @Override
    public String getNombreTipoCuenta() {

        return "Cuenta Ahorro";

    }
    
    @Override
    public void depositar(int monto) {

        if(monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso en Cuenta Ahorro. Saldo actual: $" + saldo);

            // Llamar al método de la interfaz para calcular el interés generado por este depósito
            calcularInteres(monto);
    
        } else {

            System.out.println("El monto debe ser mayor a cero.");

        }

    }
    
    @Override
    public void girar(int monto) {

        //? Depuración para verificar funcionamiento
        ////System.out.println("Ejecutando giro en (2): " + this.getClass().getSimpleName());
        
        // Validamos monto positivo y saldo suficiente
        if(monto > 0 && saldo >= monto) {
            // Verificamos que queden giros disponibles
            if(girosRestantes > 0) {
                saldo -= monto;
                girosRestantes--;
                System.out.println("Giro exitoso en Cuenta Ahorro. Saldo actual: $" + saldo 
                                   + ". Giros restantes: " + girosRestantes);
            } else {

                System.out.println("No tiene giros restantes para efectuar la operación.");
            }

        } else {

            System.out.println("Monto inválido o saldo insuficiente en Cuenta Ahorro.");
        }

    }

    @Override
    public double calcularInteres(int monto) {

        double tasaDiaria = TASA_ANUAL / 365.0;
        double montoCompuesto = monto * Math.pow(1 + tasaDiaria, 30);
        double interes = montoCompuesto - monto;

        System.out.println("Interés generado a 30 días: $" + String.format("%.2f", interes));
        return interes;

    }

    //? (Opcional, sin uso de momento) Método para consultar la cantidad de giros restantes.
    public int getGirosRestantes() {

        return girosRestantes;

    }

}
