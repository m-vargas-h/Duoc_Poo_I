package com.duoc.exp2_s6.modelo.productos;

import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.enums.EstadoProducto;
import com.duoc.exp2_s6.modelo.enums.TipoTCG;

import java.util.Objects;



public class TCG extends Producto implements ConvertirCsv {
    public static final String COD_TIPO = "003";

    private final String nombreJuego;
    private final int edadRecomendada;
    private final TipoTCG tipoProducto;

    public TCG(String id, String titulo, double precio, int stock, String nombreJuego, int edadRecomendada,
               TipoTCG tipoProducto) {
        super(id, titulo, precio, stock);
        this.nombreJuego = Objects.requireNonNull(nombreJuego).trim();
        this.edadRecomendada = edadRecomendada;
        this.tipoProducto = Objects.requireNonNull(tipoProducto);
    }

    @Override
    public String getTipo() {
        return "TCG";
    }

        @Override
    public String getCodigoTipo() {
        return COD_TIPO;
    }

    public String getNombreJuego() { 
        return nombreJuego; 
    }

    public int getEdadRecomendada() { 
        return edadRecomendada;
    }

    public TipoTCG getTipoProducto() { 
        return tipoProducto; 
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            " | Juego: %s | Tipo: %s | Edad: %d+",
            nombreJuego, tipoProducto, edadRecomendada
        );
    }

    @Override
    public String toCsvLine() {
        return super.toCsvLine()
             + "," + nombreJuego
             + "," + edadRecomendada
             + "," + tipoProducto.name();
    }

    public static TCG fromCsvTokens(String[] tokens) {
        String id     = tokens[0];
        String titulo = tokens[2];
        double precio = Double.parseDouble(tokens[3]);
        int stock     = Integer.parseInt(tokens[4]);
        EstadoProducto est = EstadoProducto.valueOf(tokens[5]);
        String nombreJuego = tokens[6];
        int edadRec = Integer.parseInt(tokens[7]);
        TipoTCG tipo = TipoTCG.valueOf(tokens[8]);

        TCG tcg = new TCG(id, titulo, precio, stock, nombreJuego, edadRec, tipo);
        tcg.setEstado(est);
        return tcg;
    }


}