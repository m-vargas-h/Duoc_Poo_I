package com.duoc.exp2_s6.servicio;

import com.duoc.exp2_s6.excepciones.*;
import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.base.Usuario;
import com.duoc.exp2_s6.modelo.enums.*;
import com.duoc.exp2_s6.modelo.ventas.ResumenVenta;
import com.duoc.exp2_s6.modelo.ventas.Venta;
import com.duoc.exp2_s6.persistencia.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


public class ComicCollectorService {
    private static final String STORE_CODE = "001"; // Código de la tienda

    // Persistencia
    private final PersistenciaProductos persistenciaProd = new PersistenciaProductos();
    private final PersistenciaUsuarios  persistenciaUsr  = new PersistenciaUsuarios();
    private final PersistenciaVentas persistenciaVentas = new PersistenciaVentas();

    private final Map<String,Integer> secuencias  = new HashMap<>();
    private final Map<String,Producto> inventario = new LinkedHashMap<>();
    private final Map<String,Usuario>  usuarios   = new LinkedHashMap<>();
    private final List<Venta> ventas              = new ArrayList<>();


    public ComicCollectorService() {
        // 1) Cargo productos
        try {
            List<Producto> listP = persistenciaProd.cargarTodos();
            for (Producto p : listP) {
                inventario.put(p.getId(), p);
                String tipo = p.getCodigoTipo();
                int seq     = Integer.parseInt(p.getId().substring(6));
                secuencias.merge(tipo, seq, Math::max);
            }
        } catch (IOException e) {
            System.err.println("⚠️ No se pudo leer productos.csv — inventario vacío");
        }

        // 2) Cargo usuarios
        try {
            List<Usuario> listU = persistenciaUsr.cargarTodos();
            for (Usuario u : listU) {
                usuarios.put(u.getId(), u);
            }
        } catch (IOException e) {
            System.err.println("⚠️ No se pudo leer usuarios.csv — lista de usuarios vacía");
        }

        // 3) Cargar ventas:
        try {
            persistenciaVentas.cargarTodos()
                              .forEach(ventas::add);
        } catch (IOException e) {
            System.err.println("⚠️ No se pudo leer ventas.csv — historial vacío");
        }

    }


    // 1. Agregar producto
    public void agregarProducto(Producto p) {
        if (inventario.containsKey(p.getId()))
            throw new EntidadDuplicadaException("Producto", p.getId());
        inventario.put(p.getId(), p);
        persistirProductos();
    }

    // 2. Eliminar producto
    public void eliminarProducto(String id) {
        if (inventario.remove(id) == null)
            throw new EntidadNoEncontradaException("Producto", id);
        persistirProductos();
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
        persistirProductos();
    }

    // 5. Cambiar estado de producto
    public void cambiarEstadoProducto(String id, EstadoProducto nuevoEstado) {
        Producto p = Optional.ofNullable(inventario.get(id))
                             .orElseThrow(() -> new EntidadNoEncontradaException("Producto", id));
        p.setEstado(nuevoEstado);
        persistirProductos();
    }

    // 6. Generar resumen de ventas
    public Collection<ResumenVenta> generarResumenVentasDetallado() {
        Map<String,ResumenVenta> map = new LinkedHashMap<>();

        for (Venta v : ventas) {
            // 1) Obtener el producto asociado
            Producto p = inventario.get(v.getProductoId());
            // 2) Buscar o crear el ResumenVenta
            ResumenVenta rv = map.computeIfAbsent(p.getId(), id -> new ResumenVenta(p));
            // 3) Acumular esta venta
            rv.addVenta(v);
        }

        return map.values();
    }

    // 7. Registrar usuario
    public void registrarUsuario(Usuario u) {
        if (usuarios.containsKey(u.getId())) {
            throw new EntidadDuplicadaException("Usuario", u.getId());
        }
        usuarios.put(u.getId(), u);
        persistirUsuarios();
    }


