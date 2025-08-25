package com.grupo7.ecommerce;

import com.grupo7.ecommerce.services.DiscountManager;
import com.grupo7.ecommerce.util.CurrencyUtils;
import com.grupo7.ecommerce.model.Carrito;
import com.grupo7.ecommerce.model.Producto;
import com.grupo7.ecommerce.commands.*;

public class App {
    public static void main(String[] args) {
        DiscountManager gestor = DiscountManager.getInstance();

        // --- Demostración: patrón Command para Carrito (agregar y eliminar productos)
        System.out.println("\n---- Carrito de compras (Command) ----");
        Carrito carrito = new Carrito();
        Invoker invoker = new Invoker();

        Producto p1 = new Producto("Polera", "Ropa", 12000);
        Producto p2 = new Producto("Pantalón", "Ropa", 18000);
        Producto p3 = new Producto("Gorro", "Accesorios", 5000);

        invoker.agregarComando(new AgregarProductoCommand(carrito, p1));
        invoker.agregarComando(new AgregarProductoCommand(carrito, p2));
        invoker.agregarComando(new AgregarProductoCommand(carrito, p3));
        invoker.agregarComando(new EliminarProductoCommand(carrito, p3));

        invoker.ejecutarComandos();

        // --- Boleta completa con descuentos aplicados a cada producto ---
        String cuponCarrito = "DESCUENTO50"; // Cupón aplicado al carrito (si corresponde)
        double subtotal = 0.0;
        double total = 0.0;
        double ahorroTotal = 0.0;

        System.out.println("\n==============================");
        System.out.println("        Boleta de Venta       ");
        System.out.println("==============================");
        System.out.printf("%-18s %-12s %-14s %-14s %-14s%n", "Producto", "Categoría", "Precio base", "Precio final", "Ahorro");
        System.out.println("--------------------------------------------------------------------------");

        for (Producto prod : carrito.getItems()) {
            double original = prod.getPrecio();
            double conDesc = gestor.aplicarDescuento(prod.getNombre(), original, prod.getCategoria(), cuponCarrito);
            double ahorro = Math.max(0, original - conDesc);

            subtotal += original;
            total += conDesc;
            ahorroTotal += ahorro;

            System.out.printf("%-18s %-12s %-14s %-14s %-14s%n",
                    prod.getNombre(),
                    prod.getCategoria(),
                    CurrencyUtils.formatCL(original),
                    CurrencyUtils.formatCL(conDesc),
                    CurrencyUtils.formatCL(ahorro));
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-31s %-14s%n", "Subtotal:", CurrencyUtils.formatCL(subtotal));
        System.out.printf("%-31s %-14s%n", "Descuentos:", "-" + CurrencyUtils.formatCL(ahorroTotal));
        System.out.printf("%-31s %-14s%n", "Total a pagar:", CurrencyUtils.formatCL(total));
    }
}