package com.duoc.exp2_s6.modelo;

import java.util.Objects;

public abstract class Producto {
    private String id;
    private String titulo;
    private double precio;
    private int stock;

    protected Producto(String id, String titulo, double precio, int stock) {
        this.id     = Objects.requireNonNull(id);
        this.titulo = Objects.requireNonNull(titulo);
        this.precio = precio;
        this.stock  = stock;
    }

    public String getId() { 
        return id; 
    }

    public String getTitulo() { 
        return titulo; 
    }

    public double getPrecio() { 
        return precio; 
    }

    public int getStock() { 
        return stock; 
    }

    public void setTitulo(String t) { 
        this.titulo = t; 
    }

    public void setPrecio(double p) { 
        this.precio = p; 
    }

    public void setStock(int s) {
        this.stock = s; 
    }

    // Cada subclase debe implementar este método
    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) $%.2f | Stock: %d",
            id, titulo, getTipo(), precio, stock);
    }
}