    // 8. Buscar producto por ID
    public Producto buscarProducto(String id) {
        Producto p = inventario.get(id);
        if (p == null) throw new EntidadNoEncontradaException("Producto", id);
        return p;
    }

    // 9. Comprar producto
    public void comprarProducto(String usuarioId, String productoId, int cantidad) {
        // 1️⃣ Recuperar objeto Usuario
        Usuario u = Optional.ofNullable(usuarios.get(usuarioId))
                            .orElseThrow(() ->
                                new EntidadNoEncontradaException("Usuario", usuarioId)
                            );

        // 2️⃣ Recuperar objeto Producto
        Producto p = buscarProducto(productoId);

        // 3️⃣ Validar stock
        if (p.getStock() < cantidad) {
            throw new StockInsuficienteException(productoId, p.getStock(), cantidad);
        }

        // 4️⃣ Ajustar stock
        p.setStock(p.getStock() - cantidad);

        // 5️⃣ Crear y registrar venta
        Venta v = new Venta(u, p, cantidad, LocalDateTime.now());
        ventas.add(v);

        // 6️⃣ Persistir ambos CSV
        persistirProductos();
        persistirVentas();
    }

    //! 10. Reservar producto, no utilizado en el momento actual
    //public String reservarProducto(String usuarioId, String productoId, int cantidad) {
    //    Usuario u = Optional.ofNullable(usuarios.get(usuarioId))
    //                        .orElseThrow(() -> new EntidadNoEncontradaException("Usuario", usuarioId));
    //    Producto p = buscarProducto(productoId);
    //    if (p.getEstado() != EstadoProducto.PRE_VENTA) {
    //        throw new IllegalStateException("Producto no está en PRE_VENTA");
    //    }
    //    if (p.getStock() < cantidad) {
    //        throw new StockInsuficienteException(productoId, p.getStock(), cantidad);
    //    }
    //    // 1) descuenta stock en preventa
    //    p.setStock(p.getStock() - cantidad);
    //    persistirProductos();

    //    // 2) genera ID de reserva
    //    String resId = String.format("RSV%06d", ++reservaSeq);
    //    Reserva r = new Reserva(resId, u, p, cantidad);
    //    reservas.put(r.getId(), r);

    //    // 3) persiste reservas
    //    persistirReservas();
    //    return r.getId();
    //}


    //! 11. Consultar estado de reserva, no utilizado en el momento actual
    //public Reserva.Estado consultarEstadoReserva(String reservaId) {
    //    Reserva r = Optional.ofNullable(reservas.get(reservaId))
    //                        .orElseThrow(() -> new EntidadNoEncontradaException("Reserva", reservaId));
    //    return r.getEstado();
    //}

    public String generarIdProducto(Producto plantilla) {
        String tipo = plantilla.getCodigoTipo();
        int next    = secuencias.getOrDefault(tipo, 0) + 1;
        secuencias.put(tipo, next);
        return STORE_CODE + tipo + String.format("%03d", next);
    }

    public Collection<Producto> listarProductos() {
        return Collections.unmodifiableCollection(inventario.values());
    }

    //  ————— Persistencia interna —————

    private void persistirProductos() {
        try {
            persistenciaProd.guardarTodos(inventario.values());
        } catch (IOException e) {
            throw new RuntimeException("Error guardando productos.csv", e);
        }
    }

    private void persistirUsuarios() {
        try {
            persistenciaUsr.guardarTodos(usuarios.values());
        } catch (IOException e) {
            throw new RuntimeException("Error guardando usuarios.csv", e);
        }
    }

    private void persistirVentas() {
        try {
            persistenciaVentas.guardarTodos(ventas);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando ventas en CSV", e);
        }
    }

    //! Persistir reservas, sistema en proceso, no utilizado en el momento actual
    //private void persistirReservas() {
    //    try {
    //        persistenciaRes.guardarTodos(reservas.values());
    //    } catch (IOException e) {
    //        throw new RuntimeException("Error guardando reservas en CSV", e);
    //    }
    //}

}