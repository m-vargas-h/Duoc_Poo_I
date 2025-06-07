
package com.duoc.exp1_s3.modelo;

public class CuentaAhorro extends CuentaBancaria {
    private int limiteGiros;
    private int girosRealizados;
    private int girosRestantes; 
    
    //! Constructor con limite de giros, por ahora los giros están definidos de forma fija en 3
    public CuentaAhorro(int limiteGiros) {
        super();
        this.girosRestantes = limiteGiros;
        this.girosRealizados = 0;
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
    
    //? (Opcional, sin uso de momento) Método para consultar la cantidad de giros restantes.
    public int getGirosRestantes() {
        return girosRestantes;
    }

}

