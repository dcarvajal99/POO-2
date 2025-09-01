package com.grupo7.ecommerce.view;

import com.grupo7.ecommerce.model.DiscountManager;

public class DescuentoView {
    public void mostrarCuponesDisponibles() {
        DiscountManager dm = DiscountManager.getInstance();
        String cupon = dm.getCuponValido();
        if (cupon == null || cupon.isBlank()) {
            System.out.println("No hay cupón disponible.");
        } else {
            System.out.println("Cupón disponible: " + cupon + " (50% de descuento)");
        }
    }

    public void mostrarDescuentosDisponibles() {
        DiscountManager dm = DiscountManager.getInstance();
        System.out.println("Descuento global: " + (int)(dm.getPorcentajeGlobal() * 100) + "%");
        String categoria = dm.getCategoriaDesc();
        if (categoria != null && !categoria.isBlank()) {
            System.out.println("Categoría en promoción: " + categoria + " (20% de descuento)");
        }
    }
}
