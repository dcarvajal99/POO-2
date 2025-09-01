package com.grupo7.ecommerce.view;

import com.grupo7.ecommerce.model.DiscountManager;

public class DescuentoView {
    public void mostrarConfiguracion() {
        DiscountManager dm = DiscountManager.getInstance();
        System.out.println("\n=== Configuración de descuentos ===");
        System.out.println("Descuento global: " + (int)(dm.getPorcentajeGlobal() * 100) + "%");
        System.out.println("Cupón válido: " + dm.getCuponValido());
        System.out.println("Categoría en promoción: " + dm.getCategoriaDesc());
    }
}

