package com.duoc.exp2_s6.modelo.base;

import java.util.Objects;
import com.duoc.exp2_s6.modelo.enums.EstadoProducto;
import com.duoc.exp2_s6.interfaces.ConvertirCsv;

public abstract class Producto implements ConvertirCsv {
    private String id;
    private String titulo;
    private double precio;
    private int stock;
    private EstadoProducto estado;

    protected Producto(String id, String titulo, double precio, int stock) {
        this.id     = Objects.requireNonNull(id);
        this.titulo = Objects.requireNonNull(titulo);
        this.precio = precio;
        this.stock  = stock;
        this.estado = EstadoProducto.DISPONIBLE; // Estado por defecto
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

    public EstadoProducto getEstado() { 
        return estado; 
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

    public void setEstado(EstadoProducto estado) {
        this.estado = Objects.requireNonNull(estado, "Estado no puede ser nulo");
    }

    // ----------------------------- MÉTODOS ABSTRACTOS PARA POLIMORFISMO ----------------------------------------------

    // devuelve el tipo de producto como String
    // por ejemplo: "Comic", "TCG", etc. 
    public abstract String getTipo();

    // devuelve el código del tipo de producto
    public abstract String getCodigoTipo();


    @Override
    public String toString() {
        return String.format("[%s] %s (%s) $%.2f | Stock: %d",
            id, titulo, getTipo(), precio, stock);
    }

    /** Primera parte de la línea CSV: id,tipo,titulo,precio,stock,estado */
    @Override
    public String toCsvLine() {
        return String.join(",",
            getId(),
            getTipo(),
            getTitulo(),
            String.valueOf(getPrecio()),
            String.valueOf(getStock()),
            getEstado().name()
        );
    }

}