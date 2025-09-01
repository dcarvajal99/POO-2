package com.grupo7.ecommerce.commands;

import com.grupo7.ecommerce.model.DiscountManager;

public class SetCategoriaPromocionCommand implements Command {
    private final String categoria;

    public SetCategoriaPromocionCommand(String categoria) {
        this.categoria = categoria == null ? null : categoria.trim();
    }

    @Override
    public void Ejecutar() {
        DiscountManager.getInstance().setCategoriaDesc(categoria);
        System.out.println("Categoría en promoción actualizada: " + categoria);
    }
}

