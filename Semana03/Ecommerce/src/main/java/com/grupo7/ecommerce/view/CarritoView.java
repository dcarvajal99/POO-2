package com.grupo7.ecommerce.view;

import com.grupo7.ecommerce.model.Carrito;
import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.DiscountManager;
import com.grupo7.ecommerce.model.dto.Boleta;
import com.grupo7.ecommerce.model.dto.LineaBoleta;
import com.grupo7.ecommerce.utils.CurrencyUtils;

public class CarritoView {
    // Nueva: listado del carrito con índices
    public void mostrarCarritoListado(Carrito carrito) {
        System.out.println("\n=== Carrito ===");
        if (carrito.getItems().isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        System.out.printf("%-6s %-18s %-12s %-14s%n", "#", "Producto", "Categoría", "Precio base");
        int i = 1;
        for (Component c : carrito.getItems()) {
            System.out.printf("%-6s %-18s %-12s %-14s%n", i++, c.getNombre(), c.getCategoria(), CurrencyUtils.formatCL(c.getPrecio()));
        }
    }

    // Vista antigua (aún disponible)
    public void mostrar(Carrito carrito, String cupon) {
        DiscountManager dm = DiscountManager.getInstance();
        System.out.println("\n==============================");
        System.out.println("        Boleta de Venta       ");
        System.out.println("==============================");
        System.out.printf("%-18s %-12s %-14s %-14s %-14s%n", "Producto", "Categoría", "Precio base", "Precio final", "Ahorro");
        System.out.println("--------------------------------------------------------------------------");

        double subtotal = 0.0, total = 0.0, ahorroTotal = 0.0;
        for (Component c : carrito.getItems()) {
            double original = c.getPrecio();
            double conDesc = dm.aplicarDescuento(c.getNombre(), original, c.getCategoria(), cupon);
            double ahorro = Math.max(0, original - conDesc);
            subtotal += original;
            total += conDesc;
            ahorroTotal += ahorro;
            System.out.printf("%-18s %-12s %-14s %-14s %-14s%n",
                    c.getNombre(), c.getCategoria(),
                    CurrencyUtils.formatCL(original),
                    CurrencyUtils.formatCL(conDesc),
                    CurrencyUtils.formatCL(ahorro));
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-31s %-14s%n", "Subtotal:", CurrencyUtils.formatCL(subtotal));
        System.out.printf("%-31s %-14s%n", "Descuentos:", "-" + CurrencyUtils.formatCL(ahorroTotal));
        System.out.printf("%-31s %-14s%n", "Total a pagar:", CurrencyUtils.formatCL(total));
    }

    // Nueva vista basada en DTO Boleta (separa lógica de UI)
    public void mostrar(Boleta boleta) {
        System.out.println("\n==============================");
        System.out.println("        Boleta de Venta       ");
        System.out.println("==============================");
        System.out.printf("%-18s %-12s %-14s %-14s %-14s%n", "Producto", "Categoría", "Precio base", "Precio final", "Ahorro");
        System.out.println("--------------------------------------------------------------------------");
        for (LineaBoleta l : boleta.getLineas()) {
            System.out.printf("%-18s %-12s %-14s %-14s %-14s%n",
                    l.getNombre(), l.getCategoria(),
                    CurrencyUtils.formatCL(l.getPrecioBase()),
                    CurrencyUtils.formatCL(l.getPrecioFinal()),
                    CurrencyUtils.formatCL(l.getAhorro()));
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-31s %-14s%n", "Subtotal:", CurrencyUtils.formatCL(boleta.getSubtotal()));
        System.out.printf("%-31s %-14s%n", "Descuentos:", "-" + CurrencyUtils.formatCL(boleta.getAhorroTotal()));
        System.out.printf("%-31s %-14s%n", "Total a pagar:", CurrencyUtils.formatCL(boleta.getTotal()));
    }
}
