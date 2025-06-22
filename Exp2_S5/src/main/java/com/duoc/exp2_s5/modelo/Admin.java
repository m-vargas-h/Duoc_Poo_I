package com.duoc.exp2_s5.modelo;

public class Admin {
    public enum Role { ADMIN, ASISTENTE }

    private String rut;
    private String nombre;
    private String password;
    private Role role;

    public Admin(String rut, String nombre, String password, Role role) {
        this.rut      = rut;
        this.nombre   = nombre;
        this.password = password;
        this.role     = role;
    }

    public String getRut() { 
        return rut; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public Role getRole() { 
        return role; 
    }
    
    public boolean checkPassword(String pw) {
        return this.password.equals(pw);
    }

    @Override
    public String toString() {
        return nombre + " (" + rut + ") [" + role + "]";
    }

}