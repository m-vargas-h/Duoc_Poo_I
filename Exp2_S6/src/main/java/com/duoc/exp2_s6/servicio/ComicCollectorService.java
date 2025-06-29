package com.duoc.exp2_s6.servicio;

import com.duoc.exp2_s6.excepciones.*;
import com.duoc.exp2_s6.modelo.*;
import com.duoc.exp2_s6.modelo.enums.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.*;

public class ComicCollectorService {
    private static final String STORE_CODE = "001"; // Código de la tienda

    private final Map<String, Integer> secuencias = new HashMap<>();    // Secuencias para IDs de productos por tipo
    private final Map<String, Producto> inventario = new HashMap<>();   // Inventario de productos
    private final Map<String, Usuario> usuarios    = new HashMap<>();   // Usuarios registrados
    private final List<Venta> ventas               = new ArrayList<>(); // Ventas realizadas
    private final Map<String, Reserva> reservas    = new HashMap<>();   // Reservas pendientes


    // 1. Agregar producto
    public void agregarProducto(Producto p) {
        if (inventario.containsKey(p.getId()))
        throw new EntidadDuplicadaException("Producto", p.getId());
        inventario.put(p.getId(), p);
    }

    // 2. Eliminar producto
    public void eliminarProducto(String id) {
        if (inventario.remove(id) == null)
        throw new EntidadNoEncontradaException("Producto", id);
    }

    // 3. Listar usuarios
    public Collection<Usuario> listarUsuarios() {
        return Collections.unmodifiableCollection(usuarios.values());
    }

    // 4. Ajustar stock
    public void ajustarStock(String id, int delta) {
        Producto p = Optional.ofNullable(inventario.get(id))
                             .orElseThrow(() -> new EntidadNoEncontradaException("Producto", id));
        int nuevo = p.getStock() + delta;
        if (nuevo < 0) throw new IllegalArgumentException("Stock no puede ser negativo");
        p.setStock(nuevo);
    }

    // 5. Cambiar estado
    public void cambiarEstadoProducto(String id, EstadoProducto nuevoEstado) {
        Producto p = Optional.ofNullable(inventario.get(id))
                             .orElseThrow(() -> new EntidadNoEncontradaException("Producto", id));
        p.setEstado(nuevoEstado);
    }

    // 6. Generar resumen de ventas
    public Map<Producto, Long> generarResumenVentas() {
        return ventas.stream()
        .collect(Collectors.groupingBy(Venta::getProducto, Collectors.counting()));
    }

    // 7. Registrar usuario
    public void registrarUsuario(Usuario u) {
        if (usuarios.containsKey(u.getId()))
            throw new EntidadDuplicadaException("Usuario", u.getId());
        usuarios.put(u.getId(), u);
    }

    // 8. Buscar producto por ID
    public Producto buscarProducto(String id) {
        Producto p = inventario.get(id);
        if (p == null) throw new EntidadNoEncontradaException("Producto", id);
        return p;
    }

    // 9. Comprar producto
    public void comprarProducto(String usuarioId, String productoId, int cantidad) {
        Usuario u = Optional.ofNullable(usuarios.get(usuarioId))
                            .orElseThrow(() -> new EntidadNoEncontradaException("Usuario", usuarioId));
        Producto p = buscarProducto(productoId);

        if (p.getStock() < cantidad) {
            throw new StockInsuficienteException(productoId, p.getStock(), cantidad);
        }
        // Reducir stock
        p.setStock(p.getStock() - cantidad);

        // Registrar venta
        Venta v = new Venta(u, p, cantidad, LocalDateTime.now());
        ventas.add(v);
    }

    // 10. Reservar producto cuando no hay stock
    public String reservarProducto(String usuarioId, String productoId, int cantidad) {
        Usuario u = Optional.ofNullable(usuarios.get(usuarioId))
                            .orElseThrow(() -> new EntidadNoEncontradaException("Usuario", usuarioId));
        Producto p = buscarProducto(productoId);

        // No bloqueamos stock en la reserva; es un “pedido pendiente”
        Reserva r = new Reserva(u, p, cantidad);
        reservas.put(r.getId(), r);
        return r.getId();
    }

    // 11. Consultar estado de reserva
    public Reserva.Estado consultarEstadoReserva(String reservaId) {
        Reserva r = Optional.ofNullable(reservas.get(reservaId))
                            .orElseThrow(() -> new EntidadNoEncontradaException("Reserva", reservaId));
        return r.getEstado();
    }

    public String generarIdProducto(Producto plantilla) {
        String tipo = plantilla.getCodigoTipo();
        int next    = secuencias.getOrDefault(tipo, 0) + 1;
        secuencias.put(tipo, next);
        String zzz  = String.format("%03d", next);
        return STORE_CODE + tipo + zzz;
    }



}