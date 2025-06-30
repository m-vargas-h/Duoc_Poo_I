package com.duoc.exp2_s6.ui;

import com.duoc.exp2_s6.servicio.ComicCollectorService;
import com.duoc.exp2_s6.util.Utilidades;
import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.base.Usuario;
import com.duoc.exp2_s6.modelo.productos.*;
import com.duoc.exp2_s6.excepciones.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuUsuario {
    private final Scanner scanner = new Scanner(System.in);
    private final ComicCollectorService servicio;

    public MenuUsuario(ComicCollectorService servicio) {
        this.servicio = servicio;
    }

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    buscarPorCategoria();
                    break;
                case 3:
                    comprarConCarrito();
                    break;
                case 4:
                    System.out.println("Reservas de productos no implementadas actualmente.");
                    //! reservarProducto();
                    break;
                case 5:
                    System.out.println("Consulta de estado de reservas no implementada actualmente.");
                    //! consultarEstadoReserva();
                    break;
                case 6:
                    System.out.println("👋 Cerrando sesión de usuario...");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 6);
    }

    private void imprimirMenu() {
        System.out.println("""
        ========== MENÚ USUARIO ==========
        1. Registrar usuario
        2. Buscar producto
        3. Comprar producto
        4. Reservar producto
        5. Consultar estado de reservas
        6. Salir
        ===================================
        """);
    }

    private int leerOpcion() {
        try {
            System.out.print("Seleccione una opción: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void registrarUsuario() {
        System.out.print("ID de nuevo usuario (RUT sin puntos, con guion): ");
        String id = scanner.nextLine().trim();
        // 1) Valida formato de RUT
        if (!Utilidades.validarFormatoRut(id)) {
            System.err.println("Formato de RUT inválido. Debe ser NNNNNNNN-X (solo un dígito o K).");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if (!Utilidades.validarEmail(email)) {
            System.err.println("Email inválido. Debe tener formato user@dominio.tld");
            return;
        }

        try {
            Usuario u = new Usuario(id, nombre, email);
            servicio.registrarUsuario(u);
            System.out.println("Usuario registrado: " + u);
        } catch (EntidadDuplicadaException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void buscarPorCategoria() {
        listarPorCategoria();
    }

    private void comprarConCarrito() {
        // 1. Validar RUT
        System.out.print("Tu RUT de usuario (sin puntos, con guion): ");
        String userId = scanner.nextLine().trim();
        if (!Utilidades.validarFormatoRut(userId)) {
            System.err.println("❌ Formato inválido. Usa NNNNNNNN-X");
            return;
        }

        // 2. Inicializar carrito
        Map<String, Integer> carrito = new LinkedHashMap<>();
        boolean seguirComprando = true;

        while (seguirComprando) {
            // 2.1 Mostrar productos por categoría reutilizando método existente
            List<Producto> lista = listarPorCategoria();
            if (lista.isEmpty()) continue;

            // 2.2 Seleccionar producto por índice
            System.out.print("Seleccione producto (número): ");
            int seleccion;
            try {
                seleccion = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (seleccion < 0 || seleccion >= lista.size()) {
                    System.out.println("❌ Selección fuera de rango.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida.");
                continue;
            }

            Producto elegido = lista.get(seleccion);

            // 2.3 Leer cantidad
            System.out.print("Cantidad a comprar: ");
            int cantidad;
            try {
                cantidad = Integer.parseInt(scanner.nextLine().trim());
                if (cantidad <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            if (cantidad > elegido.getStock()) {
                System.out.println("Stock insuficiente. Disponible: " + elegido.getStock());
                continue;
            }

            // 2.4 Agregar al carrito
            carrito.merge(elegido.getId(), cantidad, Integer::sum);
            System.out.println("Producto agregado al carrito.");

            // 2.5 Preguntar si quiere continuar
            System.out.print("¿Agregar otro producto? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            seguirComprando = resp.equals("s");
        }

        // 3. Pantalla de pago
        if (carrito.isEmpty()) {
            System.out.println("🛒 No se seleccionaron productos.");
            return;
        }

        System.out.println("\n=== RESUMEN DE COMPRA ===");
        double total = 0.0;

        for (var entry : carrito.entrySet()) {
            Producto p = servicio.buscarProducto(entry.getKey());
            int cantidad = entry.getValue();
            double subtotal = cantidad * p.getPrecio();
            total += subtotal;
            System.out.printf("• %s x%d = $%.0f%n", p.getTitulo(), cantidad, subtotal);
        }

        System.out.printf("💰 Total a pagar: $%.0f%n", total);
        System.out.print("Medio de pago: Débito (presione Enter para confirmar): ");
        scanner.nextLine();

        // 4. Ejecutar cada compra
        try {
            for (var entry : carrito.entrySet()) {
                servicio.comprarProducto(userId, entry.getKey(), entry.getValue());
            }
            System.out.println("✅ Pago aceptado. Compra realizada con éxito.");
        } catch (Exception e) {
            System.err.println("❌ Error al procesar la compra: " + e.getMessage());
        }
    }

    //! Reservar producto, actualmente no utilizado en el sistema
    //! Se ha dejado el método como referencia para futuras implementaciones 
    //private void reservarProducto() {
    //    System.out.print("Tu ID de usuario: ");
    //    String userId = scanner.nextLine().trim();
    //    System.out.print("ID de producto a reservar: ");
    //    String prodId = scanner.nextLine().trim();
    //    System.out.print("Cantidad: ");
    //    int cantidad;
    //    try {
    //        cantidad = Integer.parseInt(scanner.nextLine());
    //    } catch (NumberFormatException e) {
    //        System.err.println("⚠️ Cantidad inválida.");
    //        return;
    //    }

    //    try {
    //        String reservaId = servicio.reservarProducto(userId, prodId, cantidad);
    //        System.out.println("✅ Reserva creada (ID: " + reservaId + ").");
    //    } catch (EntidadNoEncontradaException e) {
    //        System.err.println("⚠️ " + e.getMessage());
    //    }
    //}

    //! Consultar estado de reserva, actualmente no utilizado en el sistema
    //private void consultarEstadoReserva() {
    //    System.out.print("ID de reserva: ");
    //   String reservaId = scanner.nextLine().trim();

    //    try {
    //        Reserva.Estado estado = servicio.consultarEstadoReserva(reservaId);
    //        System.out.println("🔎 Estado de reserva: " + estado);
    //    } catch (EntidadNoEncontradaException e) {
    //        System.err.println("⚠️ " + e.getMessage());
    //    }
    //}

    // ---------------------- Utilidades para el menú de usuario ----------------------

    private List<Producto> listarPorCategoria() {
        System.out.println("""
            === Buscar productos por categoría ===
            1. Comic
            2. Novela Gráfica
            3. Coleccionable
            4. Juego de Mesa
            5. TCG
            0. Volver
            """);
        int opcion = leerOpcion();

        Class<?> tipo = switch (opcion) {
            case 1 -> Comic.class;
            case 2 -> NovelaGrafica.class;
            case 3 -> Coleccionable.class;
            case 4 -> JuegoMesa.class;
            case 5 -> TCG.class;
            default -> null;
        };

        if (tipo == null) return List.of();

        List<Producto> lista = servicio.listarProductos().stream()
            .filter(p -> tipo.isInstance(p))
            .toList();

        System.out.printf("📦 Mostrando productos de tipo %s:%n", tipo.getSimpleName());
        System.out.println("ID         | Título                  | Vol | Estado       | Stock | Precio");
        System.out.println("-----------+-------------------------+------+--------------+--------+--------");

        for (Producto p : lista) {
            String id     = p.getId();
            String titulo = String.format("%-23s", p.getTitulo());
            String estado = String.format("%-12s", p.getEstado());
            String stock  = String.format("%5d", p.getStock());
            String precio = String.format("$%5.0f", p.getPrecio());

            String volumen = "-";
            if (p instanceof Comic c && c.getVolumen().orElse(0) > 0) {
                volumen = String.valueOf(c.getVolumen().orElse(0));
            }

            System.out.printf("%-10s | %s | %4s | %s | %s | %s%n",
                    id, titulo, volumen, estado, stock, precio);
        }

        return lista;
    }

}