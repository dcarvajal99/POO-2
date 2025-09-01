package com.grupo7.ecommerce.view;

import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.utils.CurrencyUtils;

import java.util.List;

public class ProductoView {
    public void mostrarCatalogo(List<Component> productos) {
        System.out.println("\n=== Catálogo de productos ===");
        System.out.printf("%-6s %-18s %-12s %-14s%n", "#", "Producto", "Categoría", "Precio base");
        for (int i = 0; i < productos.size(); i++) {
            Component p = productos.get(i);
            System.out.printf("%-6s %-18s %-12s %-14s%n",
                    (i + 1), p.getNombre(), p.getCategoria(), CurrencyUtils.formatCL(p.getPrecio()));
        }
    }
}
