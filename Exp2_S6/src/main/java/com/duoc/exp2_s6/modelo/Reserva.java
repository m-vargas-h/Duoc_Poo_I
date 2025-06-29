package com.duoc.exp2_s6.modelo;

import com.duoc.exp2_s6.interfaces.ConvertirCsv;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva implements ConvertirCsv {
    public enum Estado { PENDIENTE, COMPLETADA }
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String id;
    private final String usuarioId;
    private final String productoId;
    private final int cantidad;
    private final LocalDateTime fechaHora;
    private Estado estado;

    /** Constructor para nueva reserva: genera ID (Servicio), marca PENDIENTE */
    public Reserva(String id,
                   Usuario usuario,
                   Producto producto,
                   int cantidad) {
        this.id         = Objects.requireNonNull(id, "ID no puede ser null");
        this.usuarioId  = Objects.requireNonNull(usuario, "Usuario no puede ser null").getId();
        this.productoId = Objects.requireNonNull(producto, "Producto no puede ser null").getId();
        if (cantidad < 1) throw new IllegalArgumentException("Cantidad >= 1");
        this.cantidad   = cantidad;
        this.fechaHora  = LocalDateTime.now();
        this.estado     = Estado.PENDIENTE;
    }

    /** Constructor interno para deserializar */
    private Reserva(String id,
                    String usuarioId,
                    String productoId,
                    int cantidad,
                    LocalDateTime fechaHora,
                    Estado estado) {
        this.id         = id;
        this.usuarioId  = usuarioId;
        this.productoId = productoId;
        this.cantidad   = cantidad;
        this.fechaHora  = fechaHora;
        this.estado     = estado;
    }

    // getters/setters
    public String getId() { 
        return id; 
    }

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

    public Estado getEstado() { 
        return estado; 
    }

    public void setEstado(Estado e) { 
        this.estado = e; 
    }

    /** CSV: id,usuarioId,productoId,cantidad,fechaHora,estado */
    @Override
    public String toCsvLine() {
        return String.join(",",
            id,
            usuarioId,
            productoId,
            String.valueOf(cantidad),
            fechaHora.format(FMT),
            estado.name()
        );
    }

    /** Deserializa un token[] CSV */
    public static Reserva fromCsvTokens(String[] t) {
        String id        = t[0];
        String uId       = t[1];
        String pId       = t[2];
        int cnt          = Integer.parseInt(t[3]);
        LocalDateTime fh = LocalDateTime.parse(t[4], FMT);
        Estado st        = Estado.valueOf(t[5]);
        return new Reserva(id, uId, pId, cnt, fh, st);
    }

    @Override
    public String toString() {
        return String.format(
          "[%s] Reserva %s → prod=%s usr=%s x%d (%s)",
          fechaHora, id, productoId, usuarioId, cantidad, estado
        );
    }

    @Override
    public boolean equals(Object o) { /* compara por id */ 
        if (this==o) return true;
        if (!(o instanceof Reserva)) return false;
        return id.equals(((Reserva)o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}