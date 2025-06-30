package com.duoc.exp2_s6.ui;

import com.duoc.exp2_s6.servicio.ComicCollectorService;
import com.duoc.exp2_s6.modelo.base.Producto;
import com.duoc.exp2_s6.modelo.enums.EstadoProducto;
import com.duoc.exp2_s6.modelo.enums.TipoTCG;
import com.duoc.exp2_s6.modelo.productos.*;
import com.duoc.exp2_s6.excepciones.*;

import java.util.Scanner;

public class MenuAdministrador {
    // Credenciales de administrador 
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "password"; // La clave mas segura del mundo mundial

    private final Scanner scanner;
    private final ComicCollectorService servicio;

    public MenuAdministrador(Scanner scanner, ComicCollectorService servicio) {
        this.scanner = scanner;
        this.servicio = servicio;
    }

    // Inicia el menú de administrador, solicitando credenciales.
    // Si las credenciales son correctas, muestra el menú de administrador.
    public void login() {
        System.out.println("\n=== LOGIN ADMINISTRADOR ===");
        System.out.print("Usuario: ");
        String user = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();

        if (!ADMIN_USER.equals(user) || !ADMIN_PASS.equals(pass)) {
            System.out.println("Credenciales incorrectas. Volviendo al menú principal.");
            return;
        }

        System.out.println("Bienvenido, administrador.\n");
        mostrar();
    }

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;

                case 2:
                    eliminarProducto();
                    break;

                case 3:
                    listarUsuarios();
                    break;

                case 4:
                    modificarStock();
                    break;

                case 5:
                    cambiarEstadoProducto();
                    break;

                case 6:
                    verResumenVentasDetallado();
                    break;

