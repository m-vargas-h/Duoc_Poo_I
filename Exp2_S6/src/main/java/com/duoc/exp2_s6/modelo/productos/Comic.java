package com.duoc.exp2_s6.modelo.productos;

import java.util.Objects;
import java.util.Optional;
import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import com.duoc.exp2_s6.modelo.base.Producto;


public class Comic extends Producto implements ConvertirCsv {
    public static final String COD_TIPO = "001"; // Código del tipo de producto Comic
    private String editorial;
    private Optional<Integer> volumen; // Volumen opcional, vacío si no es parte de una serie

    // Constructor para cómics sueltos (sin volumen)
    public Comic(String id, String titulo, double precio, int stock, String editorial) {
        super(id, titulo, precio, stock);
        this.editorial = Objects.requireNonNull(editorial);
        this.volumen   = Optional.empty();
    }

    // Constructor para cómics de una serie (con volumen)
    public Comic(String id, String titulo, double precio, int stock, String editorial, int volumen) {
        super(id, titulo, precio, stock);
        this.editorial = Objects.requireNonNull(editorial);
        this.volumen   = Optional.of(volumen);
    }

    @Override
    public String getTipo() {
        return "Comic";
    }

    @Override
    public String getCodigoTipo() {
        return COD_TIPO;
    }

    public String getEditorial() {
        return editorial;
    }

    /** Devuelve Optional.empty() si no es parte de serie */
    public Optional<Integer> getVolumen() {
        return volumen;
    }

    @Override
    public String toString() {
        String base = super.toString() + " | Editorial: " + editorial;
        return volumen
                .map(v -> base + " | Vol. " + v)
                .orElse(base);
    }

    // Convierte el objeto a una línea CSV
    // El formato será: tipo,código,id,titulo,precio,stock,estado...
    @Override
    public String toCsvLine() {
        // Llama al super y añade editorial + volumen
        String base = super.toCsvLine();
        String vol = getVolumen().map(Object::toString).orElse("");
        return base + "," + getEditorial() + "," + vol;
    }


    // Método estático para crear un Comic a partir de un array de tokens CSV
    public static Comic fromCsvTokens(String[] t) {
        // t[0]=id, t[1]=tipo, t[2]=titulo, t[3]=precio, t[4]=stock, t[5]=estado,
        // t[6]=editorial, t[7]=volumen (opcional)
        String id        = t[0];
        String titulo    = t[2];
        double precio    = Double.parseDouble(t[3]);
        int stock        = Integer.parseInt(t[4]);
        String editorial = t[6];
        if (t.length > 7 && !t[7].isEmpty()) {
            return new Comic(id, titulo, precio, stock,
                             editorial,
                             Integer.parseInt(t[7]));
        }
        return new Comic(id, titulo, precio, stock, editorial);
    }

}