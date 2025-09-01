package com.grupo7.ecommerce.ui;

import com.grupo7.ecommerce.commands.SetCategoriaPromocionCommand;
import com.grupo7.ecommerce.commands.SetCuponValidoCommand;
import com.grupo7.ecommerce.commands.SetDescuentoGlobalCommand;
import com.grupo7.ecommerce.controller.CarritoController;
import com.grupo7.ecommerce.controller.DescuentoController;
import com.grupo7.ecommerce.controller.ProductoController;
import com.grupo7.ecommerce.repository.DataLoader;
import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.Producto;
import com.grupo7.ecommerce.model.dto.Boleta;
import com.grupo7.ecommerce.view.CarritoView;
import com.grupo7.ecommerce.view.DescuentoView;
import com.grupo7.ecommerce.view.ProductoView;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final ProductoController productoController;
    private final CarritoController carritoController;
    private final DescuentoController descuentoController;
    private final ProductoView productoView;
    private final CarritoView carritoView;
    private final DescuentoView descuentoView;
    private final DataLoader dataLoader;

    private String cuponActual = null;

    public ConsoleUI(ProductoController productoController,
                     CarritoController carritoController,
                     DescuentoController descuentoController,
                     ProductoView productoView,
                     CarritoView carritoView,
                     DescuentoView descuentoView,
                     DataLoader dataLoader) {
        this.productoController = productoController;
        this.carritoController = carritoController;
        this.descuentoController = descuentoController;
        this.productoView = productoView;
        this.carritoView = carritoView;
        this.descuentoView = descuentoView;
        this.dataLoader = dataLoader;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String input = sc.nextLine();
            int op = parseIntSafe(input, -1);
            switch (op) {
                case 1 -> listarCatalogo();
                case 2 -> agregarProducto(sc);
                case 3 -> eliminarProducto(sc);
                case 4 -> verCarrito();
                case 5 -> emitirBoleta(sc);
                case 6 -> menuDescuentos(sc);
                case 7 -> verHistoricoBoletas();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
            System.out.println();
        }
        System.out.println("Saliendo... ¡Gracias por su visita!");
    }

    private void mostrarMenu() {
        System.out.println("\n==== Menú ====");
        System.out.println("1) Ver catálogo");
        System.out.println("2) Agregar producto al carrito");
        System.out.println("3) Eliminar producto del carrito");
        System.out.println("4) Ver carrito");
        System.out.println("5) Emitir boleta (finalizar compra)");
        System.out.println("6) Configurar descuentos");
        System.out.println("7) Ver histórico de boletas");
        System.out.println("0) Salir");
    }

    private void listarCatalogo() {
        List<Component> lista = productoController.listarCatalogo();
        if (lista.isEmpty()) {
            System.out.println("No hay productos en el catálogo.");
            return;
        }
        productoView.mostrarCatalogo(lista);
    }

    private void agregarProducto(Scanner sc) {
        List<Component> lista = productoController.listarCatalogo();
        if (lista.isEmpty()) { System.out.println("Catálogo vacío."); return; }
        productoView.mostrarCatalogo(lista);
        System.out.print("Ingrese índice del producto a agregar: ");
        int idx = parseIntSafe(sc.nextLine(), -1) - 1;
        if (idx < 0 || idx >= lista.size()) {
            System.out.println("Índice fuera de rango.");
            return;
        }
        carritoController.agregarProducto(lista.get(idx));
        carritoView.mostrarCarritoListado(carritoController.getCarrito());
        dataLoader.guardarCarritoCSV(); // Persistir cambio
    }

    private void eliminarProducto(Scanner sc) {
        if (carritoController.getCarrito().getItems().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        carritoView.mostrarCarritoListado(carritoController.getCarrito());
        System.out.print("Ingrese índice del producto a eliminar (según carrito): ");
        int idx = parseIntSafe(sc.nextLine(), -1) - 1;
        List<Component> items = carritoController.getCarrito().getItems();
        if (idx < 0 || idx >= items.size()) {
            System.out.println("Índice fuera de rango.");
            return;
        }
        Component ref = items.get(idx);
        carritoController.eliminarProducto(new Producto(ref.getNombre(), ref.getCategoria(), ref.getPrecio()));
        carritoView.mostrarCarritoListado(carritoController.getCarrito());
        dataLoader.guardarCarritoCSV(); // Persistir cambio
    }

    private void emitirBoleta(Scanner sc) {
        if (carritoController.getCarrito().getItems().isEmpty()) {
            System.out.println("El carrito está vacío. No hay boleta para emitir.");
            return;
        }
        System.out.print("Ingrese cupón a aplicar para esta boleta (ENTER para ninguno): ");
        cuponActual = emptyToNull(sc.nextLine());
        Boleta boleta = carritoController.generarBoleta(cuponActual);
        carritoView.mostrar(boleta);
        System.out.print("Confirma emisión de la boleta y vaciado del carrito? (s/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("s") || confirm.equals("y")) {
            boolean ok = dataLoader.registrarBoletaCSV(boleta, cuponActual);
            if (!ok) System.out.println("Warning: no fue posible registrar la boleta en el CSV.");
            // Vaciar carrito en memoria y en CSV
            carritoController.getCarrito().clear();
            boolean saved = dataLoader.guardarCarritoCSV();
            if (!saved) System.out.println("Warning: no fue posible actualizar carrito.csv");
            System.out.println("Boleta emitida y carrito vaciado.");
            cuponActual = null;
        } else {
            System.out.println("Emisión cancelada.");
        }
    }

    // Nuevo: mostrar carrito sin pedir índice (desde el menú)
    private void verCarrito() {
        carritoView.mostrarCarritoListado(carritoController.getCarrito());
    }

    private void menuDescuentos(Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Descuentos --");
            System.out.println("1) Ver configuración");
            System.out.println("2) Cambiar descuento global (%)");
            System.out.println("3) Cambiar cupón válido");
            System.out.println("4) Cambiar categoría en promoción");
            System.out.println("5) Cambiar cupón a aplicar en boleta actual");
            System.out.println("0) Volver");
            System.out.print("Opción: ");
            int op = parseIntSafe(sc.nextLine(), -1);
            switch (op) {
                case 1 -> descuentoView.mostrarConfiguracion();
                case 2 -> {
                    System.out.print("Ingrese porcentaje (0-100): ");
                    double p = parseDoubleSafe(sc.nextLine(), 0);
                    p = Math.max(0, Math.min(100, p));
                    descuentoController.ejecutar(new SetDescuentoGlobalCommand(p / 100.0));
                    descuentoView.mostrarConfiguracion();
                }
                case 3 -> {
                    System.out.print("Ingrese cupón válido (vacío para ninguno): ");
                    String c = emptyToNull(sc.nextLine());
                    descuentoController.ejecutar(new SetCuponValidoCommand(c));
                    descuentoView.mostrarConfiguracion();
                }
                case 4 -> {
                    System.out.print("Ingrese categoría en promoción (vacío para ninguna): ");
                    String cat = emptyToNull(sc.nextLine());
                    descuentoController.ejecutar(new SetCategoriaPromocionCommand(cat));
                    descuentoView.mostrarConfiguracion();
                }
                case 5 -> {
                    System.out.print("Ingrese cupón a aplicar en boleta (vacío para ninguno): ");
                    cuponActual = emptyToNull(sc.nextLine());
                }
                case 0 -> back = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void verHistoricoBoletas() {
        System.out.println("\n=== Histórico de boletas emitidas ===");
        List<Boleta> lista = dataLoader.cargarBoletasCSV();
        if (lista.isEmpty()) {
            System.out.println("No hay boletas registradas.");
            return;
        }
        int i = 1;
        for (Boleta b : lista) {
            System.out.println("\n--- Boleta #" + (i++) + " ---");
            carritoView.mostrar(b);
        }
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
    private static double parseDoubleSafe(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
    private static String emptyToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
