package com.duoc.exp2_s6.ui;

import com.duoc.exp2_s6.servicio.ComicCollectorService;
import com.duoc.exp2_s6.modelo.*;
import com.duoc.exp2_s6.excepciones.*;

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
                    buscarProducto();
                    break;
                case 3:
                    comprarProducto();
                    break;
                case 4:
                    reservarProducto();
                    break;
                case 5:
                    consultarEstadoReserva();
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
        System.out.print("ID de nuevo usuario: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        try {
            Usuario u = new Usuario(id, nombre, email);
            servicio.registrarUsuario(u);
            System.out.println("✅ Usuario registrado: " + u);
        } catch (EntidadDuplicadaException | IllegalArgumentException e) {
            System.err.println("⚠️ " + e.getMessage());
        }
    }

    private void buscarProducto() {
        System.out.print("ID de producto a buscar: ");
        String id = scanner.nextLine().trim();

        try {
            Producto p = servicio.buscarProducto(id);
            System.out.println("🔍 " + p);
        } catch (EntidadNoEncontradaException e) {
            System.err.println("⚠️ " + e.getMessage());
        }
    }

    private void comprarProducto() {
        System.out.print("Tu ID de usuario: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ID de producto a comprar: ");
        String prodId = scanner.nextLine().trim();
        System.out.print("Cantidad: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Cantidad inválida.");
            return;
        }

        try {
            servicio.comprarProducto(userId, prodId, cantidad);
            System.out.println("✅ Compra exitosa.");
        } catch (EntidadNoEncontradaException | StockInsuficienteException e) {
            System.err.println("⚠️ " + e.getMessage());
        }
    }

    private void reservarProducto() {
        System.out.print("Tu ID de usuario: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ID de producto a reservar: ");
        String prodId = scanner.nextLine().trim();
        System.out.print("Cantidad: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Cantidad inválida.");
            return;
        }

        try {
            String reservaId = servicio.reservarProducto(userId, prodId, cantidad);
            System.out.println("✅ Reserva creada (ID: " + reservaId + ").");
        } catch (EntidadNoEncontradaException e) {
            System.err.println("⚠️ " + e.getMessage());
        }
    }

    private void consultarEstadoReserva() {
        System.out.print("ID de reserva: ");
        String reservaId = scanner.nextLine().trim();

        try {
            Reserva.Estado estado = servicio.consultarEstadoReserva(reservaId);
            System.out.println("🔎 Estado de reserva: " + estado);
        } catch (EntidadNoEncontradaException e) {
            System.err.println("⚠️ " + e.getMessage());
        }
    }
}