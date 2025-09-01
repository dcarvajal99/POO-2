package com.grupo7.ecommerce.controller;

import com.grupo7.ecommerce.commands.Command;
import com.grupo7.ecommerce.model.DiscountManager;

public class DescuentoController {
    private final DiscountManager dm = DiscountManager.getInstance();

    public DiscountManager getDiscountManager() { return dm; }

    public void ejecutar(Command comando) {
        comando.Ejecutar();
    }

    // Nuevos: configuración directa de descuentos
    public void setDescuentoGlobal(double porcentaje) {
        dm.setPorcentajeGlobal(porcentaje);
    }
    public void setCuponValido(String cupon) {
        dm.setCuponValido(cupon);
    }
    public void setCategoriaPromocion(String categoria) {
        dm.setCategoriaDesc(categoria);
    }
}
