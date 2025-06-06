
package com.duoc.exp1_s2.modelo;

public class Cliente {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private CuentaBancaria cuenta;
    private String tipoCuenta; //Almacena el tipo de cuenta para funciones de persistencia

    // Constructor
    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String domicilio, String comuna, String telefono, CuentaBancaria cuenta) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuenta = cuenta;
        if (cuenta != null) {
            this.tipoCuenta = cuenta.getTipoCuenta();
        }

    }

    //! Constructor para cuando no se asigna la cuenta inicialmente, solo para pruebas
    ////public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, 
    ////               String domicilio, String comuna, String telefono) {
    ////    this(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, null);
    ////}

    public void mostrarDatos() {
        System.out.println("\nCliente: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);
        System.out.println("RUT: " + rut);
        System.out.println("Domicilio: " + domicilio + ", " + comuna);
        System.out.println("Teléfono: " + telefono);
        if (cuenta != null) {
            cuenta.mostrarCuenta();
            System.out.println("Tipo de cuenta: " + tipoCuenta);
        } else {
            System.out.println("Cuenta: No asignada");
        }

    }

    // getters y setters
    public String getRut() { 
        return rut; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public String getTelefono() { 
        return telefono; 
    }

    public CuentaBancaria getCuenta() { 
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

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

}
