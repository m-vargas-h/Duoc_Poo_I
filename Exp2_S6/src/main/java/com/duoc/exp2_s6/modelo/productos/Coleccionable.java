package com.duoc.exp2_s6.modelo.productos;

import java.util.Objects;
import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.enums.EstadoProducto;

public class Coleccionable extends Producto implements ConvertirCsv {
    public static final String COD_TIPO = "004";
    private final String franquicia; // Ej: Marvel, Star Wars, o "Material original"
    private final String edicion;    // Ej: "25 Aniversario", "Comic-Con Exclusive", etc.

    public Coleccionable(String id, String titulo, double precio, int stock, String franquicia, String edicion) {
        super(id, titulo, precio, stock);
        this.franquicia = Objects.requireNonNullElse(franquicia, "Material original").trim();
        this.edicion    = Objects.requireNonNull(edicion).trim();
    }

    @Override
    public String getTipo() {
        return "Coleccionable";
    }

        @Override
    public String getCodigoTipo() {
        return COD_TIPO;
    }

    public String getFranquicia() {
        return franquicia.isBlank() ? "Material original" : franquicia;
    }

    public String getEdicion() {
        return edicion;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Franquicia: %s | Edición: %s", getFranquicia(), edicion);
    }

    @Override
    public String toCsvLine() {
        // super.toCsvLine() → "id,tipo,titulo,precio,stock,estado"
        return super.toCsvLine()
             + "," + getFranquicia()
             + "," + edicion;
    }

    public static Coleccionable fromCsvTokens(String[] tokens) {
        String id          = tokens[0];
        String titulo      = tokens[2];
        double precio      = Double.parseDouble(tokens[3]);
        int stock          = Integer.parseInt(tokens[4]);
        EstadoProducto est = EstadoProducto.valueOf(tokens[5]);
        String franq       = tokens.length > 6 ? tokens[6] : "";
        String edic        = tokens.length > 7 ? tokens[7] : "";

        Coleccionable c = new Coleccionable(id, titulo, precio, stock, franq, edic);
        c.setEstado(est);
        return c;
    }

}