package com.duoc.exp2_s6.modelo.productos;

import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.enums.EstadoProducto;
import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import java.util.Objects;

public class NovelaGrafica extends Producto implements ConvertirCsv {
    public static final String CODIGO_TIPO = "002"; // Código del tipo de producto Novela Gráfica
    private final String autor;
    private final int fechaPublicacion;

    public NovelaGrafica(String id, String titulo, double precio, int stock, String autor, int fechaPublicacion) {
        super(id, titulo, precio, stock);
        this.autor = Objects.requireNonNull(autor);
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String getTipo() {
        return "NovelaGrafica";
    }

    @Override
    public String getCodigoTipo() {
        return CODIGO_TIPO;
    }


    public String getAutor() {
        return autor;
    }

    public int getFechaPublicacion() {
        return fechaPublicacion;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Autor: %s | Año: %d", autor, fechaPublicacion);
    }

     @Override
    public String toCsvLine() {
        return super.toCsvLine()
             + "," + autor
             + "," + fechaPublicacion;
    }

    public static NovelaGrafica fromCsvTokens(String[] tokens) {
        String id       = tokens[0];
        String titulo   = tokens[2];
        double precio   = Double.parseDouble(tokens[3]);
        int stock       = Integer.parseInt(tokens[4]);
        EstadoProducto est = EstadoProducto.valueOf(tokens[5]);
        String autor    = tokens[6];
        int año         = Integer.parseInt(tokens[7]);

        NovelaGrafica ng = new NovelaGrafica(id, titulo, precio, stock, autor, año);
        ng.setEstado(est);
        return ng;
    }


}