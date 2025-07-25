
package com.duoc.exp1_s3.modelo;

public class Cliente {

    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String calle;
    private String numCalle;

    private String comuna;
    private String telefono;
    private CuentaBancaria cuenta;
    private String tipoCuenta;

    // Constructor
    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String calle,  String numCalle, String comuna, String telefono, CuentaBancaria cuenta) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.calle = calle;
        this.numCalle = numCalle;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuenta = cuenta;
        this.tipoCuenta = cuenta.getNombreTipoCuenta();

    }

    //! Constructor para cuando no se asigna la cuenta inicialmente, solo para pruebas
    ////public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, 
    ////               String domicilio, String comuna, String telefono) {
    ////    this(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, null);
    ////}

    public void mostrarDatos() {
        System.out.println("\nCliente: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);
        System.out.println("RUT: " + rut);
        System.out.println("Domicilio: " + calle +  " #" + numCalle + ", " + comuna);
        System.out.println("Teléfono: " + telefono);
        if (cuenta != null) {
            cuenta.mostrarCuenta();
            System.out.println("Tipo de cuenta: " + cuenta.getNombreTipoCuenta());
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

    public String getCalle() {
        return calle;
    }

    public String getNumCalle(){
        return numCalle;
    }

    public String getComuna() {
        return comuna;
    }

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumeroCalle(String numCalle) {
        this.numCalle = numCalle;
    }

}
