package com.duoc.exp2_s6.modelo;

import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Registra la venta de una cierta cantidad de un producto a un usuario,
 * guardando snapshot del precio unitario y total.
 */
public class Venta implements ConvertirCsv {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String usuarioId;
    private final String productoId;
    private final int cantidad;
    private final LocalDateTime fechaHora;
    private final double precioUnitario;
    private final double total;

    /**
     * Constructor para crear una venta nueva a partir de objetos Usuario y Producto.
     * Captura el precio actual del producto y calcula el total.
     *
     * @param usuario   el comprador (no null)
     * @param producto  el producto vendido (no null)
     * @param cantidad  unidades vendidas, >= 1
     * @param fechaHora instante de la transacción (no null)
     */
    public Venta(Usuario usuario, Producto producto, int cantidad, LocalDateTime fechaHora) {
        Objects.requireNonNull(usuario,  "Usuario no puede ser null");
        Objects.requireNonNull(producto, "Producto no puede ser null");
        if (cantidad < 1) {
            throw new IllegalArgumentException("Cantidad debe ser al menos 1");
        }
        this.usuarioId      = usuario.getId();
        this.productoId     = producto.getId();
        this.cantidad       = cantidad;
        this.fechaHora      = Objects.requireNonNull(fechaHora, "FechaHora no puede ser null");
        this.precioUnitario = producto.getPrecio();
        this.total          = this.precioUnitario * this.cantidad;
    }

    /**
     * Constructor interno para deserializar desde CSV.
     */
    private Venta(String usuarioId, String productoId, int cantidad, LocalDateTime fechaHora, double precioUnitario,
                  double total) {
        this.usuarioId      = Objects.requireNonNull(usuarioId, "usuarioId no puede ser null");
        this.productoId     = Objects.requireNonNull(productoId, "productoId no puede ser null");
        this.cantidad       = cantidad;
        this.fechaHora      = Objects.requireNonNull(fechaHora, "fechaHora no puede ser null");
        this.precioUnitario = precioUnitario;
        this.total          = total;
    }

    // — Getters —

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    // — ConvertirCsv —

    /**
     * Serializa esta venta a CSV:
     * usuarioId,productoId,cantidad,fechaHora,precioUnitario,total
     */
    @Override
    public String toCsvLine() {
        return String.join(",",
            usuarioId,
            productoId,
            String.valueOf(cantidad),
            fechaHora.format(FMT),
            String.valueOf(precioUnitario),
            String.valueOf(total)
        );
    }

    /**
     * Deserializa tokens CSV a un objeto Venta.
     * tokens[0]=usuarioId
     * tokens[1]=productoId
     * tokens[2]=cantidad
     * tokens[3]=fechaHora (ISO_LOCAL_DATE_TIME)
     * tokens[4]=precioUnitario
     * tokens[5]=total
     */
    public static Venta fromCsvTokens(String[] tokens) {
        String uId  = tokens[0];
        String pId  = tokens[1];
        int cnt     = Integer.parseInt(tokens[2]);
        LocalDateTime fh = LocalDateTime.parse(tokens[3], FMT);
        double pu   = Double.parseDouble(tokens[4]);
        double tot  = Double.parseDouble(tokens[5]);
        return new Venta(uId, pId, cnt, fh, pu, tot);
    }

    @Override
    public String toString() {
        return String.format(
            "[%s] %s vendida a %s x%d @%.2f → total %.2f",
            fechaHora, productoId, usuarioId, cantidad, precioUnitario, total
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, productoId, cantidad, fechaHora, precioUnitario, total);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venta)) return false;
        Venta v = (Venta) o;
        return cantidad == v.cantidad
            && Double.compare(v.precioUnitario, precioUnitario) == 0
            && Double.compare(v.total, total) == 0
            && usuarioId.equals(v.usuarioId)
            && productoId.equals(v.productoId)
            && fechaHora.equals(v.fechaHora);
    }
}