                case 7:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (opcion != 7);
    }

    private void imprimirMenu() {
        System.out.println("""
        ========== MENÚ ADMINISTRADOR ==========
        1. Agregar producto al inventario
        2. Eliminar producto
        3. Listar usuarios registrados
        4. Modificar stock de producto
        5. Cambiar estado de producto
        6. Ver resumen de ventas
        7. Salir al menú principal
        =========================================
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

    private void agregarProducto() {
        System.out.println("""
            Elige tipo de producto:
            1. Comic
            2. NovelaGráfica
            3. Coleccionable
            4. Juego de Mesa
            5. TCG
            6. Volver
            """);
        int tipo = leerOpcion();
        if (tipo == 6) return;

        // Creamos una plantilla para extraer el código de tipo
        Producto plantilla;
        switch (tipo) {
            case 1 -> plantilla = new Comic("", "", 0, 0, "");
            case 2 -> plantilla = new NovelaGrafica("", "", 0.0, 0, "", 0);
            case 3 -> plantilla = new Coleccionable("", "", 0, 0, "", "");
            case 4 -> plantilla = new JuegoMesa("", "", 0.0, 0, "", 0, 0);
            case 5 -> plantilla = new TCG("", "", 0, 0, "", 0, TipoTCG.OTRO);
            default -> {
                System.out.println("Opción inválida.");
                return;
            }
        }

        // Generar ID: XXX + YYY + ZZZ
        String id = servicio.generarIdProducto(plantilla);
        System.out.println("ID generado: " + id);

        // Leer atributos comunes
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Stock inicial: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());

        Producto real = null;
        try {
            switch (tipo) {
                case 1 -> {
                    // Comic
                    System.out.print("Editorial: ");
                    String editorial = scanner.nextLine().trim();
                    System.out.print("Volumen (opcional): ");
                    String vol = scanner.nextLine().trim();
                    if (vol.isEmpty()) {
                        real = new Comic(id, titulo, precio, stock, editorial);
                    } else {
                        real = new Comic(id, titulo, precio, stock, editorial, Integer.parseInt(vol));
                    }
                }

                case 2 -> {
                    // NovelaGráfica
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine().trim();

                    System.out.print("Año de publicación: ");
                    int fechaPublicacion = Integer.parseInt(scanner.nextLine().trim());

                    real = new NovelaGrafica(
                        id,
                        titulo,
                        precio,
                        stock,
                        autor,
                        fechaPublicacion
                    );
                }

                case 3 -> {
                    // Coleccionable
                    System.out.print("Franquicia: ");
                    String franq = scanner.nextLine().trim();
                    System.out.print("Edición: ");
                    String ed = scanner.nextLine().trim();
                    real = new Coleccionable(id, titulo, precio, stock, franq, ed);
                }

                case 4 -> {
                    // Juego de Mesa
                    System.out.print("Creador: ");
                    String creador = scanner.nextLine().trim();

                    System.out.print("Jugadores mínimos: ");
                    int min = Integer.parseInt(scanner.nextLine().trim());

                    System.out.print("Jugadores máximos (opcional): ");
                    String max = scanner.nextLine().trim();

                    System.out.print("Edad recomendada: ");
                    int edad = Integer.parseInt(scanner.nextLine().trim());

                    if (max.isEmpty()) {
                        // Llama al constructor sin jugadores máximos (7 params)
                        real = new JuegoMesa(
                            id,
                            titulo,
                            precio,
                            stock,
                            creador,
                            min,
                            edad           // este es el séptimo parámetro
                        );
                    } else {
                        // Llama al constructor con jugadores máximos (8 params)
                        int maxJug = Integer.parseInt(max);
                        real = new JuegoMesa(
                            id,
                            titulo,
                            precio,
                            stock,
                            creador,
                            min,
                            maxJug,       // séptimo parámetro
                            edad          // octavo parámetro
                        );
                    }
                }

                case 5 -> {
                    // TCG
                    System.out.print("Nombre de Juego: ");
                    String nombreJuego = scanner.nextLine().trim();
                    System.out.print("Edad recomendada: ");
                    int edadRec = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Tipo (BOOSTER, DECK, DISPLAY, PROMO, LATA, OTRO): ");
                    TipoTCG tipoTcg = TipoTCG.valueOf(scanner.nextLine().trim().toUpperCase());
                    real = new TCG(id, titulo, precio, stock, nombreJuego, edadRec, tipoTcg);
                }
            }

            // Agregar al inventario
            servicio.agregarProducto(real);
            System.out.println("Producto agregado con ID: " + id);

        } catch (Exception e) {
            System.err.println("Error al crear producto: " + e.getMessage());
        }
    }   
    
    private void eliminarProducto() {
        System.out.print("ID de producto a eliminar: ");
        String id = scanner.nextLine().trim();
        try {
            servicio.eliminarProducto(id);
            System.out.println("Producto eliminado.");
        } catch (EntidadNoEncontradaException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        System.out.println("👥 Usuarios registrados:");
        servicio.listarUsuarios()
                .forEach(u -> System.out.println("• " + u));
    }

    private void modificarStock() {
        System.out.print("ID de producto: ");
        String id = scanner.nextLine().trim();
        System.out.print("Cantidad a ajustar (+ añade, - quita): ");
        int delta;
        try {
            delta = Integer.parseInt(scanner.nextLine());
            servicio.ajustarStock(id, delta);
            System.out.println("Stock modificado.");
        } catch (NumberFormatException e) {
            System.err.println("Cantidad inválida.");
        } catch (EntidadNoEncontradaException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoProducto() {
        System.out.print("ID de producto: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nuevo estado (DISPONIBLE, AGOTADO, OCULTO): ");
        String raw = scanner.nextLine().trim().toUpperCase();
        try {
            EstadoProducto estado = EstadoProducto.valueOf(raw);
            servicio.cambiarEstadoProducto(id, estado);
            System.out.println("Estado actualizado.");
        } catch (IllegalArgumentException e) {
            System.err.println("Estado inválido.");
        } catch (EntidadNoEncontradaException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // en MenuAdministrador.java
    private void verResumenVentasDetallado() {
        System.out.println("Resumen detallado de ventas:");
        servicio.generarResumenVentasDetallado()
                .forEach(rv -> {
                    Producto p = rv.getProducto();
                    System.out.printf("• %s | %s → %d unidades → $%.2f total%n",
                        p.getId(),
                        p.getTitulo(),
                        rv.getCantidadTotal(),
                        rv.getIngresoTotal()
                    );
                });
    }
}